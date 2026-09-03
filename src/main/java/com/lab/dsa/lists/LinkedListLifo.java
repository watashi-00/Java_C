package com.lab.dsa.lists;

import com.lab.utils.Lab;
import com.lab.utils.MenuSelect;
// last in first out
@MenuSelect(label = "Linked List: LIFO")
public class LinkedListLifo implements Lab {

    private Node head;
    private Node tail;

    @Override
    public void run() {
        System.out.println("executed");

        this.append(new Node((byte)0));
        this.append(new Node((byte)1));
        this.append(new Node((byte)3));
        this.append(new Node((byte)4));

        System.out.println("Received " + this.pop().getEvent());
    }

    public void append(Node node) {
        if (node == null) {
            return;
        }

        if (this.head == null) {
            this.head = node;
            this.tail = node;
            return;
        }

        if(this.tail == this.head) {
            this.tail = node;
            this.head.setNext(tail);
            return;
        }

        this.tail.setNext(node);
        this.tail = node;

    }

    public Node pop() {
        if (this.head == null) {
            return null;
        }

        if(this.head == this.tail) {
            Node temp = this.head;
            this.head = null;
            this.tail = null;
            return temp;
        }

        Node cur = this.head;

        while(cur.getNext() != null && cur.getNext() != this.tail) {
            cur = cur.getNext();
            System.out.println(cur);
        }

        this.tail = cur;

        return cur.getNext();

    }

    public Node getHead() {
        return this.head;
    }

    public Node getTail() {
        return this.tail;
    }

}
