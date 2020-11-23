package ru.progwards.java2.lessons.recursion;

import java.lang.reflect.Array;
import java.util.*;

public class HanoiTower {
    private boolean trace;
    ArrayList<LinkedList<Integer>> pins;
    int size;

    private int getPin(int from,int to){
        if (from ==0 && to ==1)
            return 2;
        if (from ==0 && to ==2)
            return 1;
        if (from == 1 && to==0)
            return 2;
        if (from ==1 && to==2)
            return 0;
        if (from ==2 && to==0)
            return 1;
        if (from ==2 && to==1)
            return 0;
        return 0;
    }

    private String addZeros(String string){
        if (string.length()==3) return string;
        if (string.length()==2) return "0"+string;
        if (string.length()==1) return "00"+string;
        return string;
    }
    public HanoiTower(int size, int pos){
        trace = false;
        this.size =size;
        pins = new ArrayList<>();
        pins.add(new LinkedList<>());
        pins.add(new LinkedList<>());
        pins.add(new LinkedList<>());
        for(int i=0;i<size;i++){
            pins.get(pos).addLast(size-i);
        }
    }
    public void move(int from, int to){
        if (trace == true)
            print();
        move(from,getPin(from, to),size-1);
        pins.get(to).addLast(pins.get(from).pollLast());
        move(getPin(from, to),to,size-1);
    }

    private void move(int from,int to,int count){
        if (count == 1){
            pins.get(to).addLast(pins.get(from).pollLast());
            if (trace == true)
                print();
        } else {
            move(from,getPin(from, to),count-1);
            pins.get(to).addLast(pins.get(from).pollLast());
            if (trace == true)
                print();
            move(getPin(from, to),to,count-1);
        }
    }
    public void print(){
        String out="";
        for(int i =size-1;i>=0;i--){
            for (int j=0;j<3;j++){
                if (pins.get(j).size()>i){
                    out = out + "<"+addZeros(pins.get(j).get(i).toString())+"> ";
                } else {
                    out = out + "  I   ";
                }
            }
            out=out+"\n";
        }
        out = out + "=================";
        System.out.println(out);

    }
    void setTrace(boolean on){
        trace = true;
    }

    public static void main(String[] args) {
        HanoiTower hanoiTower = new HanoiTower(5,0);
        //hanoiTower.setTrace(true);
        hanoiTower.print();
        hanoiTower.move(0,2);
        hanoiTower.print();
}
}
