package ru.progwards.java1.lessons.classes;

import ru.progwards.java1.lessons.interfaces.CompareWeight;

enum AnimalKind  {ANIMAL, COW, HAMSTER, DUCK;}
enum FoodKind {UNKNOWN, HAY, CORN;}
public class Animal implements FoodCompare, CompareWeight {
    @Override
    public boolean equals(Object anObject){
        if (this == anObject) return true;
        if (anObject == null || getClass() !=anObject.getClass()) return false;
        Animal animal = (Animal) anObject;
        if (weight == animal.weight) return true;
        return false;
    }
    public double getFood1kgPrice() {
        switch (Fkind){
            case HAY: return 20;
            case CORN: return 50;
            case UNKNOWN: return 0;
            default: return 0;
        }
    }
    public double getFoodPrice() {
       return calculateFoodWeight() * getFood1kgPrice();
    }
    public int compareFoodPrice(Animal aminal){
        return  Double.compare(getFoodPrice(),aminal.getFoodPrice());
    }
    @Override
    public CompareResult compareWeight(CompareWeight smthHasWeigt) {
        Animal animal = (Animal) smthHasWeigt;
        if (weight <animal.weight) return CompareResult.LESS;
        else if (weight>animal.weight) return CompareResult.GREATER;
        else return CompareResult.EQUAL;

    }
    protected double weight;
    protected AnimalKind Akind = AnimalKind.ANIMAL;
    protected FoodKind Fkind=FoodKind.UNKNOWN;
    public Animal(double weight){
        this.weight = weight;
    }
    public double getWeight() {return weight;}
    public double getFoodCoeff() {return  0.02;}
    public double calculateFoodWeight() {return weight*getFoodCoeff();}
    public AnimalKind getKind() {return Akind;}
    public FoodKind getFoodKind(){return  Fkind;}
    public String toStringFull(){return "I am "+Akind+", eat "+Fkind+" "+calculateFoodWeight();}
    public String toString(){return "I am "+Akind+", eat "+Fkind;}
    public static void main(String[] args) {
        Animal a = new Animal(10);
        System.out.println(new Duck(20).toStringFull());
    }


}
