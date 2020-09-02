package ru.progwards.java1.lessons.basics;

public class ReverseDigits {
    public static int reverseDigits(int number) {
        return (number%10)*100 + ((number/10)%10)*10 + number/100;
    }

    public static void main(String[] args) {
        System.out.println(reverseDigits(123));
    }
}
