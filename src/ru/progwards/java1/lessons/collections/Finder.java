package ru.progwards.java1.lessons.collections;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;

public class Finder {
    public static Collection<Integer> findMinSumPair(Collection<Integer> numbers) {
        Collection<Integer> res = new ArrayList<>();
        Iterator<Integer> iterator = numbers.iterator();
        Integer minIndex=1;
        Integer prev;
        Integer min = null;
        if (iterator.hasNext())
            prev = iterator.next();
        else {
            res.add(-1);
            res.add(-1);
            return res;
        }
        for (int i = 1; iterator.hasNext(); i++) {
            Integer numb = iterator.next();
            if (min == null || numb+prev<min) {
                minIndex = i;// Вычесть единицу в конце
                min = prev + numb;
                prev = numb;
            }
        }
      res.add(minIndex-1);
        res.add(minIndex);
        return res;

    }
    public static Collection<Integer> findLocalMax(Collection<Integer> numbers){
        Collection<Integer> res = new ArrayList<>();
        if (numbers.size()<3){
            return res;
        }
        Iterator<Integer> iterator = numbers.iterator();
        Integer left = iterator.next();
        Integer middle = iterator.next();
        for (int i=2;iterator.hasNext();i++){
            Integer right = iterator.next();
            if (left<middle && middle>right)
                res.add(middle);
            left =middle;
            middle=right;
        }
        return res;
    }
    public static boolean findSequence(Collection<Integer> numbers){
        for (int i =1;i<=numbers.size();i++){
            if (!numbers.contains(i))
                return false;
        }
        return true;
    }
    public static String findSimilar(Collection<String> names){
        int count = 1;
        int maxCount =1;
        String maxString;
        String prev;
        Iterator<String> iterator = names.iterator();
        if (iterator.hasNext()) {
            prev = iterator.next();
            maxString= prev;
        }
        else {
            return  "Список пуст";
        }
        while(iterator.hasNext()){
            String newString = iterator.next();
            if (newString.equals(prev)){
                count++;
                if (count>maxCount){
                    maxCount = count;
                    maxString = newString;
                }
            } else {
                count = 1;
            }
            prev = newString;
        }
            return maxString + ":" + maxCount;
    }

    public static void main(String[] args) {
        ArrayList<Integer> list = new ArrayList<>();
        ArrayList<String> stringArrayList = new ArrayList<>();
        stringArrayList.add("Дмитрий");
        stringArrayList.add("Дмитрий");
        stringArrayList.add("Василий");
        stringArrayList.add("Василий");
        stringArrayList.add("Дмитрий");
        stringArrayList.add("Дмитрий");
        stringArrayList.add("Дмитрий");
        stringArrayList.add("Дмитрий");
        int [] array = {92,91,32,-58,33,45,-94,8,-44,93,96,75,-70};
        for (int x:array)
            list.add(x);
        System.out.println(findMinSumPair(list));
        System.out.println(findLocalMax(list));
        System.out.println(findSequence(list));
        System.out.println(findSimilar(stringArrayList));
    }
}
