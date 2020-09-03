package ru.progwards.java1.lessons.classes;

public class Cow extends Animal{

    public Cow(double weight) {
        super(weight);
        Akind = AnimalKind.COW;
        Fkind = FoodKind.HAY;
    }
    @Override
    public double getFoodCoeff(){return 0.05;}

}
