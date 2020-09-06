package ru.progwards.java1.lessons.interfaces;

public class CalculateFibonacci {
    public static class CacheInfo{
        public int n;
        public int fibo;
        CacheInfo(int n,int fibo){
            this.n = n;
            this.fibo = fibo;
        }
    }
    public static CacheInfo getLastFibo(){return CalculateFibonacci.lastFibo;}
    public static void clearLastFibo(){CalculateFibonacci.lastFibo =null;}
    private static CacheInfo lastFibo;
    public static int fiboNumber(int n){
        if (CalculateFibonacci.lastFibo !=null)
        if (n == CalculateFibonacci.lastFibo.n)
            return CalculateFibonacci.lastFibo.fibo;
        int first=1;int second=1;
        if (n<=2){
            CalculateFibonacci.lastFibo = new CacheInfo(n,first);
            return first;
        }
        else {
            for(int i = 3;i<=n;i++){
                int temp = first+second;
                first = second;
                second=temp;
            }
            CalculateFibonacci.lastFibo = new CacheInfo(n,second);
            return second;
        }
    }

    public static void main(String[] args) {
        CalculateFibonacci calculateFibonacci = new CalculateFibonacci();
        System.out.println(calculateFibonacci.fiboNumber(10));
        CalculateFibonacci calculateFibonacci2 = new CalculateFibonacci();
        System.out.println(calculateFibonacci2.fiboNumber(10));
        System.out.println(calculateFibonacci.getLastFibo().fibo);
    }
}
