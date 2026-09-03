package com.lab.dsa.lists;

import com.lab.utils.Lab;
import com.lab.utils.MenuSelect;

@MenuSelect(label = "Linked List: FIFO")
public class LinkedListFifo implements Lab {

    private Node head;
    private Node tail;

    @Override
    public void run() {

    }

    void append(Node node) {
        if (node == null) {
            return;
        }

        if(head == null) {
            this.head = node;
            this.tail = node;
            return;
        }

        if(this.head == this.tail) {
            this.tail = node;
            this.head.setNext(node);
            return;
        }

        this.tail.setNext(node);
        this.tail = node;

    }

    Node pop() {
        if(head == null) {
            return null;
        }

        Node temp =  this.head;
        if(this.head == this.tail) {
            this.tail = null;
        }

        this.head = this.head.getNext();
        return temp;
    }

    public Node getHead() {
        return head;
    }

    public Node getTail() {
        return tail;
    }
}
