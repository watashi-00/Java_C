package com.lab.dsa.hashmap;

import java.util.LinkedList;
import java.util.List;

public class HashUnrolledLinkedList <K> {
    private final Object[] list;
    
    HashUnrolledLinkedList() {
        this.list = new Object[8];
    }

    void append(K element) {
        int hash = element.hashCode() %  list.length;

        System.out.println(hash);

    }


}
