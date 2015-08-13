package com.google.jam.datastructures;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;

public class LastIndexTaskLinkedQueue<E>
        extends AbstractQueue<E>
        implements LastIndexTaskQueue<E> {

    public LastIndexTaskLinkedQueue() {
    }

    public LastIndexTaskLinkedQueue(Collection<E> collection) {
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public int size() {
        return 0;
    }

    @Override
    public boolean offer(E e) {
        return false;
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
