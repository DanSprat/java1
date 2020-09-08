package ru.progwards.java1.lessons.bigints;

import java.math.BigInteger;

public class ArrayInteger {
    byte[] digits;
    int LastIndex;

    ArrayInteger(int n) {
        digits = new byte[n];
    }

    void fromInt(BigInteger value) {
        for (int i = 0; value.compareTo(BigInteger.ZERO) != 0; ++i) {
            digits[i] = value.mod(BigInteger.TEN).byteValue();
            value = value.divide(BigInteger.TEN);
            LastIndex = i;
        }
    }

    BigInteger toInt() {
        String str = "";
        for (int i = 0; i <= LastIndex; ++i) {
            str = digits[i] + str;
        }
        return new BigInteger(str);
    }

    boolean add(ArrayInteger num) {
        if (num.LastIndex > digits.length - 1) {
            for (int i = 0; i < digits.length; i++) {
                digits[i] = 0;
            }
            LastIndex = 0;
            return false;
        }
        int min = Integer.min(digits.length, num.digits.length);
        int result = 0;
        for (int i = 0; i < min - 1; i++) {
            result = digits[i] + num.digits[i];
            digits[i] = (byte) (result % 10);
            digits[i + 1] += (byte) (result / 10);
        }
        digits[min-1] = (byte)(digits[min-1] +num.digits[min-1]);
        while (min-1<digits.length-1 && digits[min-1]>=10)
        {
            digits[min-1]%=10;
            digits[min++]++;
        }
        if (min == digits.length && digits[min-1]>=10){
            for (int i = 0; i < digits.length; i++) {
                digits[i] = 0;
            }
            LastIndex = 0;
            return false;
        }
return true;
    }
    public static void main(String[] args) {
        ArrayInteger arrayInteger = new ArrayInteger(3);
        ArrayInteger arrayInteger1 = new ArrayInteger(1);
        arrayInteger.fromInt(new BigInteger("109"));
        arrayInteger1.fromInt(new BigInteger("2"));
        System.out.println(arrayInteger.add(arrayInteger1));
        System.out.println(arrayInteger.toInt());

    }
}
