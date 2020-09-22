package ru.progwards.java1.lessons.sets;

import java.io.FileInputStream;
import java.io.FileReader;
import java.io.RandomAccessFile;
import java.util.*;

public class LettersInFile {
    public static String process(String fileName) throws Exception{
        FileInputStream fileInputStream = new FileInputStream(fileName);
        byte [] str = fileInputStream.readAllBytes();
        String s = new String(str);
        char [] chars = s.toCharArray();
        Set<Character> hashSet = new TreeSet<>();
        for (char c:chars){
            hashSet.add(c);
        }
        System.out.println(hashSet);
        String result="";
        for (Character c: hashSet){
            result+=c;
        }
        return result;
    }

    public static void main(String[] args) throws Exception{
        System.out.println(process("s"));
    }
}
