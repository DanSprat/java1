package ru.progwards.java1.lessons.bitsworld;

public class CheckBit {
    public static int checkBit(byte value, int bitNumber){
        if ((value & 1<<bitNumber)>0)
            return 1;
        else return 0;
    }

    public static void main(String[] args) {
        System.out.println(checkBit((byte)15,0));
    }
}
