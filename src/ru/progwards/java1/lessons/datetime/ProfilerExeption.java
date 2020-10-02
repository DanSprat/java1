package ru.progwards.java1.lessons.datetime;

public  class ProfilerExeption extends Exception {
    String section;
    public ProfilerExeption(String section,String text){
        super(text);
        this.section=section;
    }
    @Override
    public String toString(){
        return super.getMessage()+section;
    }

}
