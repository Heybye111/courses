package ru.inno.courses.certification;

import java.util.Scanner;

public class Task1 {
    public static void main(String[] args) {
        int countOfWords = 15;
        int countOfSpaces = 3;
        int lengthOfSpace = 12;
        int lengthOfWords = countOfWords / 3 * 62;
        int lengthOfSentence = (countOfSpaces * lengthOfSpace) + lengthOfWords;

        Scanner scanner = new Scanner(System.in);
        System.out.print("Введите длинну забора в см: ");
        int lengthOfFence = scanner.nextInt();
        boolean isSentenceCanBeWrite = lengthOfFence >= lengthOfSentence;

        if (isSentenceCanBeWrite) {
            System.out.println("Предложение поместится");
        } else {
            System.out.println("Предложение не влезет на забор");
        }
    }
}
