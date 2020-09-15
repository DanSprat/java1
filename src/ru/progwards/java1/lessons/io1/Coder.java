package ru.progwards.java1.lessons.io1;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Coder {
    public static void codeFile(String inFileName, String outFileName, char[] code, String logName) throws IOException {
        FileReader fileReader = null;
        FileWriter fileWriter =null;
        try {
            fileReader = new FileReader(inFileName);
            fileWriter = new FileWriter(outFileName);
            try {
                Scanner scanner = new Scanner(fileReader);
                String str;
                while (scanner.hasNextLine()) {
                    str=scanner.nextLine();
                    for (int i = 0; i < str.length(); ++i) {
                        fileWriter.write(code[(int) str.charAt(i)]);
                    }
                }
            } finally {
                fileReader.close();
                fileWriter.close();
            }
        } catch (Exception e) {
            FileWriter fileWriter2 = new FileWriter(logName);
            try {
                fileWriter2.write(e.getMessage());
            } catch (Exception e1) {
                System.err.println(e1.getMessage());
            } finally {
                fileWriter2.close();
            }
        }
    }
    public static void main(String[] args) throws IOException{
        char a[]= new char[65536];
        for (int i =0;i<65536;i++){
            a[i] = (char)(i+1);
        }
        codeFile("s","s1",a,"s2");
    }
}
