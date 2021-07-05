package ru.progwards.java1.lessons.sort;

import ru.progwards.java2.lessons.patterns.IntegerFactory;

import java.util.Arrays;
import java.util.Collections;

public class MergeSort {
    public static <T extends Comparable<T>> void  MergeSort(T [] arr,int left,int right) {
        int mid = (right+left) /2;
        if (right - left != 1) {
            MergeSort(arr, left, mid );
            MergeSort(arr, mid, right);
            Merge(arr,left,right);
        }
    }

    private static <T extends  Comparable<T>> void Merge(T [] dest,int left,int right){
        int size = right - left;
        int sizeFirstArr = (right - left) / 2;
        int sizeSecondArr = size - sizeFirstArr;
        T [] First= (T[]) new Comparable [sizeFirstArr];
        T [] Second = (T[]) new Comparable [sizeSecondArr];
        System.arraycopy(dest,left,First,0,sizeFirstArr);
        System.arraycopy(dest,left+sizeFirstArr,Second,0,sizeSecondArr);
        int i = left;
        int m =0;
        int n =0;
        while (m < sizeFirstArr && n < sizeSecondArr){
            if (First[m].compareTo(Second[n]) < 0){
                dest[i++] = First[m++];
            } else {
                dest [i++] = Second[n++];
            }
        }
        int remains =0;
        int last =0;
        T [] rem;
        if (m == sizeFirstArr){
            remains = n;
            last = sizeSecondArr;
            rem = Second;
        } else {
            remains = m;
            last = sizeFirstArr;
            rem = First;
        }
        for (int k = remains;k<last; k++){
            dest[i++] = rem[k];
        }
    }

    public static void main(String[] args) {
        int arr [] = {1,5,9,2,6,3};
        Integer intArr [] = Arrays.stream(arr).boxed().toArray(Integer[]::new);
        MergeSort(intArr,0,arr.length);
        for (var x: intArr){
            System.out.print(x+ " ");
        }
    }
}
