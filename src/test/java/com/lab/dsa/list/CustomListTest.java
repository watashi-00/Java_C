package com.lab.dsa.list;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ListSelectionTest {

    @Test
    @DisplayName("Create SimplyLinkedList")
    void SimplyLinkedList() {
        ListFactory factory = ListFactory.getInstance();

        CustomList<String> simplyLinkedList = factory.createList(ListFactory.ListType.SINGLY);

        simplyLinkedList.append("A");
        simplyLinkedList.append("B");

        assertThat(simplyLinkedList.pop()).isEqualTo("B");
        assertThat(simplyLinkedList.size()).isEqualTo(1);
    }

    @Test
    @DisplayName("Create DoublyLinkedList")
    void doublyLinkedList() {
        ListFactory factory = ListFactory.getInstance();

        CustomList<Integer> doublyLinkedList = factory.createList(ListFactory.ListType.DOUBLY);

        doublyLinkedList.append(10);
        doublyLinkedList.append(20);
        doublyLinkedList.append(30);

        assertThat(doublyLinkedList.pop()).isEqualTo(30);
        assertThat(doublyLinkedList.pop()).isEqualTo(20);
        assertThat(doublyLinkedList.size()).isEqualTo(1);
    }
}