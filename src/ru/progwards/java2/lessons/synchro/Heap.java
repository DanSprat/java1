package ru.progwards.java2.lessons.synchro;

import ru.progwards.java2.lessons.gc.InvalidPointerException;
import ru.progwards.java2.lessons.gc.OutOfMemoryException;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Collectors;

public class Heap {

    private class Block implements Comparable<Block> {
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
    ReadWriteLock processWithMemory;
    ReentrantLock searchMemory;
    ConcurrentSkipListSet<Block> freeBlocks;
    ConcurrentHashMap<Integer,Integer> busyBlocks;
    ConcurrentHashMap <Integer,Integer> codeMap;
    private byte [] bytes;
    Block current;


    public Heap(int size,ReentrantLock reentrantLock,ReadWriteLock readWriteLock){
        bytes = new byte [size];
        busyBlocks = new ConcurrentHashMap<>();
        freeBlocks = new ConcurrentSkipListSet<>();
        codeMap = new ConcurrentHashMap<>();
        freeBlocks.add(new Block(0,size));
        current = freeBlocks.last();
        this.searchMemory = reentrantLock;
        this.processWithMemory = readWriteLock;
    }
    public void free(int ptr) throws InvalidPointerException {
        processWithMemory.readLock().lock();
        if (codeMap.isEmpty()) {
            Integer block = busyBlocks.remove(ptr);
            if (block == null) {
                throw new InvalidPointerException("Данный указатель не является началом блока",ptr);
            } else {
                for (int i = ptr; i < block + ptr; i++) {
                    bytes[i] = 0;
                }
                boolean b = freeBlocks.add(new Block(ptr, block));
                //codeMap.remove(ptr);
            }
        } else {
            Integer block = busyBlocks.remove(ptr);
            if (block == null) {
                throw new NullPointerException();
            } else {
                for (int i = codeMap.get(ptr); i < block + codeMap.get(ptr); i++) {
                    bytes[i] = 0;
                }

                freeBlocks.add(new Block(ptr, block));
                codeMap.remove(ptr);
            }
        }
        processWithMemory.readLock().unlock();
    }

    public boolean check(int size){
        if (current.size >= size){
            return true;
        }
        return false;
    }
    public void findMemory(int size){
        if (current == null){
            return;
        }
        if (check(size)){
            return;
        }

        System.out.println("Не хватило памяти,начинаю работать");
        processWithMemory.writeLock().lock();
        current = freeBlocks.last();
        if (check(size)){
            processWithMemory.writeLock().unlock();
            return;
        }

        System.out.println("Дефрагментация start");
        defrag();
        System.out.println("Дефрагментация end");
        current = freeBlocks.last();
        if (check(size)){
            processWithMemory.writeLock().unlock();
            return;
        }
        System.out.println("Компактизация start");
        compact();
        System.out.println("Компактизация end");
        current = freeBlocks.last();
        if (check(size)){
            processWithMemory.writeLock().unlock();
            return;
        }
        current = null;
        processWithMemory.writeLock().unlock();
    }
    public int malloc(int size) throws OutOfMemoryException {
        searchMemory.lock();
        findMemory(size);
        if (current==null){
            searchMemory.unlock();
            throw new OutOfMemoryException("Out");
        } else {
            int ptr = current.ptr;
            if (codeMap.isEmpty()){
                busyBlocks.put(current.ptr,size);
            } else {
                Integer ret = ThreadLocalRandom.current().nextInt();
                while (codeMap.get(ret)!=null){
                    ret = ThreadLocalRandom.current().nextInt();
                }
                busyBlocks.put(ret,size);
                codeMap.put(ret,current.ptr);
                ptr = ret;
            }
            int fillPtr = current.ptr;;
            if (current.size.equals(size)){

                freeBlocks.remove(current);
                if (!freeBlocks.isEmpty()){
                    current = freeBlocks.last();
                } current = null;
            } else {
                current.size-=size;
                current.ptr+=size;
            }

            searchMemory.unlock(); //

            processWithMemory.readLock().lock();
            for (int i =fillPtr;i<fillPtr+size;i++){
                bytes[i]=1;
            }
            processWithMemory.readLock().unlock();
            return ptr;
        }
    }

    public void defrag(){
        ArrayList<Block> list =(ArrayList<Block>) freeBlocks.stream().sorted(Comparator.comparing(x->x.ptr)).collect(Collectors.toList());
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
        System.out.println("конец Дефрагментции");
    }
    public void compact() {
        System.out.println("начало Компактизации");
        if (codeMap.isEmpty()) {
            int index = 0;
            int ptr;
            freeBlocks.clear();
            for (var it : busyBlocks.entrySet().stream().sorted(Comparator.comparing(x -> x.getKey())).collect(Collectors.toList())) {
                if (index != it.getKey()) {
                    ptr = it.getKey();
                    for (int i = 0; i < it.getValue(); ++i, ++index) {
                        bytes[ptr + i] = 0;
                        bytes[index] = 1;
                    }
                } else {
                    index += it.getValue();
                }
                codeMap.put(it.getKey(),index);
            }
            freeBlocks.add(new Block(index, bytes.length - index));
        } else {
            int index = 0;
            int ptr;
            freeBlocks.clear();
            for (var it : busyBlocks.entrySet().stream().sorted(Comparator.comparing(x -> x.getKey())).collect(Collectors.toList())) {
                Integer key = codeMap.get(it.getKey());
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
        current = freeBlocks.last();
        System.out.println("конец Компактизации");
    }

}
