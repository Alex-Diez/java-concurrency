package org.fairytale.util;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class NamedReentrantLock
		implements Lock {

	private final String name;
	private final ReentrantLock reentrantLock;

	public NamedReentrantLock(String name, boolean fair) {
		this.name = name;
		this.reentrantLock =  new ReentrantLock(fair);
	}

	@Override
	public void lock() {
		reentrantLock.lock();
	}

	@Override
	public void lockInterruptibly()
			throws InterruptedException {
		reentrantLock.lockInterruptibly();
	}

	@Override
	public boolean tryLock() {
		return reentrantLock.tryLock();
	}

	@Override
	public boolean tryLock(long time, TimeUnit unit)
			throws InterruptedException {
		return reentrantLock.tryLock(time, unit);
	}

	@Override
	public void unlock() {
		reentrantLock.unlock();
	}

	@Override
	public Condition newCondition() {
		return reentrantLock.newCondition();
	}

	@Override
	public String toString() {
		return name;
	}
}
