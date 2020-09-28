package ru.progwards.java1.lessons.maps;

import java.io.FileInputStream;
import java.util.HashMap;
import java.util.Map;

public class UsageFrequency {
    private String data;
    public void processFile(String fileName) throws Exception {
        FileInputStream fileInputStream = new FileInputStream(fileName);
        byte[] str = fileInputStream.readAllBytes();
        data = new String(str);
    }

    public Map<Character, Integer> getLetters(){
        HashMap<Character,Integer> lettersMap = new HashMap<>();
        for (char x:data.toCharArray()){
            if (Character.isAlphabetic(x)||Character.isDigit(x)){
                Integer i = lettersMap.putIfAbsent(x,1);
                if (i!=null){
                    lettersMap.replace(x,lettersMap.get(x)+1);
                }
            }
        }
        return lettersMap;
    }
    public Map<String, Integer> getWords(){
        HashMap<String,Integer> wordsMap = new HashMap<>();
        String s="";
        for (char x:data.toCharArray()){
            if (Character.isAlphabetic(x)||Character.isDigit(x)){
                s=s+x;
            } else {
                s=s+' ';
            }
        }
        s =s.trim();
        s =s.replaceAll("\\s+"," ");
        String [] aS = s.split(" ");
        for (String str: aS){
            Integer i = wordsMap.putIfAbsent(str,1);
            if (i!=null){
                wordsMap.replace(str,wordsMap.get(str)+1);
            }
        }
        return wordsMap;
    }

    public static void main(String[] args) throws Exception{
        UsageFrequency usageFrequency = new UsageFrequency();
        usageFrequency.processFile("wiki.test.tokens");
        System.out.println(usageFrequency.getWords());
    }
}
