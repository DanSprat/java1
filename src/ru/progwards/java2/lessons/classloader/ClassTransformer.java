package ru.progwards.java2.lessons.classloader;

import javassist.*;

import java.lang.instrument.ClassFileTransformer;
import java.lang.instrument.IllegalClassFormatException;
import java.security.ProtectionDomain;
import java.util.ArrayList;
import java.util.Arrays;


public class ClassTransformer implements ClassFileTransformer {
    private ArrayList<String> classes;

    private boolean isPresent(String classname){
        for (String s:classes){
            if (s.equals(classname.replaceAll("[\\/\\\\]","."))){
                return true;
            }
        }
        return false;
    }
    @Override
    public byte[] transform(ClassLoader loader,
                            String className,
                            Class<?> classBeingRedefined,
                            ProtectionDomain protectionDomain,
                            byte[] classfileBuffer
    ) throws IllegalClassFormatException {

        String SystemProfiler = "ru.progwards.java2.lessons.classloader.SystemProfiler";
        ClassPool classPool = ClassPool.getDefault();
        if (isPresent(className)){
            try {

                CtClass ctClass = classPool.get(className.replace('/', '.'));
                CtClass ctSysProfiler = classPool.get(SystemProfiler.replace("/","."));
                CtMethod methods[] = ctClass.getDeclaredMethods();
                for (CtMethod method: methods) {
                    method.insertBefore("ru.progwards.java2.lessons.classloader.SystemProfiler.enterSection(\""+className+" "+method.getName()+"\");");
                    method.insertAfter("ru.progwards.java2.lessons.classloader.SystemProfiler.exitSection(\""+className+" "+method.getName()+"\");");
                }
                if (classes.get(0).equals(className.replaceAll("[\\/\\\\]","."))) {
                    CtMethod main = ctClass.getDeclaredMethod("main");
                    main.insertAfter("ru.progwards.java2.lessons.classloader.SystemProfiler.printStatisticInfo(\""+className.replaceAll("[\\/\\\\]",".")+".main"+".stat"+"\");");
                }
                try {
                    return ctClass.toBytecode();
                }catch (Exception ex){

                }
            } catch (NotFoundException | CannotCompileException  e) {
                e.printStackTrace();
            }
        }
        return null;
    }
    public ClassTransformer(String AI){
        if (AI!=null) {
            for (var x:Arrays.asList(AI.split(";"))){
                classes = new ArrayList<>();
                classes.add(x.replaceAll("[\\/\\\\]","."));
            }
        }
    }
}
