package ru.progwards.java1.lessons.bitsworld;

public class Binary {
    private byte num;
    public Binary(byte num){this.num=num;}
    @Override
    public String toString(){
        String ret="";
        byte b=1;
        for (int i =0;i<8;i++)
        {
          if ((num & (b<<i)) !=0 )
              ret = 1+ret;
          else ret = 0+ret;
        }
        return ret;
    }

    public static void main(String[] args) {
        Binary binary = new Binary((byte)0);
        System.out.println(binary.toString());
    }
}
