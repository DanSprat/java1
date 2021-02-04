
public class SimpleCalculator {
    public int sum(int val1, int val2){
        long result = (long)val1 + val2;
        if (result>Integer.MAX_VALUE || result<Integer.MIN_VALUE){
            throw new ArithmeticException("Исключение вызвано в методе sum");
        }
        return (int) result;
    }
    public int diff(int val1, int val2){
        long result = (long)val1 - val2;
        if (result>Integer.MAX_VALUE || result<Integer.MIN_VALUE){
            throw new ArithmeticException("Исключение вызвано в методе sum");
        }
        return (int) result;
    }
    public int mult(int val1, int val2){
        if (val1 ==0 || val2==0) {
            return 0;
        }  else {
            int res = val1*val2;
            if (res/val1 ==val2) {
                return res;
            } else {
                throw new ArithmeticException("Исключение вызвано в методе mult");
            }
        }
    }
    public int div(int val1, int val2){
        if(val2==0)
            throw new ArithmeticException("Divine by zero in div method");
        return val1/val2;
    }
}