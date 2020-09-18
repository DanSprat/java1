package ru.progwards.java1.lessons.io2;

public class Translator {

    private String [] inLang;
    private String [] outLang;

    Translator(String[] inLang, String[] outLang){
       this.inLang = inLang;
       this.outLang=outLang;
    }
    public String translate(String sentence){
        String tmp = sentence.toLowerCase();
        for (int i=0;i<inLang.length;i++){
            if(tmp.contains(inLang[i])){
                if(Character.isUpperCase(sentence.charAt(tmp.indexOf(inLang[i])))){
                   tmp.replace(inLang[i],outLang[i].replaceFirst(String.valueOf(outLang[i].charAt(0)),String.valueOf(Character.toUpperCase(outLang[i].charAt(0)))));
                } else {
                    tmp.replace(inLang[i],outLang[i]);
                }
            }
        }
        return tmp;
    }
}
