package com.google.jam.datastructures;

import java.util.AbstractQueue;
import java.util.Collection;
import java.util.Iterator;
import java.util.concurrent.TimeUnit;

public class LastIndexTaskLinkedBlockingQueue<I, E>
		extends AbstractQueue<E>
		implements LastIndexTaskQueue<I, E> {

	private static class Node<E> {

		private final E value;
		private Node<E> next;

		public Node(E value) {
			this.value = value;
		}

	}

	private int size;
	private Node<E> tail;
	private Node<E> head;

	public LastIndexTaskLinkedBlockingQueue(Collection<E> collection) {
		for (E element : collection) {
			add(element);
		}
	}

	public LastIndexTaskLinkedBlockingQueue() {

	}

	@Override
	public I getLastRetrievedTaskIndex() {
		return null;
	}

	@Override
	public boolean offer(E e) {
		if (e == null) {
			throw new NullPointerException();
		}
		Node<E> node = new Node<>(e);
		if (tail == null
				&& head == null) {
			tail = node;
			head = node;
		}
		if (tail != null) {
			tail.next = node;
			tail = node;
		}
		size++;
		return true;
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
		final int numberElementToDrain = maxElements > size ? size : maxElements;
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
			E element = head.value;
			head = head.next;
			size--;
			return element;
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
		return size;
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
}
