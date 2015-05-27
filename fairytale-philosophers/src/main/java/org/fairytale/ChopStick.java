package org.fairytale;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;

public class ChopStick {

	private final Lock lock;
	private final Condition condition;
	private Philosopher owner;

	public ChopStick(Lock lock, Condition condition) {
		this.lock = lock;
		this.condition = condition;
	}

	public void changeOwner(Philosopher owner) {
		this.owner = owner;
	}
}
