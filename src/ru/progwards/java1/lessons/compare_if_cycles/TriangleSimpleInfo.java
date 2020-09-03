package ru.progwards.java1.lessons.compare_if_cycles;

public class TriangleSimpleInfo {
    public static int maxSide(int a, int b, int c){
        if (b>a)
            if (b>c)
                return b;
            else
                return c;
        else if (a>c)
            return a;
        else return c;
    }
    public static int minSide(int a, int b, int c){
        if (b<a)
            if (b<c)
                return b;
            else
                return c;
        else if (a<c)
            return a;
        else return c;
    }
    public static boolean isEquilateralTriangle(int a, int b, int c){
        return ((a == b) && (b == c));
    }
    public static void main(String[] args) {
        System.out.println(minSide(1,2,3));
        System.out.println(minSide(1,3,2));
        System.out.println(isEquilateralTriangle(1,1,1));
        System.out.println(isEquilateralTriangle(2,1,1));

    }
}
