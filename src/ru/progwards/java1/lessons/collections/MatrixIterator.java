package ru.progwards.java1.lessons.collections;

import java.util.Iterator;

public class MatrixIterator<T> implements Iterator<T> {
    private int currentLine=0;
    private int currentRow=-1;
    private T[][] array;

    MatrixIterator(T[][] array) {
        this.array = array;
    }

    @Override
    public boolean hasNext() {
        if(currentRow<array[currentLine].length-1)
            return true;
        if(currentLine<array.length-1)
            return true;
        return false;
    }

    @Override
    public T next() {
        if (hasNext()){
            if(currentRow<array[currentLine].length-1)
                return array[currentLine][++currentRow];
            else {
                currentRow =0;
                return array[++currentLine][currentRow];
            }
        }
        return null;
    }

    public static void main(String[] args) {
        Integer [][] matrix =  {{1,2},{3,4},{5,6}};
        MatrixIterator<Integer> matrixIterator = new MatrixIterator<Integer>(matrix);
        while(matrixIterator.hasNext()){
            System.out.println(matrixIterator.next());
        }
    }
}

