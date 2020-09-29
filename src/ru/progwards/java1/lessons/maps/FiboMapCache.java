package ru.progwards.java1.lessons.maps;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

public class FiboMapCache {
    private Map<Integer, BigDecimal> fiboCache;
    public FiboMapCache(boolean cacheOn){
        if (cacheOn==true){
            fiboCache = new HashMap<>();
            fiboCache.put(1,new BigDecimal("1"));
            fiboCache.put(2,new BigDecimal("1"));
        }
    }
   /* public BigDecimal fiboNumber(int n){
        if (fiboCache!=null){
            if (n<=2){
                return fiboCache.get(n);
            } else {
                BigDecimal first = fiboCache.get(1);
                BigDecimal second = fiboCache.get(2);
                for (int i =3;i<=n;i++){
                    BigDecimal j= fiboCache.get(i);
                    if (j==null){
                        j = first.add(second);
                    }
                    first = second;
                    second=j;
                }
                return second;
            }
        } else {
            BigDecimal first = new BigDecimal("1");
            BigDecimal second = new BigDecimal("1");
            if (n<=2){
                return second;
            } else {
                for (int i =3;i<=n;i++){
                    BigDecimal j = first.add(second);
                    first = second;
                    second=j;
                }
                return second;
            }
        }
    }
*/
    public BigDecimal fiboNumber(int n){
        if (fiboCache!=null){
            if (n<=2){
                return fiboCache.get(n);
            } else {
                return fiboNumber(n-1);
            }
        } else {
            BigDecimal first = new BigDecimal("1");
            BigDecimal second = new BigDecimal("1");
            if (n<=2){
                return second;
            } else {
                for (int i =3;i<=n;i++){
                    BigDecimal j = first.add(second);
                    first = second;
                    second=j;
                }
                return second;
            }
        }
    }
    public void clearCahe(){
        fiboCache= null;
    }
    public static void test(){
        FiboMapCache fiboMapCache = new FiboMapCache(true);
        long FirstTime = System.currentTimeMillis();
        for(int i=1;i<=1000;i++){
            System.out.println(fiboMapCache.fiboNumber(i));
        }
        FirstTime = System.currentTimeMillis()-FirstTime;
        fiboMapCache.clearCahe();
        long SecondTime = System.currentTimeMillis();
        for(int i=1;i<=1000;i++){
            System.out.println(fiboMapCache.fiboNumber(i));
        }
        SecondTime = System.currentTimeMillis()-SecondTime;
        System.out.println("fiboNumber cacheOn "+ true + " время выполнения "+FirstTime);
        System.out.println("fiboNumber cacheOn "+ false + " время выполнения "+SecondTime);
    }
    public static void main(String[] args) {
        FiboMapCache.test();
    }
}
