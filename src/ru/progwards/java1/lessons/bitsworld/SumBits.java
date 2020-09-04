package ru.progwards.java1.lessons.bitsworld;

public class SumBits {
    public static int sumBits(byte value){
        int sum =0;
        for (int i =0;i<8;++i){
            sum+=value&1;
            value>>>=1;
        }
        return sum;
    }
    public static void main(String[] args) {
        System.out.println(sumBits((byte) 15));
    }
}
