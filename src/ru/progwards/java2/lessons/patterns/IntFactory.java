package ru.progwards.java2.lessons.patterns;

public enum IntFactory implements IntegerFactory {
    INT_FACTORY;
    int num;

    @Override
    public AbsInteger getNumber() {
        return new IntInteger(num);
    }

    @Override
    public void setNum(int x) {
        num = x;
    }
}
