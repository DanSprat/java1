package ru.progwards.java1.lessons.collections;

import java.util.Iterator;

public class ArrayIterator<T> implements Iterator<T> {

    private T[] array;
    int current =-1;
    ArrayIterator(T[] array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        if (current<array.length-1)
        return true;
        else return false;
    }

    @Override
    public T next() {
        if (hasNext())
        return array[++current];
        else return null;
    }
}
