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
}
