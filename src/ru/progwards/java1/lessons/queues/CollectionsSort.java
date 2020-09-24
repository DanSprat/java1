package ru.progwards.java1.lessons.queues;

import ru.progwards.java1.lessons.interfaces.CompareWeight;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class CollectionsSort {
    public static void mySort(Collection<Integer> data){
       ArrayList<Integer> arrayList = new ArrayList<>(data);
        for(int i=0;i<arrayList.size()-1;++i)
            for(int j=i+1;j<arrayList.size();++j)
                if (arrayList.get(j).compareTo(arrayList.get(i))==-1)
                {
                    Integer b = arrayList.get(i);
                    arrayList.set(i,arrayList.get(j));
                    arrayList.set(j,b);
                }
       data.clear();
       for (Integer x:arrayList){
           data.add(x);
       }
    }

    public static void main(String[] args) {
        ArrayList<Integer> arrayList = new ArrayList<>(List.of(1,3,5,7,2));
        System.out.println(arrayList);
        mySort(arrayList);
        System.out.println(arrayList);
    }
}
