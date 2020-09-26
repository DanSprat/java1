package ru.progwards.java1.lessons.queues;

import java.util.ArrayList;
import java.util.Queue;

public class Order {
    private static int count = 0;
    private double sum;
    private int num;
    private int queueClass = 1;

    public Order(double sum){
        this.sum = sum;
        this.num = ++count;
    }

    public int getQueueClass() { return queueClass; }
    public void setQueueClass (int numb) {this.queueClass=numb;}
    public double getSum(){
        return sum;
    }
    public int getNum(){
        return num;
    }

}
