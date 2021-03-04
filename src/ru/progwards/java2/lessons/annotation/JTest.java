package ru.progwards.java2.lessons.annotation;

import ru.progwards.java2.lessons.tests.calc.SimpleCalculator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

public class JTest {
    void run(String name) throws ClassNotFoundException, IllegalAccessException, InvocationTargetException, InstantiationException {
        Class clazz = Class.forName(name);

        Method methods [] = clazz.getDeclaredMethods();
        ArrayList <Method> testMethods = new ArrayList<>();
        Method before=null;
        Method after=null;
        for (Method method:methods){
            if(method.isAnnotationPresent(Test.class)){
                testMethods.add(method);
                continue;
            }
            if (method.isAnnotationPresent(Before.class)){
                if (before == null){
                    before = method;
                } else {
                    throw new NullPointerException();
                }
                continue;
            }
            if(method.isAnnotationPresent(After.class)){
                if(after == null){
                    after  = method;
                } else {
                    throw new NullPointerException();
                }
            }
        }
        testMethods = (ArrayList<Method>) testMethods.stream().sorted(Comparator.comparingInt(x -> x.getAnnotation(Test.class).priority())).collect(Collectors.toList());
        before.invoke(clazz);
        for(Method method:testMethods){
            System.out.println("Test "+ method.getName());
           method.invoke(clazz);
            System.out.println();
        }
        after.invoke(clazz);
    }

    public static void main(String[] args) throws ClassNotFoundException, InvocationTargetException, InstantiationException, IllegalAccessException {
        JTest jTest = new JTest();
        jTest.run("ru.progwards.java2.lessons.annotation.CalculatorTest");
    }

}
