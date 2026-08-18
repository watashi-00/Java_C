package src.main.java.com.lab.dsa.list;

import java.util.Objects;

public class LinkedList <T> {
    Node <T> head;
    Node <T> tail;

    public LinkedList () {
        this.head = null;
        this.tail = null;
    }

    public LinkedList (T headContent) {
        this.head = new Node<>(headContent);
        this.tail = this.head;
    }


    public LinkedList (T headContent, T tailContent) {
        this.head = new Node<>(headContent);
        this.tail = new Node<>(tailContent);
        this.head.next = this.tail;
    }

    public LinkedList (Node<T> head) {
        this.head = head;
        this.tail = this.head;
    }

    public LinkedList (Node<T> head, Node<T> tail) {
        this.head = head;
        this.tail = tail;
    }

    void append(Node<T> node) {
        if(node == null) return;
        
        if(head == null) {
            head = node;
            tail = node;
            return;
        }

        tail.next = node;
        tail = node;

    }

    Node<T> pop() {
        if(head == null) {
            return null; 
        }

        if(tail == head) {
            var temp = head;
            this.head = null;
            this.tail = null;
            return temp;
        }

        Node<T> cur = head;

        while (cur.next != tail) {
            cur = cur.next;
        }

        Node<T> removedNode = tail;
        tail = cur;
        tail.next = null;

        return removedNode;
    }

}


class Node <T> {
    Node <T> next;
    private final T content;

    Node (T content) {
        this.content = content;
        this.next = null;
    }

    public T getContent() {
        return content;
    }

    public Node<T> getNext() {
        return next;
    }

    @Override
    public boolean equals(Object obj) {

        // the same reference in heap
        if (this == obj) return true;

        // is null or not an instance of Node
        if (!(obj instanceof Node<?> other)) return false;

        return Objects.equals(this.content, other.content);
    }

    @Override
    public int hashCode() {
        return Objects.hash(content);
    }
}


class DNode<T> extends Node<T> {
    DNode<T> prev;

    public DNode(T content) {
        super(content);
        this.prev = null;
    }

}