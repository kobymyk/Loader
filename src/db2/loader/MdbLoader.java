package db2.loader;

import db2.loader.entity.*;
import db2.loader.entity.Reader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.*;

/*
 Loads files in specific order
 generating INSERT statements
 */
public class MdbLoader {
    private static final Logger log = LoggerFactory.getLogger(MdbLoader.class);
    // todo: table of files
    public static void main(String[] args) {
        // todo: for (String item :
        //load("user.txt");
        //load("genre.txt");
        //load("movie.txt");
        load("review.txt");
    }

    private static void load(String path) {
        log.info("load:path={}", path);
        File fileIn = new File(path);
        File fileOut = new File(path + ".sql");
        String fileName = fileIn.getName();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileIn));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileOut))) {

            LineReader lineReader = new LineReader(bufferedReader, bufferedWriter);
            Reader objectReader = null;

            if ("user.txt".equals(fileName)) {
                objectReader = new UserReader(lineReader);
            } else if ("genre.txt".equals(fileName)) {
                objectReader = new GenreReader(bufferedReader, bufferedWriter);
            } else if ("movie.txt".equals(fileName)) {
                objectReader = new MovieReader(lineReader);
            } else if ("review.txt".equals(fileName)) {
                objectReader = new ReviewReader(lineReader);
            }

            Object object = null;
            while ((object = objectReader.readObject()) != null) {
                objectReader.writeObject(object);
            }
            bufferedWriter.flush();
            log.info("load::end");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
