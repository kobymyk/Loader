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

    public Review readObject() throws IOException {
        Review result = new Review();
        result.movieName = lineReader.read();
        if (result.movieName == null) {
            return null;
        }
        result.userName = lineReader.read();
        result.reviewText = lineReader.read();

        return result;
    }

    public void writeObject(Object object) throws IOException {
        Review value = (Review) object;
        String userName = wrapText(value.userName);
        log.info("writeObject:userName={}", userName);
        String reviewText = "text"; wrapText(value.reviewText);
        String movieName = wrapText(value.movieName);
        String sqlText = "INSERT INTO review (movie_id, user_name, review_text) SELECT (" +
                "t.id, " + userName + ", " + reviewText +
                " FROM movie t WHERE t.name = " + movieName + ";";
        lineReader.write(sqlText);
    }
}
