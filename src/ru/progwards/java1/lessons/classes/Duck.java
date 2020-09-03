package ru.progwards.java1.lessons.classes;

public class Duck extends Animal {
    public Duck(double weight) {
        super(weight);
        Akind= AnimalKind.DUCK;
        Fkind = FoodKind.CORN;
    }

}
