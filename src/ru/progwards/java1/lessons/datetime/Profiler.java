package ru.progwards.java1.lessons.datetime;

import java.time.Instant;
import java.util.*;

public class Profiler {
    private static ArrayList<StatisticInfo> infos = new ArrayList<>();
    private static ArrayDeque<Section> Stack = new ArrayDeque<>();
    private static HashMap<String,Section> onStack = new HashMap<>();

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
            infos.sort(new Comparator<StatisticInfo>() {
                @Override
                public int compare(StatisticInfo statisticInfo, StatisticInfo t1) {
                    return statisticInfo.sectionName.compareTo(t1.sectionName);
                }
            });
        }

    }
    public static List<StatisticInfo> getStatisticInfo(){
        return infos;
    }

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

    public static void main(String[] args)  {

    }
}
