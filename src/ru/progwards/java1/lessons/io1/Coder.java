package ru.progwards.java1.lessons.io1;

import java.io.*;
import java.util.Scanner;

public class Coder {
    public static void codeFile(String inFileName, String outFileName, char[] code, String logName) {
        FileInputStream fileReader;
        FileWriter fileWriter;
        try {
            fileReader = new  FileInputStream(inFileName);
            fileWriter = new FileWriter(outFileName);
            try {
                byte [] str =fileReader.readAllBytes();
                for (int i = 0; i < str.length; ++i) {
                    fileWriter.write(code[(int) str[i]]);
                }
            } finally {
                fileReader.close();
                fileWriter.close();
            }
        } catch (Exception e) {
            try {
                FileWriter fileWriter2 = new FileWriter(logName,true);
                fileWriter2.write(e.getMessage());
                fileWriter2.close();
            } catch (Exception e1) {
                System.err.println(e1.getMessage());
            }
        }
    }
    public static void main(String[] args) throws IOException{
        char a[]= new char[65536];
        for (int i =0;i<65536;i++){
            a[i] = (char)(i);
        }
        codeFile("s","s1",a,"s2");
    }
}
