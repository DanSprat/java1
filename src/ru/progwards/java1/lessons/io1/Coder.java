package ru.progwards.java1.lessons.io1;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Coder {
    public static void codeFile(String inFileName, String outFileName, char[] code, String logName) {
        FileReader fileReader;
        FileWriter fileWriter;
        try {
            fileReader = new FileReader(inFileName);
            fileWriter = new FileWriter(outFileName);
            try {
                Scanner scanner = new Scanner(fileReader);
                String str ="";
                while (scanner.hasNextLine()) {
                    str=scanner.nextLine();
                    for (int i = 0; i < str.length(); ++i) {
                        if (str.charAt(i)!=' ')
                            fileWriter.write(code[(int) str.charAt(i)]);
                        else
                            fileWriter.write(' ');
                    }
                    fileWriter.write('\n');
                }
            } finally {
                fileReader.close();
                fileWriter.close();
            }
        } catch (Exception e) {
            try {
                FileWriter fileWriter2 = new FileWriter(logName);
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
            a[i] = (char)(i+1);
        }
        codeFile("s","s1",a,"s2");
    }
}
