package ru.progwards.java2.lessons.synchro;

import java.util.ArrayList;
import java.util.concurrent.CountDownLatch;

public class Simposion {

    private int size;
    private ArrayList<Philosopher> philosophers;
    private ArrayList<Fork> forks;
    private CountDownLatch countDownLatchFinish;

    public Simposion(CountDownLatch cdt){
        countDownLatchFinish = cdt;
        philosophers = new ArrayList<>();
        forks = new ArrayList<>();
    }

    public void addPhilosopher(Philosopher philosopher){
        philosopher.number = philosophers.size()+1;
        philosophers.add(philosopher);
    }

    public void addFork(Fork fork){
        forks.add(fork);
    }

    public void addPhilosophers(Philosopher ... philosophers){
        for (Philosopher philosopher: philosophers){
            philosopher.number = this.philosophers.size()+1;
            this.philosophers.add(philosopher);
        }
    }

    public void addForks(Fork ... forks){
        for (Fork fork:forks){
            this.forks.add(fork);
        }
    }

    public void start(){
        for (Philosopher philosopher: philosophers){
            philosopher.start();
        }
    }
    public void finish() throws InterruptedException {
        for (Philosopher philosopher:philosophers){
            philosopher.interrupt();
            System.out.println(philosopher.isInterrupted());
        }
        countDownLatchFinish.await();
        System.out.println("Беседа завершена");
    }

    public void print(){
        for (Philosopher philosopher:philosophers){
            System.out.println("Философ "+philosopher.getPhilosopherName()+", ел "+philosopher.eatSum+", размышлял "+philosopher.reflectSum);
        }
    }


    public static void main(String[] args) throws InterruptedException {

        CountDownLatch countDownLatchStart = new CountDownLatch(5);
        CountDownLatch countDownLatchFinish = new CountDownLatch(5);
        Simposion simposion = new Simposion(countDownLatchFinish);
        Fork fork1 = new Fork();
        Fork fork2 = new Fork();
        Philosopher philosopher1 = new Philosopher("Декарт",5000,5000,fork1,fork2,countDownLatchStart,countDownLatchFinish);
        Fork fork3 = new Fork();
        Philosopher philosopher2 = new Philosopher("Платон",4000,4000,fork2,fork3,countDownLatchStart,countDownLatchFinish);
        Fork fork4 = new Fork();
        Philosopher philosopher3 = new Philosopher("Аристотель",3000,3000,fork3,fork4,countDownLatchStart,countDownLatchFinish);
        Fork fork5 = new Fork();
        Philosopher philosopher4 = new Philosopher("Платон",2000,2000,fork4,fork5,countDownLatchStart,countDownLatchFinish);
        Philosopher philosopher5 = new Philosopher("Сократ",6000,6000,fork5,fork1,countDownLatchStart,countDownLatchFinish);
        simposion.addForks(fork1,fork2,fork3,fork4,fork5);
        simposion.addPhilosophers(philosopher1,philosopher2,philosopher3,philosopher4,philosopher5);
        simposion.start();
        Thread.sleep(100_000);
        System.out.println("Hello");
        simposion.finish();
        simposion.print();
    }

}
