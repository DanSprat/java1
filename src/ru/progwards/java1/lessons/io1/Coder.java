package ru.progwards.java1.lessons.io1;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Coder {
    public static void codeFile(String inFileName, String outFileName, char[] code, String logName) throws IOException {
        try {
            FileReader fileReader = new FileReader(inFileName);
            try {
                FileWriter fileWriter = new FileWriter(outFileName);
                Scanner scanner = new Scanner(fileReader);
                try {
                    String str = scanner.toString();
                    for (int i = 0; i < str.length(); ++i) {
                        fileWriter.write(code[(int) str.charAt(i)]);
                    }
                } finally {
                    fileWriter.close();
                }
            } finally {
                fileReader.close();
            }
        } catch (Exception e) {
            try {
                FileWriter fileWriter = new FileWriter(logName);
                fileWriter.write(e.getMessage());
                fileWriter.close();
            } catch (Exception e1){
                System.err.println(e1);
            }
        }
    }
}
