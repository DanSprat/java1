package ru.progwards.java1.lessons.io1;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class Coder {
    public static void codeFile(String inFileName, String outFileName, char[] code, String logName) throws IOException {
        FileReader fileReader = null;
        FileWriter fileWriter = null;
        try {
            fileReader = new FileReader(inFileName);
            fileWriter = new FileWriter(outFileName);
            Scanner scanner = new Scanner(fileReader);
            String str;
            while(scanner.hasNextLine()){
                str=scanner.nextLine();
                for(int i=0;i<str.length();i++)
                fileWriter.write(code[(int)str.charAt(i)]);
            }
        } catch (Exception e){
            FileWriter fileWriterLog = new FileWriter(logName);
            try {
                fileWriterLog.write(e.getMessage());
            } catch (Exception ex) {
                System.err.println(ex.getMessage());
            } finally {
                fileWriterLog.close();
            }
        } finally {
            fileReader.close();
            fileWriter.close();
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
