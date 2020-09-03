package ru.progwards.java1.lessons.classes;

public class Hamster extends Animal {

    public Hamster(double weight) {
        super(weight);
        Akind = AnimalKind.HAMSTER;
        Fkind = FoodKind.CORN;
    }
    @Override
    public double getFoodCoeff(){return 0.03;}


}
