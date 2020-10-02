package ru.progwards.java1.lessons.datetime;

public class CloseException extends ProfilerExeption {
    public CloseException(String section) {
        super(section,"Section not opened");
    }
}
