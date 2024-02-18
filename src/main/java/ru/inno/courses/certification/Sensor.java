package ru.inno.courses.certification;

import java.time.LocalDate;
import java.util.Random;

public class Sensor {

    private int humidity;

    public Sensor(int humidity) {
        this.humidity = humidity;
    }

    public static int getRandomHumidity() {
        Random random = new Random();
        return random.nextInt(100) + 1;
    }

    ;

    public static String getSeason(LocalDate date) {
        int month = date.getMonthValue();
        String season;
        if (month == 12 || month <= 2) {
            season = "winter";
        } else if (month >= 3 && month <= 5) {
            season = "spring";
        } else if (month >= 6 && month <= 8) {
            season = "summer";
        } else {
            season = "autumn";
        }
        return season;
    }


    public static LocalDate DateOfWatering(String season, LocalDate date) {
        LocalDate dateToWatering;
        if (season.equals("winter")) {
            dateToWatering = date.plusDays(30);
        } else if (season.equals("spring")) {
            dateToWatering = date.plusDays(14);
        } else if (season.equals("autumn")) {
            dateToWatering = date.plusDays(14);
        } else {
            dateToWatering = date.plusDays(2);
        }
        return dateToWatering;
    }

}


