package ru.progwards.java2.lessons.annotation;

import ru.progwards.java2.lessons.tests.calc.SimpleCalculator;

public class CalculatorTest {
    public static SimpleCalculator simpleCalculator;

    @Before
    public static void init(){
        simpleCalculator = new SimpleCalculator();
    }

    @Test(priority = 1)
    public static  void multExceptionSimpleCalculatorTest(){
        Integer arr [] = {Integer.MAX_VALUE,2};
         try {
            simpleCalculator.mult(arr[0],arr[1]);
             System.out.println("\tТест 1 провален. Ожидалось: "+ArithmeticException.class.getSimpleName());
        } catch (ArithmeticException ex){
             System.out.println("\tТест 1 пройден успешно");
         }
    }
    @Test(priority = 2)
    public static void sumNormalSimpleCalculatorTest(){
        Integer integers [] [] = {
                {0,0,0},
                {1,9,10},
                {0,-3,-3},
                {-5,0,-5},
                {-1,4,3}
        };
        int i =1;
        Integer check;
        for(Integer [] x:integers){
            try {
                check = x[0] + x[1];
                if (x[2].equals(check)){
                    System.out.println("\tTest "+ i +" пройден успешно" );
                } else {
                    System.out.println("\tTest "+ i +" провален. Ожидалось "+x[2]+" Получено: "+check);
                }
            } catch (Exception ex){
                System.out.println("\tTest "+ i +" провален. Ожидалось "+x[2]+" Получено: "+ex.getClass().getSimpleName());
            } finally {
                i++;
            }
        }
    }
    @Test(priority = 3)
    public static void diffNormalSimpleCalculatorTest(){
        {
            Integer integers[][] = {{5, 5, 0},
                    {4, 3, 1},
                    {-3, 2, -5},
                    {1, -6, 7}
            };
            Integer check;
            int i =1;
            for(Integer [] x:integers){
                try {
                    check = simpleCalculator.diff(x[0],x[1]);
                    if (x[2].equals(check)){
                        System.out.println("\tTest "+ i +" пройден успешно" );
                    } else {
                        System.out.println("\tTest "+ i +" провален. Ожидалось "+x[2]+" Получено: "+check);
                    }
                } catch (Exception ex){
                    System.out.println("\tTest "+ i +" провален. Ожидалось "+x[2]+" Получено: "+ex.getClass().getSimpleName());
                } finally {
                    i++;
                }
            }
        }

    }
    @Test(priority = 4)
    public static void diffExceptionSimpleCalculatorTest(){
        Integer integers [] [] = {
                {Integer.MIN_VALUE,1,Integer.MIN_VALUE-1},
                {Integer.MAX_VALUE,-1,Integer.MAX_VALUE+1},
                {1,-Integer.MAX_VALUE,Integer.MAX_VALUE+1},
                {-2,Integer.MAX_VALUE,Integer.MIN_VALUE-1}

        };
        int i =1;
        for (Integer x[]:integers){
            try{
                Integer check = simpleCalculator.diff(x[0],x[1]);
                System.out.println("\tТест "+ i + " провален. Ожидалось: "+ArithmeticException.class.getSimpleName()+" Получено: "+ check);
            }catch (ArithmeticException ex){
                System.out.println("\tТест " + i + " пройден успешно");
            }
            finally {
                i++;
            }
        }
    }
    @Test(priority = 5)
    public static void multNormalSimpleCalculatorTest(){
        Integer [] [] integers = {
                {20,30,600},
                {-10,6,-60},
                {-5,-10,50}
        };
        Integer check;
        int i =1;
        for(Integer [] x:integers){
            try {
                check = simpleCalculator.mult(x[0],x[1]);
                if (x[2].equals(check)){
                    System.out.println("\tTest "+ i +" пройден успешно" );
                } else {
                    System.out.println("\tTest "+ i +" провален. Ожидалось "+x[2]+" Получено: "+check);
                }
            } catch (Exception ex){
                System.out.println("\tTest "+ i +" провален. Ожидалось "+x[2]+" Получено: "+ex.getClass().getSimpleName());
            } finally {
                i++;
            }
        }

    }
    @Test(priority = 6)
    public static void divNormalSimpleCalculatorTest(){
        Integer [][] integers = {
                {600,20,30},
                {-5,2,-2},
                {-120,-5,24},
                {0,100,0}
        };
        Integer check;
        int i =1;
        for(Integer [] x:integers){
            try {
                check = simpleCalculator.div(x[0],x[1]);
                if (x[2].equals(check)){
                    System.out.println("\tTest "+ i +" пройден успешно" );
                } else {
                    System.out.println("\tTest "+ i +" провален. Ожидалось "+x[2]+" Получено: "+check);
                }
            } catch (Exception ex){
                System.out.println("\tTest "+ i +" провален. Ожидалось "+x[2]+" Получено: "+ex.getClass().getSimpleName());
            } finally {
                i++;
            }
        }

    }
    @Test(priority = 7)
    public static void divExceptionSimpleCalculatorTest(){
        Integer [][] integers = {
                {128,0,1},
                {0,0,1}
        };
        Integer check;
        int i =1;
        for (Integer [] x: integers){
            try{
                check = simpleCalculator.div(x[0],x[1]);
                System.out.println("\tТест "+ i + " провален. Ожидалось: "+ArithmeticException.class.getSimpleName()+" Получено: "+ check);
            } catch (ArithmeticException ex){
                System.out.println("\tТест " + i + " пройден успешно");
            } finally {
                i++;
            }
        }
    }
    @After
    public static void finish(){
        simpleCalculator= null;
    }

}
