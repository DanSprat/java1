package ru.progwards.java2.lessons.generics;

import java.util.ArrayList;
import java.util.Arrays;

public class DynamicArray <T>{
    private T arr [];
    private int size;
    public DynamicArray() {
        size = 0;
    }

    public void add (T val) {
        if (size == 0){
            arr = (T[])new Object[1];
            arr[size++] = val;
        } else{
            if (size!=arr.length){
                arr[size++] = val;
            } else {
                T array [] = Arrays.copyOf(arr,arr.length);
                arr =(T[]) new Object[2*arr.length];
                System.arraycopy(array,0,arr,0, array.length);
                arr[size++] = val;
            }
        }
    }
     public T get(int pos){
        if (pos>=size || pos<0)
            throw new IndexOutOfBoundsException();
        return arr[pos];
    }

    public void insert(int pos,T val){
        if (pos>=size || pos<0)
            throw new IndexOutOfBoundsException();
        arr[pos] = val;
    }
    public void remove(int pos){
        if (pos>=size || pos<0)
            throw new IndexOutOfBoundsException();
        T [] array = Arrays.copyOf(arr,arr.length);
        arr  =(T[]) new Object[arr.length-1];
        System.arraycopy(array,0,arr,0,pos);
        System.arraycopy(array,pos+1,arr,pos,arr.length-pos-1);
        size--;
    }
    public int size(){
        return size;
    }

    public static void main(String[] args) {
        DynamicArray<Integer> array =  new DynamicArray<>();
        array.add(0);
        array.add(1);
        array.add(2);
        array.add(3);
        array.add(4);
        array.remove(3);
        array.remove(3);
        array.remove(0);
        array.add(5);
    }
}
