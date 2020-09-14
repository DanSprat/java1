package ru.progwards.java1.lessons.io1;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.Scanner;

public class LineCount {
    public static int calcEmpty(String fileName){
        int count =0;
        try {
            FileReader reader = new FileReader(fileName);
            try {
                Scanner scanner = new Scanner(reader);
                while (scanner.hasNextLine()) {
                    String str = scanner.nextLine();
                    if (str.length() == 0)
                        count++;
                }
            } finally {
                reader.close();
            }
        } catch (Exception e){
            System.err.println(e);
            return -1;
        }
        return count;
    }

    public static void main(String[] args) throws IOException {
        FileWriter fileWriter = new FileWriter("name");
        fileWriter.write("Hello\n");
        fileWriter.write("\n");
        fileWriter.write("end");
        fileWriter.close();
        System.out.println(calcEmpty("name1"));
    }
}
