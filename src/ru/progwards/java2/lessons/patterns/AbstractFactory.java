package ru.progwards.java2.lessons.patterns;

public enum AbstractFactory  {
    FACTORY;

    public AbsInteger getNumber(int x){
        IntegerFactory factory;
        if (x < 128 && x>-128){
            factory = ByteFactory.BYTE_FACTORY;
        } else {
            if (x<=32767 && x>=-32768){
                factory =  ShortFactory.SHORT_FACTORY;
            } else {
                factory = IntFactory.INT_FACTORY;
            }
        }
        factory.setNum(x);
        return factory.getNumber();
    }
}
