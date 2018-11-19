package db2.loader.entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class UserReader implements Reader {
    private LineReader lineReader;

    public UserReader(LineReader lineReader) {
        this.lineReader = lineReader;
    }

    public User readObject() throws IOException {
        User result = new User();

        result.name = lineReader.read();
        if (result.name == null) {
            return null;
        }
        // email
        result.email = lineReader.read();
        // login
        result.login = lineReader.read();

        return result;
    }

    public void writeObject(Object object) throws IOException {
        User value = (User) object;
        String sql = "INSERT INTO users (id, name, email, login) " +
            "VALUES (users_seq.nextval, " +
            "'" + value.name + '\'' + ", '" + value.email + '\'' + ", '" + value.login + '\'' +
            ");";
        lineReader.write(sql);
    }
}
