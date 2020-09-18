package ru.progwards.java1.lessons.test;

import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.Random;
import java.util.Scanner;

public class Test1 {
    public static int ret(byte value){
        byte a = 0b0000_0001;
        return a&value;
    }
    public static Integer sqr(Integer n){
        try {
       return n*n;
        } catch (NullPointerException ex) {
            return -1;
        }

    }
    public static String invertWords(String sentence){
        String [] strs = sentence.split(" ");
        StringBuffer stringBuffer = new StringBuffer();
        for (String s:strs){
            stringBuffer.insert(0,s+" ");
        }
        return stringBuffer.toString().trim().replace(" ",".");
    }
    public static String setStars(String filename) throws IOException{
        String str="";
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(filename,"rw");){
            for (long i = 9;i<randomAccessFile.length();i+=10){
                randomAccessFile.seek(i);
                str+=(char)randomAccessFile.read();
                randomAccessFile.seek(i);
                randomAccessFile.write('*');
            }
        } catch (IOException ex)
        {
            throw new IOException(ex.getClass().toString().replace("class ",""));
        }
        return str;
    }
    private int lineCount(String filename) throws IOException{
        int i = 0;
        try {
            FileReader reader = new FileReader(filename);
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNextLine()) {
                ++i;
                scanner.nextLine();
            }
        } catch (Exception e){
            throw new IOException("файл не найден");
        }
        return i;
    }
    public static void main(String[] args) throws IOException{
        int n = 0;
        for (int i =0;i<100000000;i++){
            int shoot=0;
           for (int j=1;j<=5;j++){
               double x = Math.random();
               if (x>=0.4){
                   shoot++;
                   double y = Math.random();
                   if(shoot==1){
                       if(y>=0.5){
                          if(j==5)
                              n++;
                          break;
                       }
                   } else if (shoot==2) {
                       if(y>=0.3){
                           if (j==5)
                               n++;
                           break;
                       }
                   } else {
                       if (j==5)
                           n++;
                       break;
                   }
               }

           }
        }
        System.out.println((double)n/100000000);
        int n1 =0;
        for (int i =0;i<100000000;i++){
            int count =0;
            for (int j=1;j<=4;j++){
                double x = Math.random();
                if (x>=0.6){
                    count++;
                    double y = Math.random();
                    if (count==1){
                        if (y>=0.5){
                            if (j==4){
                                n++;
                            }
                                break;
                        }
                    } else if (count == 2){
                        if (y>=0.7){
                            
                        }
                    }
                }
            }
        }




        System.out.println(setStars("s"));
        System.out.println(invertWords("Буря мглою небо кроет"));
        System.out.println("Сделаю коммит, запушу в репо: робот, проверяй теперь всё это...");
        int[] a1 = {12, 5, 0, 58, 36};
        int[] a2 = Arrays.copyOf(a1, a1.length);
        Arrays.sort(a2);
        System.out.println(Arrays.equals(a1, a2));
        System.out.println(ret((byte)15));
        BigDecimal a = new BigDecimal("10.0");
        BigDecimal b = new BigDecimal("10.0");
        System.out.println(a.multiply(b).compareTo(a.multiply(b)));
        Integer as =null;
        System.out.println(sqr(as));

    }
}
