package ru.progwards.java1.lessons.sets;

import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

public class SetOperations {
    public static Set<Integer> union(Set<Integer> set1, Set<Integer> set2){
        Set<Integer> unionSet = new LinkedHashSet<>(set1);
        unionSet.addAll(set2);
        return unionSet;
    }
    public static Set<Integer> intersection(Set<Integer> set1, Set<Integer> set2){
        Set<Integer> intersectionSet = new LinkedHashSet<>(set1);
        intersectionSet.retainAll(set2);
        return intersectionSet;
    }
    public static Set<Integer> difference(Set<Integer> set1, Set<Integer> set2){
        Set<Integer> differenceSet = new LinkedHashSet<>(set1);
        differenceSet.removeAll(intersection(set1,set2));
        return differenceSet;
    }
    public static Set<Integer> symDifference(Set<Integer> set1, Set<Integer> set2){
        Set<Integer> symDifference = union(set1,set2);
        symDifference.removeAll(intersection(set1,set2));
        return symDifference;
    }

    public static void main(String[] args) {
        HashSet<Integer> set1 = new HashSet<>();
        HashSet<Integer> set2 = new HashSet<>();
        set1.addAll(List.of(1,2,3,4,5,6));
        set2.addAll(List.of(3,4,5,6,7,8));
        System.out.println(union(set1,set2));
        System.out.println(intersection(set1,set2));
        System.out.println(difference(set1,set2));
        System.out.println(symDifference(set1,set2));
    }
}
