package db2.loader.entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class UserReader implements Reader {
    private BufferedReader reader;
    private BufferedWriter writer;

    public UserReader(BufferedReader reader, BufferedWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public User readObject() throws IOException {
        User result = new User();

        result.name = reader.readLine();
        if (result.name == null) {
            return null;
        }
        reader.readLine();
        result.email = reader.readLine();
        reader.readLine();
        result.login = reader.readLine();
        reader.readLine();
        reader.readLine();
        reader.readLine();

        return result;
    }

    public void writeObject(Object object) throws IOException {
        User value = (User) object;
        String sql = "INSERT INTO users (id, name, email, login) " +
            "VALUES (users_seq.nextval, " +
            "'" + value.name + '\'' + ", '" + value.email + '\'' + ", '" + value.login + '\'' +
            ");";
        writer.write(sql);
        writer.newLine();
    }
}
