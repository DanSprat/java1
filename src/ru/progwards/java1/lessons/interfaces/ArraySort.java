package ru.progwards.java1.lessons.interfaces;

import java.util.Arrays;

public class ArraySort {
    public static void sort(CompareWeight[] a){
        for(int i=0;i<a.length-1;++i)
            for(int j=i+1;j<a.length;++j)
                if (a[j].compareWeight(a[i]) == CompareWeight.CompareResult.LESS)
                {
                    CompareWeight b = a[i];
                    a[i]=a[j];
                    a[j]=b;
                }
    }

    public static void main(String[] args) {

    }
}
