package ru.progwards.java1.lessons.bigints;

public class IntInteger extends AbsInteger {
    int x;
    public IntInteger(int x){ this.x =x;}
    @Override
    public String toString(){
        return Integer.toString(x);
    }

    @Override
    protected IntInteger toInt() {
        return new IntInteger(x);
    }
    protected AbsInteger toResult(){
        if (x<=127 && x>=-128)
            return new ByteInteger((byte)x);
        else if (x<=32767 && x>=-32768)
            return new ShortInteger((short) x);
       return this;
    }
}
