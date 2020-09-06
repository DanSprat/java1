package ru.progwards.java1.lessons.interfaces;

public class Food implements CompareWeight{
    private int weight;
    public int getWeight(){return weight;}


    @Override
    public CompareResult compareWeight(CompareWeight smthHasWeigt) {
        Food food = (Food) smthHasWeigt;
        if (weight <food.weight) return CompareResult.LESS;
        else if (weight>food.weight) return CompareResult.GREATER;
        else return CompareResult.EQUAL;
    }
}
