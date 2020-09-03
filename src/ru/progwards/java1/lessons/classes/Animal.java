package ru.progwards.java1.lessons.classes;
enum AnimalKind  {ANIMAL, COW, HAMSTER, DUCK;}
enum FoodKind {UNKNOWN, HAY, CORN;}
public class Animal {
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
        System.out.println(new Duck(20).toStringFull());
    }
}
