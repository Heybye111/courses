package ru.inno.courses.homework3;

public class Movie {

    String name;
    double rating;
    String genre;
    String country;
    boolean isOscarGiven;

    public Movie(String name, double rating, String genre, String country, boolean isOscarGiven) {
        this.name = name;
        this.rating = rating;
        this.genre = genre;
        this.country = country;
        this.isOscarGiven = isOscarGiven;
    }
}
