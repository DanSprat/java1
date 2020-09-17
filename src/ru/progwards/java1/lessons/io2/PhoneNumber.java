package ru.progwards.java1.lessons.io2;

public class PhoneNumber {
    public static String format(String phone)throws Exception{
        StringBuffer stringBuffer= new StringBuffer();
        for (char c: phone.toCharArray()){
            if (Character.isDigit(c))
                stringBuffer.append(c);
        }
        if (stringBuffer.length() == 10)
          stringBuffer.insert(0,"7");
        else {
            if (stringBuffer.length() == 11){
              if(stringBuffer.charAt(0)=='8' || (stringBuffer.charAt(0)=='7'))
                stringBuffer.setCharAt(0,'7');
              else throw new Exception("Incorrect number");
            } else throw new Exception("Incorrect number");
        }
        stringBuffer.insert(0,'+');
        stringBuffer.insert(2,'(');
        stringBuffer.insert(6,')');
        stringBuffer.insert(10,'-');
        return stringBuffer.toString();
    }
    public static void main(String[] args) throws Exception {
        System.out.println(format("9875554641"));
    }
}
