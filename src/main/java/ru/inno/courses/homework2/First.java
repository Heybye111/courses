package ru.inno.courses.homework2;

public class First {

    public static void main(String[] args) {

        String cardNumber = "1234 5678 9012 3456";

        String cardNumber2 = "1234567890123456";

        String hideNumbers = "**** **** **** ";

        String lastNumbers = cardNumber.substring(cardNumber.lastIndexOf(" ")+1);

        String result = hideNumbers + lastNumbers;

        System.out.println(result);

        String sub = cardNumber.substring(cardNumber2.length() -1 );

        System.out.println(hideNumbers + sub);


    }

}
