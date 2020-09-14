package ru.progwards.java1.lessons.test;

import java.io.FileReader;
import java.io.IOException;
import java.io.Reader;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Scanner;

public class Test1 {
    public static int ret(byte value){
        byte a = 0b0000_0001;
        return a&value;
    }
    public static Integer sqr(Integer n){
        try {
       return n*n;
        } catch (NullPointerException ex) {
            return -1;
        }

    }
    private int lineCount(String filename) throws IOException{
        int i = 0;
        try {
            FileReader reader = new FileReader(filename);
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNextLine()) {
                ++i;
                scanner.nextLine();
            }
        } catch (Exception e){
            throw new IOException("файл не найден");
        }
        return i;
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
        Integer as =null;
        System.out.println(sqr(as));

    }
}
