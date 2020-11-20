package ru.progwards.java2.lessons.recursion;

public class AsNumbersSum {


    public static String asNumbersSum(int number){
        StringBuffer string = new StringBuffer(String.valueOf(number));
        asNumbersSum(number-1,string,new StringBuffer(""),number);
        return null;
    }
    private static String asNumbersSum(int number,StringBuffer string,StringBuffer prev,int max){
        return null;
    }

    public static void main(String[] args) {
        String string = asNumbersSum(6);
    }
}
