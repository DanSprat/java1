package ru.progwards.java1.lessons.queues;

import java.util.*;

public class OrderQueue {
    PriorityQueue<Order> priorityQueue;
    public OrderQueue() {
        priorityQueue = new PriorityQueue<>();
    }
    public void add(Order order){
        if (order.getSum() >20000){
            order.setQueueClass(1);
            priorityQueue.add(order);
        } else if (order.getSum() <=10000){
            order.setQueueClass(3);
            priorityQueue.add(order);
        } else {
            order.setQueueClass(2);
            priorityQueue.add(order);
        }
    }
    public Order get(){
       if (!priorityQueue.isEmpty())
           return priorityQueue.poll();
       else return null;
    }

    public static void main(String[] args) {

        OrderQueue orderQueue = new OrderQueue();
        orderQueue.add(new Order(21617.0));//3
        orderQueue.add(new Order(29057.0));//1
        orderQueue.add(new Order(16492.0));//1
        orderQueue.add(new Order( 26173.0));//3
        orderQueue.add(new Order(3402.0));//3
        orderQueue.add(new Order(10260.0));//1
        orderQueue.add(new Order(11086.0));//2
        orderQueue.add(new Order(15853.0));//3

        orderQueue.add(new Order( 19271.0));//3
        orderQueue.add(new Order(21984.0));//1
        orderQueue.add(new Order(8242.0));//1
        orderQueue.add(new Order( 17181.0));//3
        orderQueue.add(new Order( 23126.0));//3
        orderQueue.add(new Order( 5820.0));//1
        orderQueue.add(new Order(7673.0));//2
        orderQueue.add(new Order(16284.0));//3

        orderQueue.add(new Order( 27622.0));//3
        orderQueue.add(new Order(5398.0));//1
        orderQueue.add(new Order( 2301.0));//1
        orderQueue.add(new Order(  9119.0));//3
        orderQueue.add(new Order( 10371.0));//3
        orderQueue.add(new Order(  3348.0));//1
        orderQueue.add(new Order(11991.0));//2



        Order order = orderQueue.get();
        int i =0;
        while (order!=null) {
            System.out.println(order.getSum());
          //  System.out.println(order.getQueueClass());
            order = orderQueue.get();
            i++;
        }
        System.out.println("End");
    }
}
