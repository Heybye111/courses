package ru.inno.courses.homework2.task3;

public class Item {

    String name;
    String article;
    int price;
    int count;
    String color;

    public Item(String name, String article, int price, int count, String color) {
        this.name = name;
        this.article = article;
        this.price = price;
        this.count = count;
        this.color = color;
    }

    public Item(String name, String article, int count, String color) {
        this.name = name;
        this.article = article;
        this.count = count;
        this.color = color;
    }
}
