package ru.progwards.java1.lessons.classes;
enum AnimalKind  {ANIMAL, COW, HAMSTER, DUCK;}
enum FoodKind {UNKNOWN, HAY, CORN;}
public abstract class  Animal implements FoodCompare{
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
    @Override
    public int compareFoodPrice(Animal aminal){
        return  Double.compare(getFoodPrice(),aminal.getFood1kgPrice());
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
        System.out.println(new Duck(20).toStringFull());
    }
}
