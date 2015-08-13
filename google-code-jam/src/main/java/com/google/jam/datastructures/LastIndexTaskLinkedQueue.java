package com.google.jam.datastructures;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;

public class LastIndexTaskLinkedQueue<E>
        extends AbstractQueue<E>
        implements LastIndexTaskQueue<E> {

    private int size;
    private Node<E> head;
    private Node<E> tail;

    public LastIndexTaskLinkedQueue() {
    }

    public LastIndexTaskLinkedQueue(Collection<? extends E> collection) {
        for(E e : collection) {
            offer(e);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public int size() {
        return size;
    }

    @Override
    public boolean offer(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        Node<E> node = new Node<>(e);
        size++;
        if (tail == null
                && head == null) {
            tail = node;
            head = node;
        }
        if (tail != null) {
            tail.setNext(node);
            tail = node;
        }
        return true;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }

    @Override
    public int getLastRetrievedTaskIndex() {
        return 0;
    }
}
