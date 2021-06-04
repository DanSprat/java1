package ru.progwards.java2.lessons.patterns;

public enum ByteFactory implements IntegerFactory {
    BYTE_FACTORY;
    byte num;

    @Override
    public AbsInteger getNumber() {
        return new ByteInteger(num);
    }

    @Override
    public void setNum(int x) {
        num = (byte)x;
    }
}
