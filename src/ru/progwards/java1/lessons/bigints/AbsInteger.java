package ru.progwards.java1.lessons.bigints;

public abstract class AbsInteger {
     abstract public String toString();
     abstract protected IntInteger toInt();
     static AbsInteger add(AbsInteger num1, AbsInteger num2){
         return new IntInteger(num1.toInt().x+num2.toInt().x).toResult();
     }

    public static void main(String[] args) {
        ByteInteger byteInteger = new ByteInteger((byte)120);
        ShortInteger shortInteger = new ShortInteger((short)26);
        AbsInteger absInteger = add(byteInteger,shortInteger);
        System.out.println(absInteger.getClass());
    }
}
