package ru.progwards.java1.lessons.io1;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CharFilter {
    public static void filterFile(String inFileName, String outFileName, String filter) throws IOException {
        filter +="—";
        FileReader fileReader = null;
        FileWriter fileWriter = null;
        try {
            fileReader = new FileReader(inFileName);
            fileWriter = new FileWriter(outFileName);
            Scanner scanner = new Scanner(fileReader);
            String str;
            boolean isFirst=true;
            while (scanner.hasNextLine()) {
                if (!isFirst)
                    fileWriter.write('\n');
                else
                    isFirst = false;
                str = scanner.nextLine();
                for (int i = 0; i < str.length(); ++i) {
                    for (int j = 0; j < filter.length(); ++j) {
                        if (filter.charAt(j) == str.charAt(i))
                            break;
                        if (j == filter.length()-1)
                            fileWriter.write(str.charAt(i));
                    }
                }
            }
        } catch (IOException e) {
            throw new IOException();
        } finally {
           if (fileReader !=null)
               fileReader.close();
           if(fileWriter!=null)
               fileWriter.close();
        }
    }
    public static void main(String[] args) throws IOException{
        String str =  " -,.()";
        for (int i=0;i<str.length();++i)
            System.out.println((int)str.charAt(i));
        filterFile("s","s1",str);
    }
}
