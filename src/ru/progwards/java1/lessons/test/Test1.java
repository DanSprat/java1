package ru.progwards.java1.lessons.test;

import javax.swing.*;
import java.io.*;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.*;
import java.math.BigDecimal;
import java.net.URL;
import java.nio.file.*;
import java.time.*;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.function.BiFunction;
import java.util.function.Function;
import java.util.jar.Attributes;
import java.util.jar.JarInputStream;
import java.util.jar.Manifest;

import static ru.progwards.java1.lessons.test.Test1.checkAndAdd;

public class Test1 {
    class User {
        public Integer id;
        public String name;
        User (Integer id, String name) {
            this.id = id;
            this.name = name;
        }
    }
    public static int ret(byte value){
        byte a = 0b0000_0001;
        return a&value;
    }
    public TreeSet<User> createSet(){
        TreeSet<User> setUsers = new TreeSet<User>(new Comparator<User>() {
            @Override
            public int compare(User user, User t1) {
                return Integer.compare(user.id,t1.id)*(-1);
            }
        });
        return setUsers;
    }
    public static Integer sqr(Integer n){
        try {
       return n*n;
        } catch (NullPointerException ex) {
            return -1;
        }

    }
    public static String invertWords(String sentence){
        String [] strs = sentence.split(" ");
        StringBuffer stringBuffer = new StringBuffer();
        for (String s:strs){
            stringBuffer.insert(0,s+" ");
        }
        return stringBuffer.toString().trim().replace(" ",".");
    }
    public static String setStars(String filename) throws IOException{
        String str="";
        try (RandomAccessFile randomAccessFile = new RandomAccessFile(filename,"rw");){
            for (long i = 9;i<randomAccessFile.length();i+=10){
                randomAccessFile.seek(i);
                str+=(char)randomAccessFile.read();
                randomAccessFile.seek(i);
                randomAccessFile.write('*');
            }
        } catch (IOException ex)
        {
            throw new IOException(ex.getClass().toString().replace("class ",""));
        }
        return str;
    }
    private int lineCount(String filename) throws IOException{
        int i = 0;
        try {
            FileReader reader = new FileReader(filename);
            Scanner scanner = new Scanner(reader);
            while (scanner.hasNextLine()) {
                ++i;
                scanner.nextLine();
            }
        } catch (Exception e){
            throw new IOException("файл не найден");
        }
        return i;
    }
    public  static void scanLines(){
        do {
            Scanner scanner = new Scanner (System.in);
            String str = scanner.nextLine();
            if (str.contains("Привет")) System.out.println("Здравствуйте!");
            else if (str.contains("как дела")) System.out.println("Хорошо");
            else if (str.contains("/stop")) break;
            else System.out.println(str);
        } while (true);
    }
    static void dequeueTest() {
        ArrayDeque deque = new ArrayDeque<>();

        for (int i = 0; i <= 10; i++) {
            deque.offer(i);
            if (i%2 == 0)
                deque.poll();
        }

        System.out.println(deque);
    }
    public List<Integer> listAction(List<Integer> list){
        ListIterator<Integer> iterator = list.listIterator();
        Integer n = iterator.next();
        while (iterator.hasNext()){
            Integer b = iterator.next();
            if(b>n)
                n = b;
        }
        list.add(0,list.size());
        list.add(2,n);
        return list;
    }
    public Set<Integer> a2set(int[] a){
      HashSet <Integer> hashSet = new HashSet<Integer>();
      for (int x :a){
          hashSet.add(x);
      }
      return hashSet;
    }
    static boolean task1(double x1,double x2,double x3){
        double d = 3*x1+7*x2+x3;
        if (d==4) return true;
        else return false;
    }
    static boolean task2(double x1,double x2,double x3){
        if(4*x1+7*x2+10*x3<=12) return true;
        else return false;
    }
    static boolean task3(double x1,double x2,double x3){
        if(2*x1-x2-7*x3>=-7) return true;
        else return false;
    }
    static boolean task4(double x1,double x2,double x3){
        if(7*x1-3*x2+7*x3<=2) return true;
        else return false;
    }
    static boolean task5(double x1,double x2,double x3){
        if(8*x1+7*x2<=1) return true;
        else return false;
    }
    static boolean task6(double x1,double x2,double x3){
        if(8*x1+7*x2>=-4) return true;
        else return false;
    }

