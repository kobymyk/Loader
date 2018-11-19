package db2.loader.entity;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class ReviewReader implements Reader {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private LineReader lineReader;

    public ReviewReader(LineReader lineReader) {
        this.lineReader = lineReader;
    }

    public String wrapText(String text) {
        return "'" + text + "'";
    }

    public String wrapText(String text, boolean doReplace) {
        String result = null;
        if (doReplace) {
            result = text.replace("'", "''");
        } else {
            result = text;
        }
        return wrapText(result);
    }

    public Movie readObject() throws IOException {
        Movie result = new Movie();
        String[] list = null;
        String line = lineReader.read();
        log.info("readObject:line={}", line);
        if (line == null) {
            return null;
        }
        // names
        result.setNameHeader(line);
        result.releaseYear = Integer.parseInt(lineReader.read());
        // issuers
        line = lineReader.read();
        result.setIssuers(line);

        result.setGenres(lineReader.read());
        result.description = lineReader.read();
        // rating
        list = lineReader.read().split(":");
        result.rating = Double.parseDouble(list[1]);
        // price
        list = lineReader.read().split(":");
        result.price = Double.parseDouble(list[1]);

        return result;
    }

    public void writeObject(Object object) throws IOException {
        Movie value = (Movie) object;
        String sqlText = "INSERT INTO movie (id, name, caption, description, release_year, rating, price) VALUES (" +
                "movie_seq.nextval, " +
                wrapText(value.name) + ", " + wrapText(value.caption, true) + ", " + wrapText(value.description, true) + ", " +
                value.releaseYear + ", " + value.rating + ", " + value.price +
            ");";
        lineReader.write(sqlText);
        // movie_genre
        for (String genre : value.genres) {
            sqlText = "INSERT INTO movie_genre (movie_id, genre_id) SELECT " +
                "movie_seq.currval, t.id FROM genre t WHERE t.name = " +
                wrapText(genre) +
                ";";
            lineReader.write(sqlText);
        }
        // movie_issuer
        for (String issuer : value.issuers) {
            sqlText = "INSERT INTO movie_issuer (movie_id, issuer) SELECT " +
                    "movie_seq.currval, " + wrapText(issuer) +
                    " FROM dual;";
            lineReader.write(sqlText);
        }
    }
}
