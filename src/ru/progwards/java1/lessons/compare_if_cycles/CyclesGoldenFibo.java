package ru.progwards.java1.lessons.compare_if_cycles;
public class CyclesGoldenFibo {
    public static boolean containsDigit(int number, int digit){
        if (number == 0)
            return number==digit;
        for (;number>0;number/=10)
        {
            if (number % 10 == digit)
                return true;
        }
        return false;
    }
    public static int fiboNumber(int n){
        int first=1;int second=1;
        if (n<=2)
            return first;
        else {
            for(int i = 3;i<=n;i++){
                int temp = first+second;
                first = second;
                second=temp;
            }
            return second;
        }

    }
    public static boolean isGoldenTriangle(int a, int b, int c){
        if (a==b)
            if ((double)a/c > 1.61703 && (double)a/c < 1.61903 )
                return true;
            else return false;
        if (b==c)
            if ((double)b/a > 1.61703 && (double)b/a < 1.61903)
                return true;
            else return false;
        if (c==a)
            if ((double)c/b > 1.61703 && (double)c/b < 1.61903)
                return true;
            else return false;
         return false;

    }
    public static void main(String[] args) {
        for (int i=0;i<15;i++)
            System.out.print(fiboNumber(i+1) + " ");
        System.out.println('\n');
        for (int i =1;i<101;i++)
        {
            for (int j =1;j<101;j++)
            {
                if (TriangleInfo.isTriangle(i,i,j))
                    if (isGoldenTriangle(i,i,j))
                        System.out.println(i + " " + i+ " "+ j + "");
            }
        }
    }
}
