package com.lab.dsa.hashmap;

import java.util.Arrays;

public class HashUnrolledLinkedList <K> {
    private final Linker<K>[] list;
    private int size;

    @SuppressWarnings("unchecked")
    HashUnrolledLinkedList() {
        this.list = (Linker<K>[]) new Linker[8];
        Arrays.fill(list, null);
    }

    void append(K element) {
        int hash = element.hashCode() & (list.length - 1); // only pow of 2

        if(list[hash] == null){
            list[hash] = new Linker<K>();
            return;
        }

        System.out.println("Collision");

    }

    @SuppressWarnings("unchecked")
    K get(int i) {
        if(i >= size || i < 0){
            throw new ArrayIndexOutOfBoundsException("Index out of bounds");
        }

        if(i <= list.length){
            return (K) list[i];
        }

        return getFromIntern(i);
    }

    @SuppressWarnings("unchecked")
    private K getFromIntern(int i) {
        return  (K) list[i];
    }

}

class Linker<K> {
    private final Object[] currentList;
    private final int size;

    private Linker<K> nextLinker;
    private int usedSize;

    Linker() {
        currentList = new Object[16];
        this.usedSize = 0;
        this.size = currentList.length;
    }

    void put(K element) {

        var curLinker = this;

        while(curLinker.usedSize == curLinker.size && curLinker.nextLinker != null) {
            curLinker = curLinker.nextLinker;
        }

        if(usedSize == size){
            curLinker.nextLinker = new Linker<K>();
            curLinker = curLinker.nextLinker;
        }

        curLinker.currentList[usedSize] = element;
        curLinker.usedSize++;

        System.out.println("Added: " + element);
    }

}