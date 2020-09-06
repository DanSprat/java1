package ru.progwards.java1.lessons.interfaces;

public class Duck extends Animal {
    public Duck(double weight) {
        super(weight);
        Akind= AnimalKind.DUCK;
        Fkind = FoodKind.CORN;
    }
    public double getFoodCoeff(){return 0.04;}
}
