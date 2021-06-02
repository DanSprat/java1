package ru.progwards.java2.lessons.patterns;

public enum ShortFactory implements IntegerFactory {
    SHORT_FACTORY;
    short num;


    @Override
    public AbsInteger getNumber(int x) {
        return new ShortInteger((short) x);
    }

    @Override
    public void setNum(int x) {
        num =(short)x;
    }
}
