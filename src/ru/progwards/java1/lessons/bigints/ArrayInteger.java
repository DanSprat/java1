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
        int max = Integer.max(LastIndex,num.LastIndex);
        if (max > digits.length-1){
            for (int i =0;i<=LastIndex;++i)
                digits[i]=0;
            return false;
        }
        else
            for(int i =0;i<max;++i){
                digits[i+1] =(byte) (digits[i+1]+ ((num.digits[i] + digits[i])/10));
                digits[i] = (byte) ((num.digits[i] + digits[i])%10);

            }
            if (max!=digits.length-1){
                digits[max+1] =(byte)(digits[max+1]+ ((num.digits[max] + digits[max])/10));
                digits[max] = (byte) ((num.digits[max] + digits[max])%10);

                LastIndex++;
                return true;
            }
            else if (num.digits[max] + digits[max] <10){
                digits[max] = (byte) ((num.digits[max] + digits[max])%10);
                return true;
            }
            else {
                for (int i =0;i<=LastIndex;++i)
                    digits[i]=0;
                    return false;
            }

    }
    public static void main(String[] args) {
        ArrayInteger arrayInteger = new ArrayInteger(10);
        ArrayInteger arrayInteger1 = new ArrayInteger(10);
        arrayInteger.fromInt(new BigInteger("934"));
        arrayInteger1.fromInt(new BigInteger("100"));
        System.out.println(arrayInteger.add(arrayInteger1));
        System.out.println(arrayInteger.toInt());

    }
}
