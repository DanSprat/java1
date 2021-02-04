import org.junit.BeforeClass;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;

import static junit.framework.TestCase.assertEquals;

@RunWith(Enclosed.class)
public class SimpleCalculatorTest {
    public static SimpleCalculator simpleCalculator;
    @BeforeClass
    public static void init(){
        simpleCalculator = new SimpleCalculator();
    }
    @RunWith(Parameterized.class)
    public static class sumNormalSimpleCalculatorTest{
        @Parameterized.Parameter(0)
        public int val1;
        @Parameterized.Parameter(1)
        public int val2;
        @Parameterized.Parameter(2)
        public int expected;

        @Parameterized.Parameters (name ="Тест {index}: ({0}) + ({1}) = {2}")
        public static Iterable<Object[]> data(){
            return Arrays.asList(new Object[][]{
                    {0,0,0},
                    {1,9,10},
                    {0,-3,-3},
                    {-5,0,-5},
                    {-1,4,3}
            });
        }
        @Test
        public void testWithParams(){
            assertEquals(expected,simpleCalculator.sum(val1,val2));
        }
    }
    @RunWith(Parameterized.class)
    public static class sumExceptionSimpleCalculatorTest{
        @Parameterized.Parameter
        public int val1;
        @Parameterized.Parameter(1)
        public int val2;
        @Parameterized.Parameter(2)
        public int expected;

        @Parameterized.Parameters()
        public static Iterable <Object[]> data(){
            return Arrays.asList(new Object[][] {
                    {Integer.MAX_VALUE,1,Integer.MAX_VALUE+1}
            });
        }
        @Test(expected = ArithmeticException.class)
        public  void testWithParams(){
            assertEquals(expected,simpleCalculator.sum(val1,val2));
        }
    }

    @RunWith(Parameterized.class)
    public static class diffNormalSimpleCalculatorTest{
        @Parameterized.Parameter
        public int val1;
        @Parameterized.Parameter(1)
        public int val2;
        @Parameterized.Parameter(2)
        public int expected;

        @Parameterized.Parameters(name ="Тест {index}: ({0}) - ({1}) = {2}")
        public static Iterable<Object[]> data(){
            return Arrays.asList(new Object[][]{
                    {5,5,0},
                    {4,3,1},
                    {-3,2,-5},
                    {1,-6,7}
            });
        }

        @Test
        public void testWithParams(){
            assertEquals(expected,simpleCalculator.diff(val1,val2));
        }
    }

    @RunWith(Parameterized.class)
    public static class diffExceptionSimpleCalculatorTest{
        @Parameterized.Parameter
        public int val1;
        @Parameterized.Parameter(1)
        public int val2;
        @Parameterized.Parameter(2)
        public int expected;

        @Parameterized.Parameters()
        public static Iterable<Object[]> data(){
            return Arrays.asList(new Object[][]{
                    {Integer.MIN_VALUE,1,Integer.MIN_VALUE-1},
                    {Integer.MAX_VALUE,-1,Integer.MAX_VALUE+1},
                    {1,-Integer.MAX_VALUE,Integer.MAX_VALUE+1},
                    {-2,Integer.MAX_VALUE,Integer.MIN_VALUE-1}

            });
        }

        @Test(expected = ArithmeticException.class)
        public void testWithParams(){
            assertEquals(expected,simpleCalculator.diff(val1,val2));
        }
    }

    @RunWith(Parameterized.class)
    public static class multNormalSimpleCalculatorTest{
        @Parameterized.Parameter(0)
        public int val1;
        @Parameterized.Parameter(1)
        public int val2;
        @Parameterized.Parameter(2)
        public int expected;

        @Parameterized.Parameters(name ="Test {index} = ({0}) * ({1}) = ({2})")
        public static Iterable<Object[]> data(){
            return Arrays.asList(new Object[][]{
                    {20,30,600},
                    {-10,6,-60},
                    {-5,-10,50}
            });
        }

        @Test()
        public void testWithParam(){
            assertEquals(expected,simpleCalculator.mult(val1,val2));
        }
    }

    @RunWith(Parameterized.class)
    public static class multExceptionSimpleCalculatorTest{
        @Parameterized.Parameter(0)
        public int val1;
        @Parameterized.Parameter(1)
        public int val2;
        @Parameterized.Parameter(2)
        public int expected;

        @Parameterized.Parameters
        public static Iterable<Object[]> data(){
            return Arrays.asList(new Object[][]{
                    {Integer.MAX_VALUE,2,Integer.MAX_VALUE*2}
            });
        }

        @Test(expected = ArithmeticException.class)
        public void testWithParams(){
            assertEquals(expected,simpleCalculator.mult(val1,val2));
        }
    }


    @RunWith(Parameterized.class)
    public static class divNormalSimpleCalculatorTest{
        @Parameterized.Parameter(0)
        public int val1;
        @Parameterized.Parameter(1)
        public int val2;
        @Parameterized.Parameter(2)
        public int expected;

        @Parameterized.Parameters(name ="Test {index} = ({0}) / ({1}) = ({2})")
        public static Iterable<Object[]> data(){
            return Arrays.asList(new Object[][]{
                    {600,20,30},
                    {-5,2,-2},
                    {-120,-5,24},
                    {0,100,0}
            });
        }

        @Test()
        public void testWithParam(){
            assertEquals(expected,simpleCalculator.div(val1,val2));
        }
    }

    @RunWith(Parameterized.class)
    public static class divExceptionSimpleCalculatorTest{
        @Parameterized.Parameter(0)
        public int val1;
        @Parameterized.Parameter(1)
        public int val2;
        @Parameterized.Parameter(2)
        public int expected;

        @Parameterized.Parameters
        public static Iterable<Object[]> data(){
            return Arrays.asList(new Object[][]{
                    {128,0,1},
                    {0,0,1}
            });
        }

        @Test(expected = ArithmeticException.class)
        public void testWithParams(){
            assertEquals(expected,simpleCalculator.div(val1,val2));
        }
    }

}
