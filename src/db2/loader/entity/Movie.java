package db2.loader.entity;

import java.util.ArrayList;

public class Movie {
    public int id;

    public String name;
    public String caption;
    public String description;

    public int releaseYear;
    public double rating;
    public double price;

    public ArrayList<String> issuers = new ArrayList<>();
    public ArrayList<String> genres = new ArrayList<>();

    public void setIssuers(String line) {
        String[] list = line.split(", ");
        for (String item : list) {
            issuers.add(item);
        }
    }
    public void setGenres(String line) {
        String[] list = line.split(", ");
        for (String item : list) {
            genres.add(item);
        }
    }
    public void setNameHeader(String line) {
        String[] list = line.split("/");
        name = list[0];
        caption = list[1];
    }
}
