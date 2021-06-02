package ru.progwards.java2.lessons.patterns;

public class ByteInteger extends AbsInteger {
    byte x;
    public ByteInteger(byte x){ this.x =x;}
    @Override
    public String toString(){
        return Byte.toString(x);
    }

    @Override
    protected IntInteger toInt() {
        return new IntInteger((int)x);
    }

}

