package ru.progwards.java1.lessons.io2;

import java.io.IOException;
import java.io.RandomAccessFile;

public class Censor {
     static class CensorException extends RuntimeException {
        String message;
        public CensorException(String s){
            super(s);
        }
    }
    private static String setStars(String s){
         String str ="";
         for (char c: s.toCharArray()){
             str+='*';
         }
         return str;
    }
    public static void censorFile(String inoutFileName, String[] obscene){

        try(RandomAccessFile randomAccessFile = new RandomAccessFile(inoutFileName,"rw")){
         while (randomAccessFile.getFilePointer() <= randomAccessFile.length()){
             long start = randomAccessFile.getFilePointer();
             if (start!=0){
                 randomAccessFile.seek(randomAccessFile.getFilePointer()-1);
                 int bc = randomAccessFile.read();
                 while (!(bc ==0x0D || bc==0x0A)){
                     bc=randomAccessFile.read();

                 }
                 //randomAccessFile.seek(randomAccessFile.getFilePointer()+1);
             }
             String s = randomAccessFile.readLine();
             s = new String(s.getBytes("ISO-8859-1"),"UTF-8");
             for (int i=0;i<obscene.length;i++) {
                 if (s.contains(obscene[i])) {
                     s = s.replace(obscene[i],setStars(obscene[i]));
                 }
             }
             s+='\n';
             byte [] b = s.getBytes("UTF-8");
             randomAccessFile.seek(start);
             randomAccessFile.write(b);
         }
        } catch (IOException ex){
            CensorException censorException = new CensorException(inoutFileName);
            censorException.message = ex.getMessage();
        }
    }

    public static void main(String[] args) {
        Character i ='a';
        System.out.println();
        censorFile("s",new String[]{"Java", "Oracle", "Sun", "Microsystems"});
    }
}
