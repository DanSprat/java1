package ru.progwards.java2.lessons.patterns;

public enum ShortFactory implements IntegerFactory {
    SHORT_FACTORY;
    short num;


    @Override
    public AbsInteger getNumber() {
        return new ShortInteger(num);
    }

    @Override
    public void setNum(int x) {
        num =(short)x;
    }
}
