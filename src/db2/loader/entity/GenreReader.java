package db2.loader.entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class GenreReader implements Reader {
    private BufferedReader reader;
    private BufferedWriter writer;

    public GenreReader(BufferedReader reader, BufferedWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public Genre readObject() throws IOException {
        Genre result = new Genre();

        result.name = reader.readLine();
        if (result.name == null) {
            return null;
        }
        reader.readLine();

        return result;
    }

    public void writeObject(Object object) throws IOException {
        Genre value = (Genre) object;
        String sql = "INSERT INTO genre (id, name) " +
            "VALUES (genre_seq.nextval, " +
            "'" + value.name + '\'' +
            ");";
        writer.write(sql);
        writer.newLine();
    }
}
