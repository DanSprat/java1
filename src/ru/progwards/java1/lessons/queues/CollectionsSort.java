package ru.progwards.java1.lessons.queues;

import ru.progwards.java1.lessons.interfaces.CompareWeight;

import java.util.*;

public class CollectionsSort {
    static class Method implements Comparable<Method> {
        String name;
        long time;

        public Method(String name, long time) {
            this.name = name;
            this.time = time;
        }

        @Override
        public int compareTo(Method method) {
            int res = Long.compare(time,method.time);
            if (res!=0) return res;
            else  return name.compareTo(method.name);
        }
    }

    public static void mySort(Collection<Integer> data){
       ArrayList<Integer> arrayList = new ArrayList<>(data);
        for(int i=0;i<arrayList.size()-1;++i) {
            for (int j = i + 1; j < arrayList.size(); ++j) {
                if (arrayList.get(j).compareTo(arrayList.get(i)) == -1) {
                    Integer b = arrayList.get(i);
                    arrayList.set(i, arrayList.get(j));
                    arrayList.set(j, b);
                }
            }
        }
       data.clear();
        data.addAll(arrayList);

    }
    public static void minSort(Collection<Integer> data){
        ArrayList<Integer> newList = new ArrayList<>();
        while (!data.isEmpty()) {
        newList.add(Collections.min(data));
        data.remove(Collections.min(data));
        }
       data.addAll(newList);
    }
    static void collSort(Collection<Integer> data){
        ArrayList<Integer> list = new ArrayList<>(data);
        Collections.sort(list);
        data.clear();
        data.addAll(list);
    }
    public static Collection<String> compareSort(){


        ArrayList<Integer> arrayList = new ArrayList<>();
        for (int i =1;i<50000;i++){
            arrayList.add((int)Math.random()*500000);
        }
        ArrayList <Integer> MySort = new ArrayList<>(arrayList);
        long TimeMySort = System.currentTimeMillis();
        mySort(MySort);
        TimeMySort = System.currentTimeMillis() - TimeMySort;

        ArrayList <Integer> MinSort = new ArrayList<>(arrayList);
        long TimeMinSort = System.currentTimeMillis();
        minSort(MinSort);
        TimeMinSort = System.currentTimeMillis() - TimeMinSort;

        ArrayList <Integer> CollSort = new ArrayList<>(arrayList);
        long TimeCollSort = System.currentTimeMillis();
        collSort(CollSort);
        TimeCollSort = System.currentTimeMillis() - TimeCollSort;

        ArrayList<Method> methods = new ArrayList<>();
        methods.add(new Method("MySort",TimeMySort));
        methods.add(new Method("MinSort",TimeMinSort));
        methods.add(new Method("CollSort",TimeCollSort));
        Collections.sort(methods);
        Collections.reverse(methods);
        Collection <String> res = new ArrayList<>( List.of(methods.get(0).name,methods.get(1).name,methods.get(2).name));
        return res;
    }
    public static void main(String[] args) {
        for (String x: compareSort()){
            System.out.println(x);
        }
        ArrayList<Integer> arrayList = new ArrayList<>(List.of(1,3,5,7,2));
        System.out.println(arrayList);
        mySort(arrayList);
        System.out.println(arrayList);
        ArrayList<Integer> arrayList1 = new ArrayList<>(List.of(1,3,5,7,2));
        System.out.println(arrayList1);
        minSort(arrayList1);
        System.out.println(arrayList1);
        TreeSet<Integer> arrayList2 = new TreeSet<>(List.of(1,3,5,7,2));
        System.out.println(arrayList2);
        collSort(arrayList2);
        System.out.println(arrayList2);
    }

}
