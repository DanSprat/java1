package ru.progwards.java2.lessons.classloader;

import java.io.File;
import java.io.IOException;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class PathLoader extends ClassLoader {

    public PathLoader(String basePath) {
        this(basePath,getSystemClassLoader());
    }
    public PathLoader(String basePath,ClassLoader classLoader) {
        super(basePath,classLoader);
        this.basePath = basePath;
    }
    public void setBasePath(String basePath){
        this.basePath = basePath;
    }

    private static class ClassInfo {
        Long millis;
        String path;
        String name;

        public ClassInfo(Long m,String path,String name){
            millis = m;
            this.path = path;
            this.name = name;
        }
    }
    private String basePath;
    final static String DOT = ".class";
    final static String ROOT  = "C:/Users/Work/IdeaProjects/Progwards/src/ru/progwards/java2/lessons/classloader/root";
    final static String LOG ="patchloader.log";
    private static PathLoader loader = new PathLoader(ROOT);

    private static void process(Path logFile,HashMap<String,Long> cls, HashMap<String,String> fld) throws IOException {
        Files.walkFileTree(Paths.get(ROOT),new SimpleFileVisitor<Path>(){
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                HashMap<String,ClassInfo> checkedClasses = new HashMap<>();
                if (file.toString().endsWith(DOT)){
                    String className = makeClassName(file);
                    if (checkedClasses.get(className) == null){
                        checkedClasses.put(className,new ClassInfo(attrs.lastModifiedTime().toMillis(),file.toAbsolutePath().toString(),makeSubClassPath(file)));
                        Files.walkFileTree(Paths.get(ROOT),new SimpleFileVisitor<>(){
                            @Override
                            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                               if (file.toString().replaceAll("[\\/\\\\]",".").endsWith(className+DOT)){
                                   if (checkedClasses.get(className).millis<attrs.lastModifiedTime().toMillis()){
                                       checkedClasses.put(className,new ClassInfo(attrs.lastModifiedTime().toMillis(),file.toAbsolutePath().toString(),makeSubClassPath(file)));
                                   }
                               }

                               return FileVisitResult.CONTINUE;
                            }

                            @Override
                            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                              return FileVisitResult.CONTINUE;
                            }
                        });
                        ClassInfo classInfo = checkedClasses.get(className);
                        Long time = cls.get(className);
                        if (time==null || cls.get(className)<checkedClasses.get(className).millis){
                            if (time != null){
                                loader = new PathLoader(classInfo.path.replace(classInfo.name,""));
                            }
                            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("dd.MM.YYYY HH:mm");
                            ZonedDateTime ZDT = ZonedDateTime.now();
                            try {
                                loader.setBasePath(classInfo.path.replace(classInfo.name,""));
                                loader.loadClass(className,true);
                                String string = dateTimeFormatter.format(ZDT) + " "+className+ " загружен из " + loader.basePath+"\n\n";
                                Files.writeString(logFile,string,StandardOpenOption.APPEND);
                                System.out.println(string);
                                cls.put(className,classInfo.millis);
                                if (fld.get(className)!=null){
                                    fld.remove(className);
                                }
                            } catch (ClassNotFoundException | ClassFormatError ex){
                                if (fld.get(className)==null || !fld.get(className).equals(classInfo.path)){
                                    fld.put(className,classInfo.path);
                                    String failedClass = dateTimeFormatter.format(ZDT) + " "+className+ " ошибка загрузки "+ ex.toString()+"\n\n";
                                    Files.writeString(logFile,failedClass,StandardOpenOption.APPEND);
                                }
                            }
                        }
                    }
                }
                return FileVisitResult.CONTINUE;
            }

            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                return FileVisitResult.CONTINUE;
            }
        });
    }


    @Override
    public Class<?> findClass(String name) throws ClassNotFoundException,ClassFormatError {
        try {
            String classPath = name.replace(".","/");
            Path className = Paths.get(basePath+classPath+DOT);
            if(Files.exists(className)){
                byte [] byteClass = Files.readAllBytes(className);
                return defineClass(name,byteClass,0,byteClass.length);
            } else {
                return findSystemClass(name);
            }
        } catch (IOException ex ){
            throw new ClassNotFoundException(name);
        } catch (ClassFormatError ex) {
            throw new ClassFormatError();
        }
    }

    @Override
    protected Class<?> loadClass(String name, boolean resolve) throws ClassNotFoundException {
        Class <?> clazz = findClass(name);
        if (resolve)
            resolveClass(clazz);
        return clazz;
    }
    private static String makeClassName(Path file) throws IOException {
        Path path = file.toAbsolutePath().toRealPath();
        Path pathWithoutRoot = Paths.get(ROOT).relativize(path); // Путь без root
        String className = pathWithoutRoot.subpath(1,pathWithoutRoot.getNameCount()).toString().replaceAll("[\\/\\\\]",".");
        return className.substring(0,className.length()-DOT.length());
    }

    private static String makeSubClassPath(Path file) throws IOException {
        Path path = file.toAbsolutePath().toRealPath();
        Path pathWithoutRoot = Paths.get(ROOT).relativize(path); // Путь без root
        String className = pathWithoutRoot.subpath(1,pathWithoutRoot.getNameCount()).toString();
        return className;
    }

    public static void main(String[] args) throws InterruptedException, IOException {
        HashMap <String,Long> classes = new HashMap<>();
        HashMap<String,String> failed = new HashMap<>();
        Path pathLog = Paths.get(ROOT).resolve(LOG);
        if (!Files.exists(pathLog)){
            Files.createFile(pathLog);
        }
        String openSession =  "======================\nOpen Session: "+ZonedDateTime.now().format(DateTimeFormatter.ISO_ZONED_DATE_TIME)+"\n";
        Files.writeString(pathLog,openSession,StandardOpenOption.APPEND);
        while (true){
            process(pathLog,classes,failed);
            Thread.sleep(5000);
        }
    }
}
