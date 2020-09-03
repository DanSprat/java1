package ru.progwards.java1.lessons.classes;
enum AnimalKind  {ANIMAL, COW, HAMSTER, DUCK;}
enum FoodKind {UNKNOWN, HAY, CORN;}
public class Animal {
    double weight;
    AnimalKind Akind = AnimalKind.ANIMAL;
    FoodKind Fkind;
    public Animal(double weight){
        this.weight = weight;
    }
    public AnimalKind getKind() {return Akind;}
    public FoodKind getFoodKind(){return  Fkind;}
    public String toString(){return "I am "+Akind+", eat "+Fkind;}
    public static void main(String[] args) {
        System.out.println(new Duck(20).toString());
    }
}
