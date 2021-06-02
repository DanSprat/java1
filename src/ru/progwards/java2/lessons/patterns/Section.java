package ru.progwards.java2.lessons.patterns;

import java.time.Instant;

public class Section{
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
