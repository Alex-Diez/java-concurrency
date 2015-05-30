package org.fairytale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;

public abstract class Philosopher
		implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(Philosopher.class);

	private final Lock leftChopStick;
	private final Lock rightChopStick;
	private final int number;

	public Philosopher(
			Lock leftChopStick,
			Lock rightChopStick,
			int number) {
		this.leftChopStick = leftChopStick;
		this.rightChopStick = rightChopStick;
		this.number = number;
	}

	@Override
	public void run() {
		try {
			takeChopstiks();
			try {
				LOG.debug("Philosopher N {} starts eating", number);
				eating();
				statistic();
				LOG.debug("Philosopher N {} stops eating", number);
			}
			finally {
				leftChopStick.unlock();
				LOG.debug("Philosopher N {} puts {} which is left for him", number, leftChopStick);
				rightChopStick.unlock();
				LOG.debug("Philosopher N {} puts {} which is right for him", number, rightChopStick);
				thinking();
			}
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void takeChopstiks() {
		boolean isLeftChopStickTaken = false;
		boolean isRightChopStickTaken = false;
		while (!isLeftChopStickTaken
				|| !isRightChopStickTaken) {
			isLeftChopStickTaken = takeChopStick(leftChopStick, rightChopStick, isRightChopStickTaken, "left");
			isRightChopStickTaken = takeChopStick(rightChopStick, leftChopStick, isLeftChopStickTaken, "right");
		}
	}

	private boolean takeChopStick(
			Lock chopStick,
			Lock oppositeChopStick,
			boolean isOppositeChopStickTaken,
			String side) {
		while (!chopStick.tryLock()) {
			LOG.debug("Philosopher N {} tries but doesn't take {} which is {} for him", number, chopStick, side);
			if (isOppositeChopStickTaken) {
				oppositeChopStick.unlock();
				isOppositeChopStickTaken = false;
				LOG.debug("Philosopher N {} puts {} which is {} for him", number, oppositeChopStick, side);
			}
		}
		LOG.debug("Philosopher N {} takes {} which is {} for him", number, chopStick, side);
		return true;
	}

	public abstract void statistic();

	public abstract void eating()
			throws InterruptedException;

	public abstract void thinking()
			throws InterruptedException;

	@Override
	public String toString() {
		return String.format("Philosopher %d", number);
	}
}
