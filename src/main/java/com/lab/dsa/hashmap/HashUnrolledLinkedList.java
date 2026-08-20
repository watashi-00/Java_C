package com.lab.dsa.hashmap;

import java.util.LinkedList;
import java.util.List;

public class HashUnrolledLinkedList <K> {
    private final Object[] list;
    private int size;
    
    HashUnrolledLinkedList() {
        this.list = new Object[8];
    }

    void append(K element) {
        int hash = element.hashCode() & (list.length - 1); // only pow of 2

        if(list[hash] == null){
            list[hash] = element;
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
