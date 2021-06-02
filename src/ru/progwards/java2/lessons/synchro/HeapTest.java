package ru.progwards.java2.lessons.synchro;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Random;
import java.util.concurrent.*;
import java.util.concurrent.locks.ReentrantLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class HeapTest {

    static class Block {
        public int ptr;
        public int size;

        public Block(int ptr, int size) {
            this.ptr = ptr;
            this.size = size;
        }
    }

    static final int maxSize = 1932735283;
    //static final int maxSize = 1000000000;


    public static void main(String[] args) throws InterruptedException {

        ReentrantLock reentrantLock = new ReentrantLock();
        ReentrantReadWriteLock reentrantReadWriteLock = new ReentrantReadWriteLock();
        CountDownLatch countDownLatch = new CountDownLatch(5);
        Heap heap = new Heap(maxSize,reentrantLock,reentrantReadWriteLock);
        BlockingDeque blocks = new LinkedBlockingDeque();
        ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();

        MyThread thread1 = new MyThread(heap,countDownLatch,blocks,concurrentHashMap);
        MyThread thread2 = new MyThread(heap,countDownLatch,blocks,concurrentHashMap);
        MyThread thread3 = new MyThread(heap,countDownLatch,blocks,concurrentHashMap);
        MyThread thread4 = new MyThread(heap,countDownLatch,blocks,concurrentHashMap);
        MyThread thread5 = new MyThread(heap,countDownLatch,blocks,concurrentHashMap);

        long start = System.currentTimeMillis();
        thread1.start();
        thread2.start();
        thread3.start();
        thread4.start();
        thread5.start();


        countDownLatch.await();
        long stop = System.currentTimeMillis();
        System.out.println("malloc time: "+thread1.allocTime+" free time: "+thread1.freeTime);
        System.out.println("total time: "+(stop-start)+" count: "+thread1.count);
        System.out.println("Allocated: "+ thread1.allocated);

    }

}
