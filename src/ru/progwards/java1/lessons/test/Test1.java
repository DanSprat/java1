package ru.progwards.java1.lessons.test;

import java.io.FileReader;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.io.Reader;
import java.lang.reflect.Array;
import java.math.BigDecimal;
import java.util.*;

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
    public static void main(String[] args) throws IOException{
        Collection<Integer> numbers = new ArrayList<>();
        for (int i =0;i<5;i++)
        ((ArrayList) numbers).add(3,5);
        scanLines();
        System.out.println(setStars("s"));
        System.out.println(invertWords("Буря мглою небо кроет"));
        System.out.println("Сделаю коммит, запушу в репо: робот, проверяй теперь всё это...");
        int[] a1 = {12, 5, 0, 58, 36};
        int[] a2 = Arrays.copyOf(a1, a1.length);
        Arrays.sort(a2);
        System.out.println(Arrays.equals(a1, a2));
        System.out.println(ret((byte)15));
        BigDecimal a = new BigDecimal("10.0");
        BigDecimal b = new BigDecimal("10.0");
        System.out.println(a.multiply(b).compareTo(a.multiply(b)));
        Integer as =null;
        System.out.println(sqr(as));

    }
}
