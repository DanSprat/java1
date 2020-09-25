package ru.progwards.java1.lessons.queues;

import java.util.ArrayList;
import java.util.Queue;

public class Order {
    private static int count = 0;
    double sum;
    int num;

    public Order(double sum){
        this.sum = sum;
        this.num = ++count;
    }
    public double getSum(){
        return sum;
    }
    public int getNum(){
        return num;
    }
}
