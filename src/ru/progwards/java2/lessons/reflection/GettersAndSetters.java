package ru.progwards.java2.lessons.reflection;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;
import java.lang.reflect.Type;
import java.util.Arrays;

import static ru.progwards.java2.lessons.reflection.ClassInspector.generic;

public class GettersAndSetters {
    public static void check(String clazz) throws ClassNotFoundException, NoSuchMethodException {
        Class sClass = Class.forName(clazz);
        Method [] methods = sClass.getDeclaredMethods();
        Field [] fields = sClass.getDeclaredFields();
        for (Field field:fields){
            if (field.getModifiers()==Modifier.PRIVATE){
                String get = "get"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
                String set = "set"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1);
                boolean isGet =false;
                boolean isSet =false;
                for (Method method:methods){
                    if (method.getName().equals(get) && method.getReturnType().equals(field.getType()) && method.getGenericReturnType().equals(field.getGenericType())){
                        if (method.getParameters().length==0){
                            isGet =true;
                            continue;
                        }
                    }
                    if (method.getName().equals(set)){
                        if (method.getParameterTypes().length==1 && method.getParameterTypes()[0].equals(field.getType()) && method.getReturnType().equals(void.class) && method.getGenericParameterTypes()[0].equals(field.getGenericType()) ){
                            isSet = true;
                            continue;
                        }
                    }
                }
                if(isGet == false){
                    StringBuilder stringBuilder = new StringBuilder("public ");
                    stringBuilder.append(field.getType().getSimpleName());
                    Type tl = field.getGenericType();
                    generic(stringBuilder,tl);
                    stringBuilder.append(" get"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1)+"()");
                    System.out.println(stringBuilder);
                }
                if (isSet == false){
                    StringBuilder stringBuilder = new StringBuilder("public void set"+field.getName().substring(0,1).toUpperCase()+field.getName().substring(1)+"(");
                    stringBuilder.append(field.getType().getSimpleName());
                    Type tl = field.getGenericType();
                    generic(stringBuilder,tl);
                    stringBuilder.append(" "+field.getName()+")");
                    System.out.println(stringBuilder);
                }
            }

        }
    }

    public static void main(String[] args) throws NoSuchMethodException, ClassNotFoundException {
        check("ru.progwards.java2.lessons.reflection.Person2");
    }
}
