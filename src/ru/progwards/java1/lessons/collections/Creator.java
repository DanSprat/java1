package ru.progwards.java1.lessons.collections;

import java.util.ArrayList;
import java.util.Collection;

public class Creator {
    public static Collection<Integer> fillEven(int n){
        Collection<Integer> collection = new ArrayList<Integer>();
        for (int i=0;i<n;i++)
        {
            collection.add(2*i+2);
        }
        return collection;
    }

    public static Collection<Integer> fillOdd(int n){
        Collection<Integer> collection = new ArrayList<Integer>();
        for (int i=0;i<n;i++)
        {
            collection.add(1-2*i);
        }
        return collection;
    }
    public static Collection<Integer> fill3(int n){

        Collection<Integer> collection = new ArrayList<Integer>();
        for (int i=0;i<n;i++)
        {
           collection.add(3*i);
           collection.add(3*3*i*i);
            collection.add(3*3*3*i*i*i);
        }
        return collection;
    }

    public static void main(String[] args) {
        Collection <Integer> collection = fillEven(10);
        System.out.println(collection);
        collection = fill3(5);
        System.out.println(collection);
    }
}
