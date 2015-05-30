package org.fairytale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;

public abstract class Philosopher
		implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(Philosopher.class);
	public static final String RIGHT_SIDE = "right";
	public static final String LEFT_SIDE = "left";

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
			takeChopsticks();
			try {
				eatingProcess();
				statistic();
			}
			finally {
				putChopSticks();
				thinking();
			}
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void eatingProcess() throws InterruptedException {
		LOG.debug("Philosopher N {} starts eating", number);
		eating();
		LOG.debug("Philosopher N {} stops eating", number);
	}

	private void putChopSticks() {
		putChopstick(leftChopStick, LEFT_SIDE);
		putChopstick(rightChopStick, RIGHT_SIDE);
	}

	private void putChopstick(Lock leftChopStick, String side) {
		leftChopStick.unlock();
		LOG.debug("Philosopher N {} puts {} which is {} for him", number, leftChopStick, side);
	}

	private void takeChopsticks() {
		boolean isLeftChopStickTaken = false;
		boolean isRightChopStickTaken = false;
		while (!isLeftChopStickTaken
				|| !isRightChopStickTaken) {
			isRightChopStickTaken = takeChopStick(leftChopStick, rightChopStick, isRightChopStickTaken, LEFT_SIDE);
			isLeftChopStickTaken = takeChopStick(rightChopStick, leftChopStick, isLeftChopStickTaken, RIGHT_SIDE);
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
		return isOppositeChopStickTaken;
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
