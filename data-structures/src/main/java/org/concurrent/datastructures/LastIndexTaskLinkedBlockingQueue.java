package org.concurrent.datastructures;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.ReentrantLock;

public class LastIndexTaskLinkedBlockingQueue<E>
        extends AbstractQueue<E>
        implements LastIndexTaskBlockingQueue<E> {

    private final AtomicInteger size;
    private final AtomicInteger lastRetrievedTaskIndex;
    private final ReentrantLock addLock;
    private final ReentrantLock removeLock;
    private final ThreadLocal<Integer> lastThreadTask;

    private Node<E> tail;
    private Node<E> head;

    public LastIndexTaskLinkedBlockingQueue(Collection<? extends E> collection) {
        this(collection.size());
        for (E element : collection) {
            add(element);
        }
    }

    public LastIndexTaskLinkedBlockingQueue() {
        this(-1);
    }

    private LastIndexTaskLinkedBlockingQueue(int initialIndex) {
        lastRetrievedTaskIndex = new AtomicInteger(1);
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
                tail.setNext(node);
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
        return drainTo(collection, size.get());
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
                Node<E> h = head;
                E element = head.getValue();
                head = head.getNext();
                h.setNext(null);
                size.getAndDecrement();
                final int index = lastRetrievedTaskIndex.getAndIncrement();
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
            return head.getValue();
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
        for (Node<E> node = head; node != null; node = node.getNext()) {
            if (node.getValue().equals(o)) {
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
        for (Node<E> node = head; node != null; node = node.getNext()) {
            if (node != tail) {
                sb.append(node.getValue()).append(", ");
            }
            else {
                sb.append(node.getValue());
            }
        }
        sb.append(" ]");
        return sb.toString();
    }

}
