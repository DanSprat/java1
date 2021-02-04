package ru.progwards.java2.lessons.recursion;

public class AsNumbersSum {
    public static String asNumbersSum(int number){
       StringBuffer string = new StringBuffer();
       StringBuffer prev = new StringBuffer();
       asNumberSum(number,0,number,number+1,string,prev);
       return string.toString();
    }

    private static void asNumberSum(int number,int sum,int max,int prev,StringBuffer out, StringBuffer middle){
        if (number==0) return;
        if (number > prev){
            asNumberSum(number-1,sum,max,prev,out,middle);
        } else {
            if (number + sum == max){
                if (middle.length() == 0){
                    if (out.length()!=0) {
                        out.append(" = " + number);
                    } else {
                        out.append( number);
                    }
                } else {
                    if (out.length()!=0) {
                        out.append(" = " + middle + "+" + number);
                    } else {
                        out.append(middle + "+" + number);
                    }
                }
            } else {
                StringBuffer newS;
                if (middle.length() == 0){
                    newS = new StringBuffer(Integer.toString(number));
                } else {
                    newS = new StringBuffer(middle+ "+"+number);
                }
                prev = number;
                asNumberSum(max-(sum+number),sum+number,max,prev,out,newS);
            }
            asNumberSum(number-1,sum,max,prev,out,middle);
        }

    }
    public static void main(String[] args) {
        String string = asNumbersSum(7);
        System.out.println(string);
    }
}
