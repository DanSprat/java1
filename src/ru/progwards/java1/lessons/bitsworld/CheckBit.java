package ru.progwards.java1.lessons.bitsworld;

public class CheckBit {
    public static int checkBit(byte value, int bitNumber){
        return value & 1<<bitNumber;
    }

    public static void main(String[] args) {
        System.out.println(checkBit((byte)15,0));
    }
}
