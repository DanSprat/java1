package ru.progwards.java2.lessons.reflection;

import java.io.IOException;
import java.lang.reflect.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class ClassInspector {
    private static void generic(StringBuilder stringBuilder,Type type) throws ClassNotFoundException {
        if (type instanceof ParameterizedType) {
            ParameterizedType pt = (ParameterizedType)type;
            Type[] args = pt.getActualTypeArguments();
            stringBuilder.append("<");
            String generics="";
            for (Type arg:args){
                String s=arg.getTypeName();
                generics+=(Class.forName(s).getSimpleName()+",");
            }
            stringBuilder.append(generics, 0, generics.length()-1);
            stringBuilder.append(">");
        }
    }
    public static void inspect(String clazz) throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, IOException {
        Class sClass = Class.forName(clazz);
        StringBuilder fileS = new StringBuilder();
        String fileString ="";
        int m =sClass.getModifiers();
        fileS.append(Modifier.toString(m));
        fileS.append(" class "+sClass.getSimpleName());
        Class extClass = sClass.getSuperclass();
        if (extClass!=Object.class){
            fileS.append(" extends "+extClass.getSimpleName());
        }
        Class [] classes = sClass.getInterfaces();
        Type types [] = sClass.getGenericInterfaces();
        int j = 0;
        String tmp ="";
        if (classes.length!=0){
            fileS.append(" implements ");
            for (Class c: classes){
                fileS.append(c.getSimpleName());
                generic(fileS,types[j++]);
                fileS.append(", ");
            }
            fileS.deleteCharAt(fileS.lastIndexOf(", "));
        }
        fileS.append(" {\n\n\t");
        Field [] fields = sClass.getDeclaredFields();
        for (Field field: fields){
            fileS.append(Modifier.toString(field.getModifiers())+" ");
            fileS.append(field.getType().getSimpleName());
            Type tl = field.getGenericType();
            generic(fileS,tl);
            fileS.append(" "+field.getName()+";\n\t");
        }
        fileS.append("\n\t");
        Method [] methods = sClass.getDeclaredMethods();
        for (Method method: methods){
            fileS.append(Modifier.toString(method.getModifiers()));
            fileS.append(" "+method.getReturnType().getSimpleName());
            Type tl = method.getGenericReturnType();
            generic(fileS,tl);
            fileS.append(" "+method.getName());
            fileS.append("(");
            String vars = "";
            final String agr ="arg";
            for (int i =0;i<method.getParameterCount();i++){
                vars=vars+method.getParameterTypes()[i].getSimpleName();
                fileS.append(vars);
                Type type = method.getGenericParameterTypes()[i];
                generic(fileS,type);
                vars = " "+ method.getParameters()[i].getName()+",";
                fileS.append(vars,0,vars.length()-1);
            }
            fileS.append("){ }\n\t");
        }
        fileS.append("\n\t");
        Constructor [] constructors = sClass.getDeclaredConstructors();
        for (Constructor constructor: constructors){
            fileS.append(Modifier.toString(constructor.getModifiers())+" "+sClass.getSimpleName());
            fileS.append("(");
            String vars = "";
            for (int i =0;i<constructor.getParameterCount();i++){
                fileS.append(constructor.getParameterTypes()[i].getSimpleName());
                Type type = constructor.getGenericParameterTypes()[i];
                generic(fileS,type);
                vars = " "+ constructor.getParameters()[i].getName()+",";
                fileS.append(vars,0,vars.length()-1);
            }
            fileS.append("){ }\n");
        }
        fileS.append("}");
        System.out.println(fileS.toString());
        Path path2 = Paths.get("");
        Path path = Paths.get("").resolve("src\\ru\\progwards\\java2\\lessons\\tests\\"+sClass.getSimpleName()+".java");
        if (Files.exists(path)){
            Files.delete(path);
            Files.createFile(path);
        } else {
            Files.createFile(path);
        }
        Files.writeString(path,fileS.toString());


    }

    public static void main(String[] args) throws ClassNotFoundException, NoSuchFieldException, NoSuchMethodException, IOException {
        ClassInspector inspector = new ClassInspector();
        inspector.inspect("ru.progwards.java2.lessons.reflection.Person");

    }
}
