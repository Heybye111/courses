package ru.inno.courses.homework2.task2;

public class Room {
    double square;
    String roomType;
    boolean balcony;
    int countOfWindows;

    public Room(double square, String roomType, boolean balcony, int countOfWindows) {
        this.square = square;
        this.roomType = roomType;
        this.balcony = balcony;
        this.countOfWindows = countOfWindows;
    }
}
