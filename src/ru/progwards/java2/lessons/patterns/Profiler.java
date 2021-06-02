package ru.progwards.java2.lessons.patterns;

import javassist.bytecode.analysis.Executor;
import ru.progwards.java1.lessons.datetime.CloseException;
import ru.progwards.java1.lessons.datetime.OpenException;
import ru.progwards.java1.lessons.datetime.ProfilerExeption;


import java.lang.reflect.Array;
import java.time.Instant;
import java.util.*;
import java.util.concurrent.*;
import java.util.stream.Collectors;

public  enum  Profiler {
    INSTANCE;
    private HashMap<Thread,ArrayDeque<Section>> stacks = new HashMap<>();
    private HashMap<Thread,HashMap<String, Section>> onStacks = new HashMap<>();
    private HashMap<String,StatisticInfo> information = new HashMap<>();
    private ArrayList<StatisticInfo> infos = new ArrayList<>();


    private Profiler(){

    }
    public  void enterSection(String name) {
        ArrayDeque<Section> Stack = stacks.get(Thread.currentThread());
        HashMap <String,Section> onStack = onStacks.get(Thread.currentThread());
        if(onStack.containsKey(name)){
            try {
                throw new OpenException(name);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        } else if(!Stack.isEmpty()){
            ru.progwards.java2.lessons.patterns.Section add=new Section(name,Stack.peekLast());
            Stack.peekLast().child=add;
            Stack.offerLast(add);
            onStack.put(name,add);
        } else {
            Section add=new Section(name);
            Stack.offerLast(add);
            onStack.put(name,add);
        }
    }

    public  void exitSection(String name){
        ArrayDeque<Section> Stack = stacks.get(Thread.currentThread());
        HashMap <String,Section> onStack = onStacks.get(Thread.currentThread());
        if(!onStack.containsKey(name)){
            try {
                throw new CloseException(name);
            } catch (ProfilerExeption exception){
                System.out.println(exception.getMessage());
            }

        } else {
            long time;
            time = Instant.now().toEpochMilli() - Stack.peekLast().start;
            if (Stack.peekLast().parent != null) {
                Stack.peekLast().parent.allChildTime += time;
                Stack.peekLast().parent.child = null;
            } else {

            }
            StatisticInfo info = information.get(name);
            if (info != null){
                info.count++;
                info.fullTime += time;
                info.selfTime += (time - Stack.peekLast().allChildTime);
            } else {
                information.put(name,new StatisticInfo(name,time,time-Stack.peekLast().allChildTime));
            }

            Stack.pollLast();
            onStack.remove(name);
        }

    }
    public List<StatisticInfo> getStatisticInfo(){
        return information.values().stream().sorted(Comparator.comparing(statisticInfo -> statisticInfo.sectionName)).collect(Collectors.toList());
    }

    public void addStack(ArrayDeque<Section> stack){
        stacks.put(Thread.currentThread(),stack);
    }
    public void addOnStack(HashMap<String,Section> onStack){
        onStacks.put(Thread.currentThread(),onStack);
    }
    final static int THREADS =2;
    public static void main(String[] args) throws Exception {
        CountDownLatch countDownLatch = new CountDownLatch(THREADS);
        MyThread thread1 =  new MyThread(countDownLatch);
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        Future<List<StatisticInfo>> future = null;
        for(int i = 0;i<THREADS;i++){
            future =executorService.submit(thread1);
        }
        countDownLatch.await();
        List<StatisticInfo> statisticInfos =future.get();
        for (var x: statisticInfos){
            System.out.println(x);
        }
        executorService.shutdown();
    }
}

