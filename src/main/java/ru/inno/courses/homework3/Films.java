package ru.inno.courses.homework3;

public class Films {
    public static void main(String[] args) {

        //task 3

        Movie firstMovie = new Movie("Lobster", 7.0, "drama", "Irland", false);
        Movie secondMovie = new Movie("Crazy, Stupid, Love.", 7.5, "comedy", "USA", false);
        Movie thirdMovie = new Movie("Schindler's List", 8.8, "military", "USA", true);

        Movie[] films = new Movie[3];
        films[0] = firstMovie;
        films[1] = secondMovie;
        films[2] = thirdMovie;

        // task 5
        for (Movie currentMovie : films) {
            System.out.println(currentMovie.name + " " + currentMovie.genre + " " + currentMovie.rating);
        }




    }

}
