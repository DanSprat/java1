package ru.progwards.java2.lessons.patterns;

import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CountDownLatch;

public class MyThread  implements Callable<List<StatisticInfo>> {
    CountDownLatch countDownLatch;
    public MyThread(CountDownLatch countDownLatch){
        this.countDownLatch = countDownLatch;
    }
    @Override
    public List<StatisticInfo> call() throws Exception {
        Profiler profiler = Profiler.INSTANCE;
        profiler.addStack(new ArrayDeque<Section>());
        profiler.addOnStack(new HashMap<>());

        try{
            profiler.enterSection("section 1");
            Thread.sleep(1000);
            profiler.enterSection("section 2");
            Thread.sleep(1000);
            profiler.enterSection("section 3");
            Thread.sleep(1000);
            profiler.exitSection("section 3");
            Thread.sleep(1000);
            profiler.exitSection("section 2");
            Thread.sleep(1000);
            profiler.exitSection("section 1");
        } catch (InterruptedException ex){
            ex.printStackTrace();
            countDownLatch.countDown();
        }
        countDownLatch.countDown();
        return profiler.getStatisticInfo();
    }
}
