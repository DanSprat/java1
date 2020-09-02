package ru.progwards.java1.lessons.basics;

public class AccuracyDoubleFloat {
    public static double volumeBallDouble(double radius){
        return (4*radius*radius*radius*3.14)/3;
    }
    public static float volumeBallFloat(float radius){
        return (4*radius*radius*radius*3.14f)/3;
    }
    public static double calculateAccuracy(double radius){
        return volumeBallDouble(radius)-volumeBallFloat((float)radius);
    }

    public static void main(String[] args) {
        System.out.println(volumeBallDouble(1.0));
        System.out.println(volumeBallFloat(1.0f));
        System.out.println(calculateAccuracy(6371.2));
    }
}
