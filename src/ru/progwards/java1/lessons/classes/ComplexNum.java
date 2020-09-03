package ru.progwards.java1.lessons.classes;

public class ComplexNum {
    private int a;
    private int b;
    public ComplexNum(){a=0;b=0;}
    public ComplexNum(int a,int b){
        this.a=a;
        this.b=b;
    }
    public String toString(){return a+"+"+b+"i";}
    public ComplexNum add(ComplexNum num){
       return new ComplexNum(a+num.a,b+num.b);
    }
    public ComplexNum sub(ComplexNum num){
        return new ComplexNum(a-num.a,b-num.b);
    }
    public ComplexNum mul(ComplexNum num){
        return new ComplexNum(a*num.a - b*num.b,a*num.b+b*num.a);
    }
    public ComplexNum div(ComplexNum num){
        return new ComplexNum((a*num.a+b*num.b)/(num.a*num.a+num.b*num.b),(b*num.a-a*num.b)/(num.a*num.a+num.b*num.b));
    }

    public static void main(String[] args) {
        ComplexNum a = new ComplexNum(1,1);
        ComplexNum b = new ComplexNum(1,1);
        System.out.println(a.add(b));
        System.out.println(a.div(b));
        System.out.println(a.mul(b));
        System.out.println(a.sub(b));
    }
}
