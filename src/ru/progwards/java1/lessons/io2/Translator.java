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
                  tmp = tmp.replace(inLang[i],outLang[i].replaceFirst(String.valueOf(outLang[i].charAt(0)),String.valueOf(Character.toUpperCase(outLang[i].charAt(0)))));
                } else {
                    tmp = tmp.replace(inLang[i],outLang[i]);
                }
            }
        }
        return tmp;
    }

    public static void main(String[] args) {
        Translator translator = new Translator(new String[]{"make", "love", "not", "war"},new String[]{"твори", "любовь", "не", "войну"});
        System.out.println(translator.translate("make love not war"));
    }
}
