package ru.progwards.java1.lessons.datetime;

public class OpenException extends ProfilerExeption {
    public OpenException(String section) {
        super(section,"Section already opened");
    }
}