    static void pqTest() {
        PriorityQueue pQueue = new PriorityQueue<>();
        pQueue.offer(10);
        pQueue.offer(1);
        pQueue.offer(9);
        pQueue.offer(3);
        System.out.println(pQueue);
        System.out.println(pQueue.poll());
    }
    HashMap<Integer, String> a2map(int[] akey, String[] aval){
        HashMap <Integer, String> HM = new HashMap<>();
        for (int i = 0;i<akey.length;i++){
            HM.put(akey[i],aval[i]);
        }
        return HM;
    }
    static void checkAndAdd(TreeMap<Integer, String> map, Integer key, String value){
        String s = map.get(key);
        if (s==null){
            if (!map.isEmpty()) {
                Integer i1 = map.firstKey();
                Integer i2 = map.lastKey();
                if (key > i1 && key < i2) {
                    map.put(key, value);
                }
            }
        }

    }
    static Instant createInstant(){
        Date.from(Instant.ofEpochMilli(0).plusMillis(16*365*24*60*60*1000L+62*24*60*60*1000L+21*60*60*1000L));
        ZonedDateTime zdt = ZonedDateTime.of(2020,1,1,15,0,0,1,ZoneId.of("Europe/Moscow"));
        return zdt.toInstant();
    }
    static ZonedDateTime parseZDT(String str){
        DateTimeFormatter dft = DateTimeFormatter.ofPattern("dd.MM.yyyy HH:mm:ss.SSS Z zzzz", Locale.ENGLISH);
        ZonedDateTime zdt = ZonedDateTime.from(dft.parse(str));
        return zdt;
    }
    static String createFolder(String name){
        File filename = new File(name);
        boolean b =filename.mkdir();
        return Paths.get("").toAbsolutePath().getParent().toString();
    }
   static boolean replaceF(String name){
        Path path = Paths.get(name);
        try {
            String s = Files.readString(path);
            s = s.replaceAll("F","f");
            Files.writeString(path,s,StandardOpenOption.TRUNCATE_EXISTING);
            return true;
        } catch (IOException ex) {
            return false;
        }
    }
    public static <T>ArrayList <T> from(T arr []){
        ArrayList<T> list = new ArrayList<>();
        for(T x:arr){
            list.add(x);
        }
        return list;
    }
    public static<T> void swap(List<T> arr,int first,int second){
        T elem = arr.get(first);
        arr.set(first,arr.get(second));
        arr.set(second,elem);
    }
    enum CompareResult {LESS, EQUAL, GREATER};
    public static <T extends Comparable>CompareResult compare(T first,T second){
        if (first.compareTo(second) == -1)
            return CompareResult.LESS;
        if (first.compareTo(second) == 0)
            return CompareResult.EQUAL;
        return CompareResult.GREATER;
    }
   static class Person {
        private String name;

        public Person() {
            name = "no name";
        }

        private Person(String name) {
            this.name = name;
        }
       private void setName(String name) {
           this.name = name;
       }

    }

