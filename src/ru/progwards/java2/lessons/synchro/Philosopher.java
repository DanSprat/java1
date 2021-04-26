package ru.progwards.java2.lessons.synchro;

import ru.progwards.java2.lessons.classloader.SystemProfiler;

import java.util.concurrent.CountDownLatch;

public class Philosopher extends Thread {

    protected int number;
    private String name;
    protected Fork right;
    protected Fork left;
    protected long reflectTime;
    protected long eatTime;
    protected long reflectSum;
    protected long eatSum;
    protected CountDownLatch countDownLatchStart;
    protected CountDownLatch countDownLatchFinish;

    private long eatIterations;
    private long reflectIterations;

    public String getPhilosopherName(){
        return name;
    }
    public Philosopher(String name,long reflect,long eat,Fork left,Fork right,CountDownLatch ctdS,CountDownLatch ctdF){
        this.countDownLatchStart = ctdS;
        this.countDownLatchFinish = ctdF;
        this.name = name;
        this.reflectTime = reflect;
        this.eatTime = eat;
        this.left= left;
        this.right = right;

        eatIterations = eatTime / (long)500;
        reflectIterations = reflectTime / (long)500;
    }

    public void reflect() throws InterruptedException {
        long time = System.currentTimeMillis();
        try {
            System.out.println("Размышляет "+name);
            for (int i =0;i<reflectIterations;i++){
                Thread.sleep(500);
                System.out.println("Размышляет "+name);
                if (super.isInterrupted())
                    return;
            }
            Thread.sleep(reflectTime-500*reflectIterations);
            reflectSum+=reflectTime;
        } catch (InterruptedException ex){
            reflectSum+=System.currentTimeMillis() - time;
            interrupt();
        }

    }

    void eat() throws InterruptedException {
        long time = System.currentTimeMillis();
        try {
            System.out.println("Ест "+name);
            for (int i =0; i<eatIterations;i++){
                Thread.sleep(500);
                System.out.println("Ест "+name);
                if (super.isInterrupted()){
                    eatSum+= System.currentTimeMillis()-time;
                    return;
                }
            }
            Thread.sleep(eatTime - 500*eatIterations);
            eatSum+=eatTime;
        } catch (InterruptedException ex){
            eatSum+= System.currentTimeMillis()-time;
            interrupt();
        }
    }

    @Override
    public void run() {
        countDownLatchStart.countDown();
        Fork first;
        Fork second;
        if (number % 2 == 1){
            first = left;
            second =right;
        } else {
            first = right;
            second = left;
        }
        try {
            countDownLatchStart.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        while (!super.isInterrupted()){
            try {
                reflect();
                if (isInterrupted())
                    break;
                first.lock();
                first.take();
                second.lock();
                second.take();
                eat();
                second.unlock();
                second.hold();
                first.unlock();
                first.hold();
                if (isInterrupted())
                    break;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        countDownLatchFinish.countDown();
    }
}
