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
        int hash = genHash(element);

        if(list[hash] == null){
            list[hash] = new Linker<K>();
        }
        list[hash].put(element);
        size++;
    }


    public boolean contains(K element) {
        int hash = genHash(element);

        var linker = list[hash];

        if (linker == null){
            return false;
        }

        return linker.exists(element);

    };

    private int genHash(K key) {
        return key.hashCode() & (list.length - 1);  // only pow of 2
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
        var curLinker = getLatestLinker(this);

        if(curLinker.usedSize == curLinker.size){
            curLinker.nextLinker = new Linker<K>();
            curLinker = curLinker.nextLinker;
        }

        curLinker.currentList[curLinker.usedSize] = element;
        curLinker.usedSize++;

        System.out.println("Added: " + element);
    }

    boolean exists(K element) {
        return findElement(this, element) != null;
    }

    private Linker<K> getLatestLinker(Linker<K> curLinker) {
        while(curLinker.usedSize == curLinker.size && curLinker.nextLinker != null) {
            curLinker = curLinker.nextLinker;
        }
        return curLinker;
    }

    private SearchedElement<K> findElement(Linker<K> curLinker, K element) {
        while(curLinker != null) {

            var Sel = _getElement(curLinker,  element);
            if(Sel != null){
                return Sel;
            }
            curLinker = curLinker.nextLinker;
        };

        return null;
    }

    private SearchedElement<K> _getElement(Linker<K> curLinker, K element) {
        for(var i = 0; i < curLinker.usedSize; i++){
            if(curLinker.currentList[i].equals(element)) { // needs override Equals on element
                return new SearchedElement<>(curLinker, element);
            }
        }
        return null;
    }

}

record SearchedElement<K>(Linker<K> linker, K element ) {}
