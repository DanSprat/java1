package ru.progwards.java2.lessons.classloader;


public class TestClass {
    public static void main(String[] args) throws InterruptedException {
        first();
        second();
        third();
    }

    public static void first() throws InterruptedException {
        Thread.sleep(1000);
    }
    public static void second() throws InterruptedException {
        Thread.sleep(2000);
    }
    public static void third() throws InterruptedException {
        first();
        second();
        Thread.sleep(3000);
    }
}
