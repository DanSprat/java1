package ru.progwards.java1.lessons.basics;

public class Astronomy {
    public static Double sphereSquare(Double r){  // Площадь сфера
        return 4*3.14*r*r;
    }
    public static Double earthSquare(){ // Площадь Земли
        return sphereSquare(6371.2);
    }
    public static Double mercurySquare(){ // Площадь Меркурия
        return sphereSquare(2439.7);
    }
    public static Double jupiterSquare(){ // Площадь Юпитера
        return sphereSquare(71492.0);
    }
    public static Double earthVsMercury(){ // Земля / Меркурий
        return earthSquare()/mercurySquare();
    }
    public static Double earthVsJupiter(){ // Земля / Юпитер
        return earthSquare()/jupiterSquare();
    }

    public static void main(String[] args) {
        System.out.println(sphereSquare(1.0));
        System.out.println(earthSquare());
        System.out.println(mercurySquare());
        System.out.println(jupiterSquare());
        System.out.println(earthVsMercury());
        System.out.println(earthVsJupiter());
    }
}
