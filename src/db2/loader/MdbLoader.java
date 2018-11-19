package db2.loader;

import db2.loader.entity.GenreReader;
import db2.loader.entity.MovieReader;
import db2.loader.entity.Reader;
import db2.loader.entity.UserReader;

import java.io.*;

/*
 Loads files in specific order
 generating INSERT statements
 */
public class MdbLoader {
    // todo: table of files
    public static void main(String[] args) {
        // todo: for (String item :
        //load("user.txt");
        //load("genre.txt");
        load("movie.txt");
    }

    private static void load(String path) {
        File fileIn = new File(path);
        File fileOut = new File(path + ".sql");
        String fileName = fileIn.getName();

        try (BufferedReader bufferedReader = new BufferedReader(new FileReader(fileIn));
             BufferedWriter bufferedWriter = new BufferedWriter(new FileWriter(fileOut))) {

            Reader reader = null;

            if ("user.txt".equals(fileName)) {
                reader = new UserReader(bufferedReader, bufferedWriter);
            } else if ("genre.txt".equals(fileName)) {
                reader = new GenreReader(bufferedReader, bufferedWriter);
            } else if ("movie.txt".equals(fileName)) {
                reader = new MovieReader(bufferedReader, bufferedWriter);
            }

            Object object = null;
            while ((object = reader.readObject()) != null) {
                reader.writeObject(object);
            }
            bufferedWriter.flush();
            System.out.println(fileName + " loaded");
        }
        catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
