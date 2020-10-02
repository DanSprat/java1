package ru.progwards.java1.lessons.datetime;

class StatisticInfo {
    public String sectionName;
    public long fullTime;
    public long selfTime;
    public int count;

    public StatisticInfo(String sectionName, long fullTime, long selfTime) {
        this.sectionName = sectionName;
        this.fullTime = fullTime;
        this.selfTime = selfTime;
    }
}
