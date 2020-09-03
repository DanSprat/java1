package ru.progwards.java1.lessons.compare_if_cycles;

public class TriangleInfo {
    public static boolean isTriangle(int a, int b, int c){
        return (a < b+c) && (b < a+c) && (c < a+b);
    }
    public static boolean isRightTriangle(int a, int b, int c){
        if (!isTriangle(a,b,c))
            return false;
        if (c>a && c>b)
            return c*c == a*a + b*b;
        else if (b>a && b>c)
            return b*b == a*a + c*c;
        else return a*a == b*b + c*c;
    }
    public static boolean isIsoscelesTriangle(int a, int b, int c){
        if (isTriangle(a,b,c))
        return (a==b || b==c ||c==a);
        else return false;
    }
    public static void main(String[] args) {
        System.out.println(isRightTriangle(4,4,5));
        System.out.println(isIsoscelesTriangle(2,2,3));
    }
}
