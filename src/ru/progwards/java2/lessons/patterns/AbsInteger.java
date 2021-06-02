package ru.progwards.java2.lessons.patterns;

public abstract class AbsInteger {
    abstract public String toString();
    abstract protected IntInteger toInt();
    static AbsInteger add(AbsInteger num1, AbsInteger num2){
        return new IntInteger(num1.toInt().x+num2.toInt().x).toResult();
    }

    public static void main(String[] args) {

    }
}