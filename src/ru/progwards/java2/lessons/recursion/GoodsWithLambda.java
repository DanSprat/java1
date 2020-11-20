package ru.progwards.java2.lessons.recursion;

import java.time.Instant;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class GoodsWithLambda {
    private ArrayList<Goods> goods;

    public void setGoods(List<Goods> list){
        goods = new ArrayList<>(list);
    }

    public List<Goods> sortByName(){
        goods.sort(Comparator.comparing(a->a.name));
        return goods;
    }

    public List<Goods> sortByNumber(){
        goods.sort(Comparator.comparing(x -> x.number.toLowerCase()));
        return goods;
    }

    public List<Goods> sortByPartNumber(){
        goods.sort(Comparator.comparing(x->x.number.substring(0,3).toLowerCase()));
        return goods;
    }
    public List<Goods> sortByAvailabilityAndNumber(){
        goods.sort((x,y)-> x.available!=y.available ? Integer.compare(x.available,y.available) : x.number.toLowerCase().compareTo(y.number.toLowerCase()));
        return goods;
    }

    public List<Goods> expiredAfter(Instant date){
        ArrayList<Goods> filtered = (ArrayList<Goods>) goods.stream().filter(x -> x.expired.isBefore(date)).collect(Collectors.toList());
        filtered.sort(Comparator.comparing(x->x.expired));
        return filtered;
    }

    public List<Goods> сountLess(int count){
        ArrayList<Goods> filtered = (ArrayList<Goods>) goods.stream().filter(x->x.available<count).collect(Collectors.toList());
        filtered.sort(Comparator.comparing(x->x.available));
        return filtered;
    }

    public List<Goods> сountBetween(int count1, int count2){
        Predicate<Goods> lower = x -> x.available<count1;
        ArrayList<Goods> filtered = (ArrayList <Goods>)goods.stream().filter(lower.and(x->x.available>count2));
        filtered.sort(Comparator.comparing(x->x.available));
        return filtered;
    }

    public static void main(String[] args) {

    }

}
