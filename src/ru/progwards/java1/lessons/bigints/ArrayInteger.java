package ru.progwards.java1.lessons.bigints;

import java.math.BigInteger;

public class ArrayInteger {
    byte[] digits;
    int LastIndex;
    ArrayInteger(int n){
        digits = new byte[n];
    }
    void fromInt(BigInteger value){
        for (int i =0;value.compareTo(BigInteger.ZERO)!=0;++i){
            digits[i] = value.mod(BigInteger.TEN).byteValue();
            value = value.divide(BigInteger.TEN);
            LastIndex=i;
        }
    }
    BigInteger toInt(){
        String str = "";
        for (int i =0;i<=LastIndex;++i){
            str = digits[i] + str;
        }
        return new BigInteger(str);
    }
    boolean add(ArrayInteger num){
     if (num.LastIndex >= digits.length){
         for(int i =0;i<digits.length;i++)
             digits[i]=0;
         LastIndex =0;
         return false;
     }
     int max = Integer.max(LastIndex,num.LastIndex);
     byte result;
     for (int i =0;i<max;++i){
         result =  (byte) (digits[i] + num.digits[i]);
         if (result >=10)
             digits[i+1]++;
         digits[i] = (byte)(result%10);
     }
        result =  (byte) (digits[max] + num.digits[max]);
     if (result>=10) {
         if (max >= digits.length - 1) {
             for (int i = 0; i < digits.length; i++)
                 digits[i] = 0;
             LastIndex=0;
             return false;
         } else {
             LastIndex = max + 1;
             digits[max] = (byte) (result % 10);
             digits[max + 1]++;
             return true;
         }
     }
     else {
      digits[max] = result;
      LastIndex=max;
      return true;
     }


    }
    public static void main(String[] args) {
        ArrayInteger arrayInteger = new ArrayInteger(3);
        ArrayInteger arrayInteger1 = new ArrayInteger(3);
        arrayInteger.fromInt(new BigInteger("10"));
        arrayInteger1.fromInt(new BigInteger("999"));
        System.out.println(arrayInteger.add(arrayInteger1));
        System.out.println(arrayInteger.toInt());

    }
}
