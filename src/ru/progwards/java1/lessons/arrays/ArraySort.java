package ru.progwards.java1.lessons.arrays;

import java.util.Arrays;

public class ArraySort {
    public static void sort(int[] a){
        for(int i=0;i<a.length-1;++i)
            for(int j=i+1;j<a.length;++j)
                if (a[j]<a[i])
                {
                    int b = a[i];
                    a[i]=a[j];
                    a[j]=b;
                }
    }

    public static void main(String[] args) {
       int a[] = {1,3,2,5,4,9,6};
       sort(a);
       System.out.println((Arrays.toString(a)));
    }
}
