package ru.inno.courses.homework2.task2;


public class Task2 {

    public static void main(String[] args) {

        Flat newFlat = new Flat("30000000", 44.5, 3, 2, 12);

        System.out.println(newFlat);


        Room bathRoom = new Room(5.6, "bathRoom", false, 0);

        System.out.println(bathRoom);
    }
}
