package ru.progwards.java2.lessons.patterns;

public enum IntFactory implements IntegerFactory {
    INT_FACTORY;
    int num;

    @Override
    public AbsInteger getNumber(int x) {
        return new IntInteger(x);
    }

    @Override
    public void setNum(int x) {
        num = x;
    }
}
