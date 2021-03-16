package ru.progwards.java2.lessons.classloader;
import ru.progwards.java1.lessons.datetime.CloseException;
import ru.progwards.java1.lessons.datetime.OpenException;
import ru.progwards.java1.lessons.datetime.ProfilerExeption;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.lang.instrument.Instrumentation;
import java.nio.file.*;
import java.time.Instant;
import java.util.*;
import java.util.List;


public class SystemProfiler {


    public static class StatisticInfo {
        public String sectionName;
        public long fullTime;
        public long selfTime;
        public int count;

        public StatisticInfo(String sectionName, long fullTime, long selfTime) {
            this.sectionName = sectionName;
            this.fullTime = fullTime;
            this.selfTime = selfTime;
            this.count=1;
        }
    }


    private static ArrayList<StatisticInfo> infos = new ArrayList<>();
    private static ArrayDeque<Section> Stack = new ArrayDeque<>();
    private static HashMap<String,Section> onStack = new HashMap<>();

    private static class Section{
        public long start;
        public String name;
        public Section parent;
        public Section child;
        public int allChildTime;

        public Section(String name){
            start = Instant.now().toEpochMilli();
            this.name = name;
            parent =null;
            allChildTime = 0;
            child=null;
        }

        public Section(String name,Section parent){
            start = Instant.now().toEpochMilli();
            this.name = name;
            this.parent = parent;
            allChildTime = 0;
            child=null;
        }
    }
    public static void enterSection(String name) {
        if(onStack.containsKey(name)){
            try {
                throw new OpenException(name);
            } catch (Exception ex) {
                System.out.println(ex.getMessage());
            }

        } else if(!Stack.isEmpty()){
            Section add=new Section(name,Stack.peekLast());
            Stack.peekLast().child=add;
            Stack.offerLast(add);
            onStack.put(name,add);
        } else {
            Section add=new Section(name);
            Stack.offerLast(add);
            onStack.put(name,add);
        }
    }

    public static void exitSection(String name){
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
            boolean in = false;
            for (int i =0;i<infos.size();i++){
                if (infos.get(i).sectionName==name){
                    infos.get(i).count++;
                    infos.get(i).fullTime+=time;
                    infos.get(i).selfTime+=(time-Stack.peekLast().allChildTime);
                    in = true;
                    break;
                }
            }
            if(in == false){
                infos.add(new StatisticInfo(name,time,time-Stack.peekLast().allChildTime));
            }
            Stack.pollLast();
            onStack.remove(name);
            infos.sort(Comparator.comparing(statisticInfo -> statisticInfo.sectionName));
        }

    }
    public static List<StatisticInfo> getStatisticInfo(){
        return infos;
    }
    public static void printStatisticInfo(String fileName) throws IOException {
        Path path = Paths.get("").resolve("src/ru/progwards/java2/lessons/classloader/"+fileName).toAbsolutePath();
        if (!Files.exists(path)){
            Files.createFile(path);
        }
        System.out.println("Буду писать сюда: "+path.toAbsolutePath());
        for(var info:infos){
            Formatter formatter = new Formatter();
            formatter.format("%40s\t%7d\t%7d\t%7d\n",info.sectionName,info.fullTime,info.selfTime,info.count);
            String str =formatter.toString();
            Files.writeString(path,str,StandardOpenOption.APPEND);
        }
    }

    public static void premain(String agentArgument, Instrumentation instrumentation){
        instrumentation.addTransformer(new ClassTransformer(agentArgument));
    }
}
