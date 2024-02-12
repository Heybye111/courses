package ru.inno.courses.homework4;

public class Playground {
    public static void main(String[] args) {
        // task 1
        int balance = 15;
        if (balance == 10) {
            System.out.println("Десятка");
        }

        // task 2-3
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

        //task 4
        String url = "http://vk.com";
        if (url.startsWith("https://")) {
            System.out.println("Соединение безопасное");
        } else {
            System.out.println("Небезопано. Не указывайте логины, пароли и данные банковских карт");
        }

        // task 5
        String password = "qwertdsad!321";

        if (password.length() < 8) {
            System.out.println("Пароль не менее 8 символов");
        }
        if (!password.matches(".*\\d.*")) {
            System.out.println("Пароль должен содержать минимум 1 цифру");
        }

        if (!password.matches(".*[!@#$%^&*].*")) {
            System.out.println("Пароль должен содержать минимум 1 спецсимвол ");


        } else {
            System.out.println("Correct");
        }

        //task 6
        String pass = "asdasdas";
        if (pass.equals("Qwerty0987654321")) {
            System.out.println("Доступ разрешен");
        } else {
            System.out.println("Доступ запрещен");
        }

        // task 7
        for (int i = 1; i < 100; i++) {

            if (i % 3 == 0 && i % 5 == 0) {
                System.out.println("FizzBuzz");
            } else if (i % 5 == 0) {
                System.out.println("Buzz");
            } else if (i % 3 == 0) {
                System.out.println("Fizz");
            } else {
                System.out.println(i);
            }
        }

    }
}



