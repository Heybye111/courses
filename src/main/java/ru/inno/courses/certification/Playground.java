package ru.inno.courses.certification;

import java.text.ParseException;

import java.util.Scanner;
import java.time.*;
import java.time.format.DateTimeFormatter;

import static ru.inno.courses.certification.Sensor.*;

public class Playground {
    public static void main(String[] args) throws ParseException {
        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите дату последнего полива куста в формате 'yyyy-mm-dd' : ");
        String date = scanner.nextLine();
        LocalDate wateringDay = LocalDate.parse(date, DateTimeFormatter.ISO_LOCAL_DATE);
        String season = getSeason(LocalDate.parse(date));
        System.out.println(season);
        int humidity = getRandomHumidity();
//        System.out.println(humidity);
        if (season.equals("summer") && humidity <= 30) {
            System.out.println("Дата для полива: " + wateringDay);
        } else {
            LocalDate finalWateringDayDate = DateOfWatering(season, LocalDate.parse(date));
            System.out.println("Дата для полива: " + finalWateringDayDate);
        }
    }
}
