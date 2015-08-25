package org.concurrent.datastructures;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;

public class LastIndexTaskLinkedQueue<E>
        extends AbstractQueue<E>
        implements LastIndexTaskQueue<E> {

    private static final Node NULL = new Node(null);

    private int size;
    private int lastRetrievedTaskIndex;
    private Node<E> head;
    private Node<E> tail;

    public LastIndexTaskLinkedQueue() {
        lastRetrievedTaskIndex = 1;
        head = NULL;
        tail = NULL;
    }

    public LastIndexTaskLinkedQueue(Collection<? extends E> collection) {
        this();
        for (E e : collection) {
            offer(e);
        }
    }

    @Override
    public Iterator<E> iterator() {
        return new Iterator<E>() {

            private Node<E> current = head;

            @Override
            public boolean hasNext() {
                return current != tail;
            }

            @Override
            public E next() {
                E element = current.getValue();
                current = current.getNext();
                return element;
            }
        };
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
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
        if (tail == NULL) {
            tail = node;
        }
        else {
            tail.setNext(node);
            tail = node;
        }
        if (head == NULL) {
            head = node;
            head.setNext(node);
        }
        return true;
    }

    @Override
    public E poll() {
        if (head != null) {
            E element = head.getValue();
            Node<E> h = head;
            head = head.getNext();
            h.setNext(null);
            size--;
            return element;
        }
        return null;
    }

    @Override
    public E peek() {
        if (head != null) {
            return head.getValue();
        }
        return null;
    }

    @Override
    public int getLastRetrievedTaskIndex() {
        final int index = lastRetrievedTaskIndex;
        lastRetrievedTaskIndex++;
        return index;
    }
}
