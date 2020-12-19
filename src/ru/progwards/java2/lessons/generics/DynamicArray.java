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
        if (size == arr.length){
            T array [] = Arrays.copyOf(arr,arr.length);
            arr =(T[]) new Object[2*arr.length];
            System.arraycopy(array,0,arr,0,pos);
            System.arraycopy(array,pos,arr,pos+1,size-pos);
            arr[pos]=val;
        } else {
            for (int i = size -1;i>=pos;--i){
                arr[i] =arr[i+1];
            }
            arr[pos] = val;
        }
        size++;
    }
    public void remove(int pos){
        if (pos>=size || pos<0)
            throw new IndexOutOfBoundsException();
        if (pos == size-1){
            arr[pos] =null;
        } else {
            for (int i = pos + 1; i < size; ++i) {
                arr[i-1] = arr[i];
            }
        }
        size--;
    }
    public int size(){
        return size;
    }

    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>();
        arrayList.add(0);
        arrayList.add(1);
        DynamicArray<Integer> list =  new DynamicArray<>();
        list.add(0);
        list.add(2);
        list.insert(1,1);
        System.out.println(list.get(2));
    }
}
