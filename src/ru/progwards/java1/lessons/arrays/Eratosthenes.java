package ru.progwards.java1.lessons.arrays;

import java.lang.reflect.Array;
import java.util.Arrays;

public class Eratosthenes {
    private boolean[] sieve;
    public Eratosthenes(int N){
        sieve = new boolean[N];
        Arrays.fill(sieve,true);
        sift();

    }
    private void sift(){
        for (int i=2;i<sieve.length-1;i++){
            if(sieve[i]!=false)
                for(int j=2*i;j<sieve.length;j+=i)
                    sieve[j]=false;
        }
    }
    public boolean isSimple(int n){
        if (n<2)
            return false;
            else return sieve[n];
    }

    public static void main(String[] args) {
        Eratosthenes eratosthenes = new Eratosthenes(20);
        System.out.println(eratosthenes.isSimple(16));

    }
}
