package ru.inno.courses.homework4;

public class Playground {
    public static void main(String[] args) {
        int balance = 15;
        if (balance == 10) {
            System.out.println("Десятка");

        }
        int number = 9;
        if (number % 2 == 0) {
            System.out.println("Четное число");
            if (number % 4 == 0) {
                System.out.println("Четное число. Кратно четырем");
            }
        } else {
            if (number % 3 == 0) {
                System.out.println("Нечетное число. Кратное трем.");
            }
            System.out.println("Нечетное число");
        }

        String url = "http://vk.com";
        if (url.startsWith("https://")) {
            System.out.println("Соединение безопасное");
        } else {
            System.out.println("Небезопано. Не указывайте логины, пароли и данные банковских карт");
        }
        String password = "qwerty123";
        if (password.length() < 8) {
            System.out.println("Password less then 8 symbols");
        } if (password.matches("^/d")) {
            System.out.println("Password must contains at least one number");

        }    }
}
