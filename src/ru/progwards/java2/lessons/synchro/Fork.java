package ru.progwards.java2.lessons.synchro;

import java.util.concurrent.locks.ReentrantLock;

public class Fork extends ReentrantLock {
    private boolean busy;

    public Fork(){
        busy = false;
    }

    public void take(){
        busy = true;
    }

    public void hold(){
        busy = false;
    }

}
