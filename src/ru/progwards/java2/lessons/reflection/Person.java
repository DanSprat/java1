package ru.progwards.java2.lessons.reflection;

import java.util.Iterator;
import java.util.List;

public  class Person  extends Person2 implements Comparable<Person>,Iterable<Person>{
    private String name;
    private int age;
    private boolean sex;
    public List<Integer> integers;

    public Person(List<Integer> list){
        integers = list;

    }
    public String getName() {
        return name;
    }
    public  void setIntegers(List<Integer> integers){
        this.integers = integers;
    }

    public void setName(String name) {
        this.name = name;
    }
    public boolean getSex() {
        return sex;
    }

    public List<Integer> getIntegers(){
        return integers;
    }
    @Override
    public int compareTo(Person person) {
        return 0;
    }

    @Override
    public Iterator<Person> iterator() {
        return null;
    }
}
