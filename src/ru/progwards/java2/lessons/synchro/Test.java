package ru.progwards.java2.lessons.synchro;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class Test {

    public  static class ThreadWrite extends  Thread {

        ReadWriteLock readWriteLock;

        public ThreadWrite(ReadWriteLock readWriteLock){
            this.readWriteLock = readWriteLock;
        }

        @Override
        public void run() {
            readWriteLock.writeLock().lock();
            System.out.println("Запись");
            try {
                sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                readWriteLock.writeLock().unlock();
                System.out.println("Конец записи");
            }
        }
    }

    public static class ThreadRead extends Thread {
        ReadWriteLock readWriteLock;
          public ThreadRead (ReadWriteLock readWriteLock){
              this.readWriteLock = readWriteLock;
          }

        @Override
        public void run() {
            readWriteLock.readLock().lock();
            System.out.println("Чтение");
            try {
                sleep(5000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            } finally {
                readWriteLock.readLock().unlock();
                System.out.println("Чтение окончено");
            }
        }
    }

    public static void main(String[] args) {
        ReadWriteLock readWriteLock  = new ReentrantReadWriteLock();
        ThreadRead threadRead = new ThreadRead(readWriteLock);
        ThreadWrite threadWrite = new ThreadWrite(readWriteLock);
        ThreadRead threadRead1 = new ThreadRead(readWriteLock);
        threadRead.start();
        threadRead1.start();
        threadWrite.start();
    }
}
