package ru.progwards.java2.lessons.generics;

public class ArraySort {
    public static <T extends Comparable> void sort  (T [] arr) {
        for (int i = 0;i<arr.length;i++)
            for(int j=i;j<arr.length;j++){
               if (arr[i].compareTo(arr[j])>0){
                   T temp = arr[i];
                   arr[i] =arr[j];
                   arr[j] = temp;
               }
            }
    }

    public static void main(String[] args) {
        Integer arr [] = {2,3,5,1,4};
        int String [] = new int [5];

        sort(arr);
        for (int i=0;i<arr.length;i++)
            System.out.print(arr[i] + " ");
    }

    // Expected: 1 2 3 4 5
}
