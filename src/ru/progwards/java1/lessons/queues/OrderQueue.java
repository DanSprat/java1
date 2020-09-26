package ru.progwards.java1.lessons.queues;

import java.util.*;

public class OrderQueue {
    PriorityQueue<Order> priorityQueue;
    public OrderQueue() {
        priorityQueue = new PriorityQueue<>(new Comparator<Order>() {
            @Override
            public int compare(Order order, Order t1) {
                return Integer.compare(order.getQueueClass(),t1.getQueueClass());
            }
        });
    }
    public void add(Order order){
        if (order.getSum() >20000){
            order.setQueueClass(1);
            priorityQueue.offer(order);
        } else if (order.getSum() <=10000){
            order.setQueueClass(3);
            priorityQueue.offer(order);
        } else {
            order.setQueueClass(2);
            priorityQueue.offer(order);
        }
    }
    public Order get(){
       if (!priorityQueue.isEmpty())
           return priorityQueue.poll();
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
        Order order = orderQueue.get();
        System.out.println(order.getSum());
        System.out.println(order.getQueueClass());
        System.out.println("End");
    }
}
