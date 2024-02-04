package ru.inno.courses.homework2.task3;

public class Task3 {
    public static void main(String[] args) {
        Item CPU = new Item("Intel Core i5 13400f", "656RFT", 14500, 65, "gray");
        Item Playstation5 = new Item("Playstation5", "432fds", 60000, 12, "white");
        Item Xbox = new Item ("X box series X", "ger435", 55000, 34, "black");
        Item s24 = new Item("Samsung s24", "rewrew32", 34, "blue");
        Item newItem = new Item("Iphone 15 pro max", "ewrw34", 120000, 124, "titanium");


        System.out.println(CPU.article + " " + CPU.name + " " + CPU.price + " " + CPU.count + " " + CPU.color);
        System.out.println(Playstation5.article + " " + Playstation5.name + " " + Playstation5.price + " " + Playstation5.count + " " + Playstation5.color);
        System.out.println(Xbox.article + " " + Xbox.name + " " + Xbox.price + " " + Xbox.count + " " + Xbox.color);
        System.out.println(s24.article + " " + s24.name + " " + s24.count + " " + s24.color);
        System.out.println(newItem.article + " " + newItem.name + " " + newItem.price + " " + newItem.count + " " + newItem.color);
    }
}
