package org.fairytale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Lock;

public abstract class Philosopher
		implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(Philosopher.class);

	private final int number;
	private final Lock leftChopStick;
	private final Lock rightChopStick;

	private boolean isRightChopStickTaken = false;
	private boolean isLeftChopStickTaken = false;

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
			}
			finally {
				putChopSticks();
			}
			thinkingProcess();
		}
		catch (InterruptedException e) {
			LOG.debug("Philosopher N {} was interrupted", number);
			Thread.currentThread().interrupt();
		}
	}

	private void thinkingProcess()
			throws InterruptedException {
		LOG.debug("Philosopher N {} start thinking", number);
		thinking();
		LOG.debug("Philosopher N {} stop thinking", number);
	}

	private void eatingProcess() throws InterruptedException {
		LOG.debug("Philosopher N {} starts eating", number);
		eating();
		LOG.debug("Philosopher N {} stops eating", number);
		statistic();
	}

	private void putChopSticks() {
		putLeftChopstick();
		putRightChopstick();
	}

	private void putLeftChopstick() {
		leftChopStick.unlock();
		isLeftChopStickTaken = false;
		LOG.debug("Philosopher N {} puts {} which is left for him", number, leftChopStick);
	}

	private void putRightChopstick() {
		rightChopStick.unlock();
		isRightChopStickTaken = false;
		LOG.debug("Philosopher N {} puts {} which is right for him", number, leftChopStick);
	}

	private void takeChopsticks() {
		while (!isBothChopsticksTaken()) {
			takeLeftChopStick();
			if(isBothChopsticksTaken()) {
				break;
			}
			takeRightChopStick();
		}
	}

	private boolean isBothChopsticksTaken() {
		return isLeftChopStickTaken
				&& isRightChopStickTaken;
	}

	private void takeLeftChopStick() {
		if (!(isLeftChopStickTaken = leftChopStick.tryLock())) {
			LOG.debug("Philosopher N {} tries but doesn't take {} which is left for him", number, leftChopStick);
			if (isRightChopStickTaken) {
				rightChopStick.unlock();
				isRightChopStickTaken = false;
				LOG.debug("Philosopher N {} puts {} which is right for him", number, rightChopStick);
			}
		}
		else {
			LOG.debug("Philosopher N {} takes {} which is left for him", number, leftChopStick);
		}
	}

	private void takeRightChopStick() {
		if (!(isRightChopStickTaken = rightChopStick.tryLock())) {
			LOG.debug("Philosopher N {} tries but doesn't take {} which is right for him", number, rightChopStick);
			if (isLeftChopStickTaken) {
				leftChopStick.unlock();
				isLeftChopStickTaken = false;
				LOG.debug("Philosopher N {} puts {} which is left for him", number, leftChopStick);
			}
		}
		else {
			LOG.debug("Philosopher N {} takes {} which is right for him", number, rightChopStick);
		}
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
