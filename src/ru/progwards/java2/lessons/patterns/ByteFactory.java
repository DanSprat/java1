package ru.progwards.java2.lessons.patterns;

public enum ByteFactory implements IntegerFactory {
    BYTE_FACTORY;
    byte num;


    @Override
    public AbsInteger getNumber(int x) {
        return new ByteInteger((byte) x);
    }

    @Override
    public void setNum(int x) {
        num = (byte)x;
    }
}
