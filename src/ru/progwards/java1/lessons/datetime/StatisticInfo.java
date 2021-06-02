package ru.progwards.java1.lessons.datetime;

class StatisticInfo {
    public String sectionName;
    public long fullTime;
    public int count;
    public long selfTime;

    public StatisticInfo(String sectionName, long fullTime, long selfTime) {
        this.sectionName = sectionName;
        this.fullTime = fullTime;
        this.selfTime = selfTime;
        this.count=1;
    }
}
