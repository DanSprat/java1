package ru.progwards.java2.lessons.calculator;


import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Hashtable;
import java.util.function.BiFunction;

public class Calculator {
    private  static class MyOperation{
        int priority;
        BiFunction<Double,Double,Double> op;
        public MyOperation(int priority,BiFunction<Double,Double,Double> op){
            this.op = op;
            this.priority = priority;
        }
    }
    private static ArrayList<String> toPostfix(String str, Hashtable<String,MyOperation> hashtable){
        ArrayDeque<String> stack= new ArrayDeque<>();
        ArrayList<String> postfix = new ArrayList<>();
        String number="";
        for (var x: str.toCharArray()){
            if (Character.isDigit(x)){
                number+=x;
            } else {
                if (number.length()>0)
                    postfix.add(number);
                number="";
                MyOperation op =hashtable.get(String.valueOf(x));
                if (op==null){
                  if (x=='('){
                      stack.addLast(String.valueOf(x));
                  } else if(x==')'){
                      while(!stack.peekLast().equals("(")){
                          postfix.add(stack.pollLast());
                      }
                      stack.pollLast();
                  }
                } else {
                    if (stack.isEmpty() || stack.peekLast().equals("(")) {
                        stack.addLast(String.valueOf(x));
                    } else {
                        while ((!stack.isEmpty() && !stack.peekLast().equals("(")) && op.priority >= hashtable.get(stack.peekLast()).priority) {
                            postfix.add(stack.pollLast());
                        }
                        stack.addLast(String.valueOf(x));
                    }
                }
            }
        }
        if(!number.isEmpty())
            postfix.add(number);
        while(!stack.isEmpty()){
            postfix.add(stack.pollLast());
        }
        return postfix;
    }
    public static double calculate(String expression){
        Hashtable<String,MyOperation> hashtable = new Hashtable<>();
        hashtable.put("+",new MyOperation(2,(x,y)-> Double.valueOf(x+y)));
        hashtable.put("-",new MyOperation(2,(x,y)-> Double.valueOf(x-y)));
        hashtable.put("*",new MyOperation(1,(x,y)-> Double.valueOf(x*y)));
        hashtable.put("/",new MyOperation(1,(x,y)-> Double.valueOf(x/y)));

        ArrayList<String> postfix = toPostfix(expression,hashtable);
        ArrayDeque<Double> stack = new ArrayDeque<>();
        for (var x: postfix){
            if (hashtable.get(x) == null){
                stack.addLast(Double.parseDouble(x));
            } else {
                Double b = stack.pollLast();
                Double a = stack.pollLast();
                stack.addLast(hashtable.get(x).op.apply(a,b));
            }
        }
        return stack.pollLast();
    }

    public static void main(String[] args) {
        System.out.println(calculate("(15+5)*20+1"));
    }
}
