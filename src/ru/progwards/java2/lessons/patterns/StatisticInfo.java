package ru.progwards.java2.lessons.patterns;

class StatisticInfo {
    public Thread thread;
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

    public StatisticInfo(String sectionName, long fullTime, long selfTime,Thread thread) {
        this.thread = thread;
        this.sectionName = sectionName;
        this.fullTime = fullTime;
        this.selfTime = selfTime;
        this.count=1;

    }

    @Override
    public String toString() {
       return sectionName +" "+count+" "+selfTime+" "+fullTime;
    }
}
