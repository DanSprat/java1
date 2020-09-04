package ru.progwards.java1.lessons.arrays;

import java.util.Arrays;

public class DIntArray {
    private int [] arr;
    DIntArray(){}
    public void add(int num){
        if(arr == null){
            arr = new int [1];
            arr[0] = num;
        }
        else {
            int[] arrCopy = new int[arr.length];
            System.arraycopy(arr, 0, arrCopy, 0, arr.length);
            arr = new int[arrCopy.length + 1];
            System.arraycopy(arrCopy, 0, arr, 0, arrCopy.length);
            arr[arr.length - 1] = num;
            System.out.println(Arrays.toString(arr));
        }
    }

    public int at(int pos){return arr[pos];}
    public void atDelete(int pos){
        int[] arrCopy = new int[arr.length-1];
        System.arraycopy(arr, 0, arrCopy, 0, pos);
        System.arraycopy(arr, pos+1, arrCopy, pos, arr.length-pos-1);
        arr = new int [arrCopy.length];
        System.arraycopy(arrCopy,0,arr,0,arrCopy.length);

    }
    public void atInsert(int pos, int num){
        int[] arrCopy = new int[arr.length+1];
        System.arraycopy(arr,0,arrCopy,0,pos);
        arrCopy[pos]= num;
        System.arraycopy(arr,pos,arrCopy,pos+1,arr.length-pos);
        arr = new int [arrCopy.length];
        System.arraycopy(arrCopy,0,arr,0,arrCopy.length);
    }
    public static void main(String[] args) {
        int [] arr = {1,2,3,4};
        DIntArray intArray = new DIntArray();
        intArray.add(0);
        intArray.add(1);
        intArray.add(2);
        intArray.add(3);
        intArray.add(4);
        intArray.atDelete(2);
        intArray.atInsert(2,2);
    }

}
