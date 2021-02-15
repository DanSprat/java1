package ru.progwards.java2.lessons.gc;

import java.awt.List;
import java.util.*;
import java.util.stream.Collectors;

public class Heap {

    private static class Block implements Comparable<Block>{
        private Integer size;
        private Integer ptr;


        Block (int p,int sz){
            size =sz;
            ptr =p;
        }

        int getSize(){
            return size;
        }
        int getPtr(){
            return ptr;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (!(o instanceof Block)) return false;
            Block block = (Block) o;
            return size == block.size &&
                    ptr == block.ptr;
        }

        @Override
        public int hashCode() {
            return Objects.hash(size, ptr);
        }


        @Override
        public int compareTo(Block block) {
            int val = this.size.compareTo(block.size);
            if (val == 0) {
                return this.ptr.compareTo(block.ptr);
            }
            return val;
        }
    }

    TreeSet <Block> freeBlocks;
    HashMap<Integer,Integer> busyBlocks;
    HashMap <Integer,Integer> codeMap;
    private byte [] bytes;


    Heap(int maxHeapSize){
        bytes = new byte [maxHeapSize];
        busyBlocks = new HashMap<>();
        freeBlocks = new TreeSet<>();
        codeMap = new HashMap<>();
        freeBlocks.add(new Block(0,maxHeapSize));

    }

    public int malloc(int size){
        Block block = freeBlocks.ceiling(new Block(-1,size));
        if (block == null){
            throw new NullPointerException("Беда!");
        } else {
            int ptr = block.ptr+size;
            busyBlocks.put(block.ptr,size);
            for (int i =block.ptr;i<block.ptr+size;i++){
                bytes[i]=1;
            }
            if (block.size.equals(size)){
                freeBlocks.remove(block);
            } else {
                block.size-=size;
                block.ptr+=size;
            }
            return ptr;
        }
    }
    public void free(int ptr){
        if (codeMap.isEmpty()) {
            Integer block = busyBlocks.remove(ptr);
            if (block == null) {
                throw new NullPointerException("Беда 2");
            } else {
                for (int i = ptr; i < block + ptr; i++) {
                    bytes[i] = 0;
                }
                boolean b = freeBlocks.add(new Block(ptr, block));
                codeMap.remove(ptr);
            }
        } else {
            Integer block = busyBlocks.remove(ptr);
            if (block == null) {
                throw new NullPointerException("Беда 2");
            } else {
                for (int i = codeMap.get(ptr); i < block + codeMap.get(ptr); i++) {
                    bytes[i] = 0;
                }
                freeBlocks.add(new Block(ptr, block));
                codeMap.remove(ptr);
            }
        }
    }
    public void defrag(){
        ArrayList <Block> list =(ArrayList<Block>) freeBlocks.stream().sorted(Comparator.comparing(x->x.ptr)).collect(Collectors.toList());
        Block first;
        Block next;
        for (int i =0;i<list.size()-1;++i){
            first = list.get(i);
            Integer nextPtr = first.ptr+first.size;
            ArrayList <Block> toMerge = new ArrayList<>();
            Integer size = first.size;
            for (int j = i+1;j<list.size() &&nextPtr.equals(list.get(j).ptr);j++,i++){
                next = list.get(j);
                nextPtr+=next.size;
                size+=next.size;
                toMerge.add(next);
            }
            freeBlocks.add(new Block(first.ptr,size));
            for (var x: toMerge){
                freeBlocks.remove(x);
            }
            freeBlocks.remove(first);

        }
    }
    public void compact() {
        if (codeMap.isEmpty()) {
            int index = 0;
            int ptr;
            freeBlocks.clear();
            for (var it : busyBlocks.entrySet().stream().sorted(Comparator.comparing(x -> x.getKey())).collect(Collectors.toList())) {
                if (index != it.getKey()) {
                    ptr = it.getKey();
                    codeMap.put(ptr, index);
                    for (int i = 0; i < it.getValue(); ++i, ++index) {
                        bytes[ptr + i] = 0;
                        bytes[index] = 1;
                    }
                } else {
                    index += it.getValue();
                }
            }
            freeBlocks.add(new Block(index, bytes.length - index));
        } else {
            int index = 0;
            int ptr;
            freeBlocks.clear();
            for (var it : busyBlocks.entrySet().stream().sorted(Comparator.comparing(x -> x.getKey())).collect(Collectors.toList())) {
                Integer key = codeMap.get(it.getKey());
                if (key==null){
                    key = it.getKey();
                }
                if (index != key) {
                    ptr = key;
                    codeMap.put(ptr, index);
                    for (int i = 0; i < it.getValue(); ++i, ++index) {
                        bytes[ptr + i] = 0;
                        bytes[index] = 1;
                    }
                } else {
                    index += it.getValue();
                }
            }
            freeBlocks.add(new Block(index, bytes.length - index));
        }
    }

    public static void main(String[] args) {
        Heap heap = new Heap(20);
        heap.malloc(5);
        heap.malloc(10);
        heap.malloc(2);
        heap.malloc(1);
        heap.malloc(2);
        heap.free(5);
        heap.compact();
        heap.free(17);
    }
}
