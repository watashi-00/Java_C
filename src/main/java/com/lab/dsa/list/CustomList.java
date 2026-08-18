package com.lab.dsa.list;

class SNode<T> {
    T content;
    SNode<T> next;

    SNode(T content) {
        this.content = content;
    }
}

class DNode<T> {
    T content;
    DNode<T> next;
    DNode<T> prev;

    DNode(T content) {
        this.content = content;
    }
}

public interface CustomList<T> {
    void append(T element);
    T pop();
    int size();
    boolean isEmpty();
}

class SinglyLinkedListLIFO<T> implements CustomList<T> {
    private SNode<T> head;
    private SNode<T> tail;
    private int size;

    @Override
    public void append(T element) {
        SNode<T> node = new SNode<>(element);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            tail = node;
        }
        size++;
    }

    @Override
    public T pop() {
        if (head == null) return null;

        if (head == tail) {
            T value = head.content;
            head = tail = null;
            size = 0;
            return value;
        }

        SNode<T> cur = head;
        while (cur.next != tail) {
            cur = cur.next;
        }

        T value = tail.content;
        tail = cur;
        tail.next = null;
        size--;
        return value;
    }

    @Override
    public int size() { return size; }

    @Override
    public boolean isEmpty() { return size == 0; }
}

class DoublyLinkedListLIFO<T> implements CustomList<T> {
    private DNode<T> head;
    private DNode<T> tail;
    private int size;

    @Override
    public void append(T element) {
        DNode<T> node = new DNode<>(element);
        if (head == null) {
            head = tail = node;
        } else {
            tail.next = node;
            node.prev = tail;
            tail = node;
        }
        size++;
    }

    @Override
    public T pop() {
        if (head == null) return null;

        T value = tail.content;

        if (head == tail) {
            head = tail = null;
        } else {
            tail = tail.prev;
            tail.next = null;
        }

        size--;
        return value;
    }

    @Override
    public int size() { return size; }

    @Override
    public boolean isEmpty() { return size == 0; }
}

class SinglyLinkedListFIFO<T> implements CustomList<T> {
    SNode<T> head;
    SNode<T> tail;
    private int size;

    @Override
    public void append(T element) {
        SNode<T> node = new SNode<>(element);
        if(head == null) {
            head = tail = node;
        } else {
            tail.next = head;
            tail = node;
        }
        size++;
    }

    @Override
    public T pop() {
        if (head == null) return null;

        T value = head.content;

        if (head == tail) {
            head = tail = null;
            size = 0;
            return value;
        }

        head = head.next;
        size--;
        return value;
    }

    @Override
    public int size() { return size;  }

    @Override
    public boolean isEmpty() { return size == 0; }
}

class SinglyToDoublyAdapterLIFO<T> implements CustomList<T> {
    private final DoublyLinkedListLIFO<T> doublyList;

    public SinglyToDoublyAdapterLIFO(SinglyLinkedListLIFO<T> singlyList) {
        this.doublyList = new DoublyLinkedListLIFO<>();
        
        while (!singlyList.isEmpty()) {
            this.doublyList.append(singlyList.pop());
        }
    }

    @Override
    public void append(T element) { doublyList.append(element); }

    @Override
    public T pop() { return doublyList.pop(); }

    @Override
    public int size() { return doublyList.size(); }

    @Override
    public boolean isEmpty() { return doublyList.isEmpty(); }
}

class ListFactory {

    public enum ListType {
        SINGLY_LIFO,
        DOUBLY_LIFO,
        SINGLY_FIFO,
        DOUBLY_FIFO
    }

    private static final ListFactory INSTANCE = new ListFactory();

    private ListFactory() {}

    public static ListFactory getInstance() {
        return INSTANCE;
    }

    public <T> CustomList<T> createList(ListType type) {
        return switch (type) {
            case SINGLY_LIFO -> new SinglyLinkedListLIFO<>();
            case DOUBLY_LIFO -> new DoublyLinkedListLIFO<>();
            case SINGLY_FIFO -> new SinglyLinkedListFIFO<>();
            case DOUBLY_FIFO -> new DoublyLinkedListLIFO<>();
        };
    }

    public <T> CustomList<T> adaptToDoubly(SinglyLinkedListLIFO<T> singlyList) {
        return new SinglyToDoublyAdapterLIFO<>(singlyList);
    }
}
