package db2.loader.entity;

import java.io.IOException;

// Object reader
public interface Reader<T> {
    public T readObject() throws IOException;

    public void writeObject(T object) throws IOException;
}
