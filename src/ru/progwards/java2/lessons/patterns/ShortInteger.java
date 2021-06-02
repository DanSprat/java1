package ru.progwards.java2.lessons.patterns;

public class ShortInteger extends AbsInteger {
    short x;
    public ShortInteger(short x){ this.x =x;}
    @Override
    public String toString(){
        return Short.toString(x);
    }

    @Override
    protected IntInteger toInt() {
        return new IntInteger((int)x);
    }


}
