package ru.progwards.java1.lessons.io1;

import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class CharFilter {
    public static void filterFile(String inFileName, String outFileName, String filter) throws IOException {
        FileReader fileReader = null;
        FileWriter fileWriter = null;
        try {
            fileReader = new FileReader(inFileName);
            fileWriter = new FileWriter(outFileName);
            Scanner scanner = new Scanner(fileReader);
            String str;
            while (scanner.hasNextLine()) {
                str = scanner.nextLine();
                for (int i = 0; i < str.length(); ++i) {
                    for (int j = 0; j < filter.length(); ++j) {
                        if (filter.charAt(j) == str.charAt(i))
                            break;
                        if (j == filter.length()-1)
                            fileWriter.write(str.charAt(i));
                    }
                }
                fileWriter.write('\n');
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
        filterFile("s","s1",str);
    }
}
