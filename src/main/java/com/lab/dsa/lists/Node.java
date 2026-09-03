package com.lab.dsa.lists;

public class Node {
    private Node next;
    private byte event;

    Node(byte event) {
        this.event = event;
        this.next = null;
    }

    public Node getNext() {
        return next;
    }

    public void setNext(Node next) {
        this.next = next;
    }

    public byte getEvent() {
        return event;
    }

    public void setEvent(byte event) {
        this.event = event;
    }
}
