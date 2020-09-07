package ru.progwards.java1.lessons.test;

import java.math.BigDecimal;
import java.util.Arrays;

public class Test1 {
    public static int ret(byte value){
        byte a = 0b0000_0001;
        return a&value;
    }
    public static void main(String[] args){
        System.out.println("Сделаю коммит, запушу в репо: робот, проверяй теперь всё это...");
        int[] a1 = {12, 5, 0, 58, 36};
        int[] a2 = Arrays.copyOf(a1, a1.length);
        Arrays.sort(a2);
        System.out.println(Arrays.equals(a1, a2));
        System.out.println(ret((byte)15));
        BigDecimal a = new BigDecimal("10.0");
        BigDecimal b = new BigDecimal("10.0");
        System.out.println(a.multiply(b).compareTo(a.multiply(b)));
    }
}
