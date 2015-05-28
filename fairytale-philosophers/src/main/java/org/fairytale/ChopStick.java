package org.fairytale;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ChopStick {

	private final Lock lock;
	private final Condition condition;
	private final int number;

	private volatile Runnable owner;

	public ChopStick(Lock lock, int number) {
		this.number = number;
		this.lock = lock;
		this.condition = lock.newCondition();
	}

	public Runnable changeOwner(Runnable newOwner, Runnable expectedOwner) {
		if(newOwner == null
				&& owner == expectedOwner) {
			lock.unlock();
			condition.signal();
			owner = newOwner;
		}
		else if(newOwner != null
				&& owner == expectedOwner) {
			lock.lock();
			owner = newOwner;
		}
		return owner;
	}

	public Runnable getOwner() {
		return owner;
	}

	@Override
	public String toString() {
		return String.format("Chop stick %d [has owner %s]", number, owner);
	}
}
