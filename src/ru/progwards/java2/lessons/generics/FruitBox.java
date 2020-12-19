package ru.progwards.java2.lessons.generics;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FruitBox <T extends Fruit> extends ArrayList {
    public FruitBox(){
         super();
    }
    public float getWeight(){
        if (size() ==0) return 0;
        Fruit fruit = (Fruit) super.get(0);
        return size()*fruit.weight;
    }


    public boolean add(Object o) {
        if (o instanceof Fruit){
            if (size() == 0 || o.getClass().equals(super.get(0).getClass())){
                super.add(o);
                return true;
            } else {
                return false;
            }
        }
        return false;
    }

    @Override
    public String toString(){
        return null;
    };
    public void moveTo(FruitBox  box){
        if (size() == 0 ) return;
        if (box.size() == 0 || box.get(0).getClass().equals(this.get(0).getClass())){
            for (int i=0;i<size();i++)
                box.add(super.get(i));
            this.clear();
        } else {
            throw new UnsupportedOperationException();
        }

    }
    public int compareTo(FruitBox box){
        if (getWeight() == box.getWeight()) return 0;
        if (getWeight()< box.getWeight()) return  -1;
        return 1;
    }

    public static void main(String[] args) {
        FruitBox <Orange> orangeFruitBox = new FruitBox<>();
        FruitBox <Apple> appleFruitBox = new FruitBox<>();
        FruitBox<Apple> appleFruitBox1 = new FruitBox<>();
        appleFruitBox1.add(new Apple());
        appleFruitBox.add(new Apple());
        appleFruitBox.moveTo(appleFruitBox1);
        System.out.println(appleFruitBox.compareTo(appleFruitBox1));

    }
}
