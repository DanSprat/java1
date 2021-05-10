package ru.progwards.java2.lessons.synchro;

import ru.progwards.java2.lessons.gc.InvalidPointerException;
import ru.progwards.java2.lessons.gc.OutOfMemoryException;

import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

public class MyThread extends Thread {

    Heap heap;
    CountDownLatch countDownLatchFinish;
    BlockingDeque<HeapTest.Block> blocks;

    static final int maxSize = 1932735283;
    static final int maxSmall = 10;
    static final int maxMedium = 100;
    static final int maxBig = 1000;
    public static final int maxHuge = 10000;
    static AtomicInteger allocated = new AtomicInteger(0);
    AtomicInteger count = new AtomicInteger(0);
    AtomicInteger allocTime = new AtomicInteger(0);
    AtomicInteger freeTime = new AtomicInteger(0);
    ConcurrentHashMap concurrentHashMap = new ConcurrentHashMap();

    static int getRandomSize() {
        int n = Math.abs(ThreadLocalRandom.current().nextInt()%10);
        int size = Math.abs(ThreadLocalRandom.current().nextInt());
        if (n < 6)
            size %= maxSmall;
        else if (n < 8)
            size %= maxMedium;
        else if (n < 9)
            size %= maxBig;
        else
            size %= maxHuge;
        return size > (maxSize-allocated.get())-1 ? (maxSize-allocated.get())/2+1 : size+1;
    }

    public MyThread(Heap heap, CountDownLatch countDownLatch, BlockingDeque<HeapTest.Block> concurrentLinkedDeque, ConcurrentHashMap<HeapTest.Block,Integer> concurrentHashMap){
        this.concurrentHashMap = concurrentHashMap;
        this.blocks = concurrentLinkedDeque;
        this.countDownLatchFinish = countDownLatch;
        this.heap = heap;
    }
    @Override
    public void run() {
        try {
            while ((maxSize - allocated.get()) > 50000) {
                long lstart, lstop;
                int size = getRandomSize();
                //allocated += size;
                allocated.addAndGet(size);
                count.incrementAndGet();
                lstart = System.currentTimeMillis();
                int ptr = heap.malloc(size);
                lstop = System.currentTimeMillis();
                //allocTime += lstop-lstart;
                allocTime.addAndGet((int)(lstop-lstart));
                blocks.offer(new HeapTest.Block(ptr, size));
                int n = Math.abs(ThreadLocalRandom.current().nextInt()%25);
                if (n == 0) {
                    //n = Math.abs(ThreadLocalRandom.current().nextInt()%blocks.size());
                    for (int i=0; i<5; i++) {
                        HeapTest.Block block = blocks.poll();
                        if (block == null)
                            break;
                        lstart = System.currentTimeMillis();
                        heap.free(block.ptr);
                        lstop = System.currentTimeMillis();
                        //freeTime += lstop - lstart;
                        freeTime.addAndGet((int)(lstop-lstart));
                        //allocated -= block.size;
                        allocated.addAndGet(-block.size);
                    }
                    //blocks.remove(n);
                }
                n = Math.abs(ThreadLocalRandom.current().nextInt()%100000);
                if (n==0)
                    System.out.println(maxSize-allocated.get());
            }
        } catch (OutOfMemoryException | InvalidPointerException ex){
            ex.printStackTrace();
        } finally {
            countDownLatchFinish.countDown();
        }
    }

}
