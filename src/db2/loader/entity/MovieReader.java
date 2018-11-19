package db2.loader.entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

/*
  Read Movie fields
 */
public class MovieReader implements Reader {
    private BufferedReader reader;
    private BufferedWriter writer;

    public MovieReader(BufferedReader reader, BufferedWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public String readLine() throws IOException {
        String result = reader.readLine();
        if ("".equals(result)) {
            result = readLine();
        }
        return result;
    }

    public String wrapText(String text) {
        String result = text.replace("'", "''");
        return "'" + result + "'";
    }

    public Movie readObject() throws IOException {
        Movie result = new Movie();
        String line = readLine();
        if (line == null) {
            return null;
        }
        // names
        String[] list = line.split("/");
        result.name = list[0];
        result.caption = list[1];
        // year
        result.releaseYear = Integer.parseInt(readLine());
        // issuers
        line = readLine();
        result.setIssuers(line);
        // genres
        list = readLine().split(", ");
        for (String item : list) {
            result.genres.add(item);
        }
        // description
        result.description = readLine();
        // rating
        list = readLine().split(":");
        result.rating = Double.parseDouble(list[1]);
        reader.readLine();
        // price
        list = readLine().split(":");
        result.price = Double.parseDouble(list[1]);

        return result;
    }

    public void writeObject(Object object) throws IOException {
        Movie value = (Movie) object;
        String sqlText = "INSERT INTO movie (id, name, caption, description, release_year, rating, price) VALUES (" +
                "movie_seq.nextval, " +
                wrapText(value.name) + ", " + wrapText(value.caption) + ", " + wrapText(value.description) + ", " +
                value.releaseYear + ", " + value.rating + ", " + value.price +
            ");";
        writer.write(sqlText);
        writer.newLine();
        // movie_genre
        for (String genre : value.genres) {
            sqlText = "INSERT INTO movie_genre (movie_id, genre_id) SELECT " +
                "movie_seq.currval, t.id FROM genre t WHERE t.name = " +
                wrapText(genre) +
                ";";
            writer.write(sqlText);
            writer.newLine();

        }
        // movie_issuer
        for (String issuer : value.issuers) {
            sqlText = "INSERT INTO movie_issuer (movie_id, issuer) SELECT " +
                    "movie_seq.currval, " + wrapText(issuer) +
                    " FROM dual;";
            writer.write(sqlText);
            writer.newLine();

        }
    }
}