    static void setName(Person person, String name) throws NoSuchFieldException, IllegalAccessException {
        try {
            Field nm = person.getClass().getDeclaredField("name");
            nm.setAccessible(true);
            nm.set(person,name);
        }
        catch(Exception ex){

        }
    }
    void callSetName(Person person, String name){
        try {
            Method method = person.getClass().getDeclaredMethod("setName", String.class);
            method.setAccessible(true);
            try {
                method.invoke(person,name);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (InvocationTargetException e) {
                e.printStackTrace();
            }
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        }
    }
    static Person callConstructor(String name){
        Person person;
        try {
            Constructor constructor = Person.class.getDeclaredConstructor(String.class);
            constructor.setAccessible(true);
            person = (Person) constructor.newInstance(name);
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (InvocationTargetException e) {
            e.printStackTrace();
        } catch (NoSuchMethodException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    @interface AnnotationTest {
        String text() default "Всегда говори привет";
    }



        class Greetings {
        void hello() {return;}
        void goodday() {return;}
        void goodnight() {}
        void hi(){}
    }
    static void printAnnotation(){
        String string="";
        for (var m: Greetings.class.getDeclaredMethods()){

            if (m.isAnnotationPresent(AnnotationTest.class)){
                string= m.getName();
                string+=(" "+m.getAnnotation(AnnotationTest.class).text());
                System.out.println(string);
            }

        }
    }
    static class Thread1 extends Thread{

        private Integer integer;

        public Thread1(Integer integer){
            this.integer = integer;
        }
        @Override
        public void run() {
            synchronized (integer){
                System.out.println("Thread 1 runned");
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 1 ended");
            }

        }
    }

    static class Thread2 extends Thread{

        private Integer integer;

        public Thread2(Integer integer){
            this.integer = integer;
        }
        @Override
        public void run() {
            synchronized (integer){
                System.out.println("Thread 2 runned");
                try {
                    sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("Thread 2 ended");
            }

        }
    }
    public void changeString(String string){
        string+=string;
    }

    public static void main(String[] args) throws Exception {
        Integer integer = 1;
        Thread1 thread1 = new Thread1(integer);
        Thread2 thread2 = new Thread2(integer);
        thread1.start();
        Thread.sleep(1000);
        thread2.start();
        Thread.sleep(10000);
        StringBuilder stringBuilder = new StringBuilder("");
        int a1= 100;
        int a2 = 4;
        double check =100000000;
        double minB = 0;
        ArrayList<Double> list = new ArrayList<>();
        for (double b =0.01;b<=100;b+=0.01){
            double val=Math.sqrt(Math.pow(b*b*b,2)+Math.pow(2*b*b-a1,2)+Math.pow(b-a2,2));
            if (val<check){
                check =val;
                minB =b;
            }

        }
        System.out.println(minB);


        System.out.println(task1(-351.0/95,4.0/95,559.0/665));

        Path path = Paths.get("C:\\Users\\Work\\Documents\\МатМоделирование");
        byte aaa []= Files.readAllBytes(path.resolve("105.png"));
        int j =3,k=7;
        System.out.println(3+Integer.MAX_VALUE+Integer.MAX_VALUE+2);
        BiFunction<Integer,Integer,Integer> plus = (x, y)-> x+y;
        replaceF("s");
        System.out.println(createFolder("name1"));
        ZoneId zid1 = ZoneId.of("Europe/Moscow");
        System.out.println(zid1.getRules().getOffset(Instant.now()));
        ZonedDateTime zdt = Instant.now().atZone(ZoneId.of("Europe/Moscow"));
        zdt.plusMonths(1);
        System.out.println(Duration.between(zdt,zdt.plusMonths(2)).toString());
        System.out.println(parseZDT("01.01.2020 16:27:14.444 +0300 Moscow Standard Time"));
        System.out.println(createInstant());
        System.out.println();
        LocalDateTime ldt1= LocalDateTime.now();
        LocalDateTime ldt2= ldt1.plusDays(4);
        Duration duration = Duration.between(ldt1,ldt2);
        System.out.println(duration.toHours());
        TreeMap<Integer, String> map = new TreeMap<>();
        LocalDateTime ldt3= LocalDateTime.of(2019, 05, 05, 22, 24);
        System.out.println(ldt3);
        checkAndAdd(map, 0, "Zero");
        checkAndAdd(map, 0, "Zero");
        System.out.println(map);
        pqTest();
        dequeueTest();
        Collection<Integer> numbers = new ArrayList<>();
        for (int i =0;i<5;i++)
        scanLines();
        System.out.println(setStars("s"));
        System.out.println(invertWords("Буря мглою небо кроет"));
        System.out.println("Сделаю коммит, запушу в репо: робот, проверяй теперь всё это...");
        //int[] a1 = {12, 5, 0, 58, 36};
       //int[] a2 = Arrays.copyOf(a1, a1.length);
       // Arrays.sort(a2);
        //System.out.println(Arrays.equals(a1, a2));
        System.out.println(ret((byte)15));
        //BigDecimal a = new BigDecimal("10.0");
        BigDecimal b = new BigDecimal("10.0");
        //System.out.println(a.multiply(b).compareTo(a.multiply(b)));
        Integer as =null;
        System.out.println(sqr(as));

    }
}
