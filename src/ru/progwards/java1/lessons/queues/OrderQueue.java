package ru.progwards.java1.lessons.queues;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.PriorityQueue;

public class OrderQueue {
    ArrayList<Deque<Order>> classesList = new ArrayList<>(3);

    public OrderQueue() {
            classesList.add(new ArrayDeque<>());
            classesList.add(new ArrayDeque<>());
            classesList.add(new ArrayDeque<>());
    }

    public void add(Order order){
        if (order.sum >20000){
            classesList.get(0).offer(order);
        } else if (order.sum <=10000){
            classesList.get(2).offer(order);
        } else {
            classesList.get(1).offer(order);
        }
    }
    public Order get(){
        if (!classesList.get(0).isEmpty())
            return classesList.get(0).poll();
        else if (!classesList.get(1).isEmpty())
            return classesList.get(1).poll();
        else if (!classesList.get(2).isEmpty())
            return classesList.get(2).poll();
        else return null;
    }

    public static void main(String[] args) {
        OrderQueue orderQueue = new OrderQueue();
        orderQueue.add(new Order(1000));//3
        orderQueue.add(new Order(200000));//1
        orderQueue.add(new Order(30000));//1
        orderQueue.add(new Order(400));//3
        orderQueue.add(new Order(5000));//3
        orderQueue.add(new Order(70000));//1
        orderQueue.add(new Order(20000));//2
        orderQueue.add(new Order(10000));//3
        System.out.println("End");
    }
}
