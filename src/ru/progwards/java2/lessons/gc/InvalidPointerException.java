package ru.progwards.java2.lessons.gc;

public class InvalidPointerException extends Exception {
    Integer pointer;
    public InvalidPointerException(String text,int ptr){
        super(text);
        pointer = ptr;
    }

    @Override
    public String toString() {
        return "InvalidPointerException " +
                "pointer" + pointer;
    }
}
