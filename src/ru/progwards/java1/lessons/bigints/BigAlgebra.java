package ru.progwards.java1.lessons.bigints;

import java.math.BigDecimal;
import java.math.BigInteger;

public class BigAlgebra {
    public static BigDecimal fastPow(BigDecimal num, int pow){
        BigDecimal num2 = num;
        int y =1;
        while (y<=pow){
            y<<=1;
        }
        y>>>=2;
        for (int x =y;x>0;x>>>=1){
            if ((pow & x) > 0)
                num = num.multiply(num).multiply(num2);
            else
                num = num.multiply(num);
        }
        return num;
    }
    public static BigInteger fibonacci(int n){
        if (n<=2)
            return new BigInteger("1");
        BigInteger first = new BigInteger("1");
        BigInteger second = new BigInteger("1");
        for (int i = 3;i<=n;++i){
            BigInteger temp = first.add(second);
            first = second;
            second = temp;
        }
        return second;
    }
    public static void main(String[] args) {
        BigDecimal bigDecimal = new BigDecimal("21");
        BigDecimal bigDecimal1 = BigAlgebra.fastPow(bigDecimal,13);
        System.out.println(bigDecimal1);
        BigDecimal bigDecimal2 = new BigDecimal(String.valueOf(bigDecimal));
        System.out.println(fibonacci(10));
    }
}
