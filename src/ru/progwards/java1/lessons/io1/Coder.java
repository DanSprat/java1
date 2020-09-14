package ru.progwards.java1.lessons.io1;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Coder {
    public static void codeFile(String inFileName, String outFileName, char[] code, String logName) {
        try {
            FileReader fileReader = new FileReader(inFileName);
            FileWriter fileWriter = new FileWriter(outFileName);
            try {
                Scanner scanner = new Scanner(fileReader);
                String str ="";
                while (scanner.hasNextLine()) {
                    str=scanner.nextLine();
                    for (int i = 0; i < str.length(); ++i) {
                        fileWriter.write(code[(int) str.charAt(i)]);
                    }
                    fileWriter.write('\n');
                }
            } finally {
                fileReader.close();
                fileWriter.close();
            }
        } catch (Exception e) {
            try {
                FileWriter fileWriter = new FileWriter(logName);
                fileWriter.write(e.getMessage());
                fileWriter.close();
            } catch (Exception e1) {
                System.err.println(e1.getMessage());
            }
        }
    }
    public static void main(String[] args) throws IOException{
        System.out.println((char)1);
        char [] a = {'c','a'};
        codeFile("s","s1",a,"s2");
    }
}
