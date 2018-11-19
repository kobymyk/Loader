package db2.loader.entity;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;

public class LineReader {
    private BufferedReader reader;
    private BufferedWriter writer;

    public LineReader(BufferedReader reader, BufferedWriter writer) {
        this.reader = reader;
        this.writer = writer;
    }

    public String read() throws IOException {
        String result = reader.readLine();


        if ("".equals(result)) {
            result = read();
        }
        return result;
    }

    public void write(String line) throws IOException {
        writer.write(line);
        writer.newLine();
    }
}
