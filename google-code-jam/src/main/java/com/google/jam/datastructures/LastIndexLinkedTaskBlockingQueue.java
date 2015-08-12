package com.google.jam.datastructures;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class LastIndexLinkedTaskBlockingQueue<E>
        extends AbstractQueue<E>
        implements LastIndexTaskBlockingQueue<E> {

    private final AtomicInteger size;
    private final ReentrantLock addLock;
    private final ReentrantLock removeLock;
    private final ThreadLocal<Integer> lastThreadTask;

    private Node<E> tail;
    private Node<E> head;

    public LastIndexLinkedTaskBlockingQueue(Collection<E> collection) {
        this(collection.size());
        for (E element : collection) {
            add(element);
        }
    }

    public LastIndexLinkedTaskBlockingQueue() {
        this(-1);
    }

    private LastIndexLinkedTaskBlockingQueue(int initialIndex) {
        addLock = new ReentrantLock();
        removeLock = new ReentrantLock();
        size = new AtomicInteger();
        lastThreadTask = new ThreadLocal<Integer>() {

            @Override
            protected Integer initialValue() {
                return initialIndex;
            }
        };
    }

    @Override
    public int getLastRetrievedTaskIndex() {
        return lastThreadTask.get();
    }

    @Override
    public boolean offer(E e) {
        if (e == null) {
            throw new NullPointerException();
        }
        Node<E> node = new Node<>(e);
        final ReentrantLock lock = addLock;
        lock.lock();
        try {
            if (tail == null
                    && head == null) {
                tail = node;
                head = node;
            }
            if (tail != null) {
                tail.next = node;
                tail = node;
            }
            size.getAndIncrement();
            return true;
        }
        finally {
            lock.unlock();
        }
    }

    @Override
    public void put(E e)
            throws InterruptedException {
        offer(e);
    }

    @Override
    public boolean offer(E e, long timeout, TimeUnit unit)
            throws InterruptedException {
        return offer(e);
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public E take()
            throws InterruptedException {
        return poll();
    }

    @Override
    public E poll(long timeout, TimeUnit unit)
            throws InterruptedException {
        return poll();
    }

    @Override
    public int remainingCapacity() {
        return Integer.MAX_VALUE;
    }

    @Override
    public int drainTo(Collection<? super E> collection) {
        return 0;
    }

    @Override
    public int drainTo(Collection<? super E> collection, int maxElements) {
        if (collection == null) {
            throw new NullPointerException();
        }
        final int queueSize = size.get();
        final int numberElementToDrain = maxElements > queueSize ? queueSize : maxElements;
        int i = 0;
        while (i < numberElementToDrain) {
            E element = poll();
            collection.add(element);
            i++;
        }
        return i;
    }

    @Override
    public E poll() {
        if (head != null) {
            final ReentrantLock lock = addLock;
            lock.lock();
            try {
                E element = head.value;
                head = head.next;
                int index = size.getAndDecrement();
                lastThreadTask.set(index);
                return element;
            }
            finally {
                lock.unlock();
            }
        }
        return null;
    }

    @Override
    public E peek() {
        if (head != null) {
            return head.value;
        }
        return null;
    }

    @Override
    public int size() {
        return size.get();
    }

    @Override
    public boolean contains(Object o) {
        if (isEmpty()) {
            return false;
        }
        for (Node<E> node = head; node != null; node = node.next) {
            if (node.value.equals(o)) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder("[ ");
        for (Node<E> node = head; node != null; node = node.next) {
            if (node != tail) {
                sb.append(node.value).append(", ");
            }
            else {
                sb.append(node.value);
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

    private static class Node<E> {

        private final E value;
        private Node<E> next;

        public Node(E value) {
            this.value = value;
        }

    }
}
