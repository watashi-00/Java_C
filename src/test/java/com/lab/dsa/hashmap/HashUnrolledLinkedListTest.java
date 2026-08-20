package com.lab.dsa.hashmap;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

public class HashUnrolledLinkedListTest {

    @Test
    @DisplayName("Create UnrolledLinkedList")
    void  createUnrolledLinkedList() {
        HashUnrolledLinkedList<Integer> unrolledLinkedList = new HashUnrolledLinkedList<>();

        for(int i = 0; i < 10; i++){
            unrolledLinkedList.append(i);
        }

    }

}

