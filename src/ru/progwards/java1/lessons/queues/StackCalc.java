package ru.progwards.java1.lessons.queues;

import java.util.ArrayDeque;

public class StackCalc {
    private ArrayDeque<Double> stackCalc;

    public StackCalc() {
        stackCalc = new ArrayDeque<Double>();
    }

    public void push(double value){
        stackCalc.offerLast(value);
    }
    public double pop(){
        return stackCalc.pollLast();
    }
    public void add(){
        stackCalc.offerLast(stackCalc.pollLast()+stackCalc.pollLast());
    }

    public void sub(){
       Double a = stackCalc.pollLast();
       stackCalc.offerLast(stackCalc.pollLast()-a);
    }
    public void mul(){
        stackCalc.offerLast(stackCalc.pollLast()*stackCalc.pollLast());
    }
    public void div(){
        Double a = stackCalc.pollLast();
        stackCalc.offerLast(stackCalc.pollLast()/a);
    }

}
