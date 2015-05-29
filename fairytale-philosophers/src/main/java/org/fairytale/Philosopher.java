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
	private final long eatingTime;
	private final long thinkingTime;

	public Philosopher(
			Lock leftChopStick,
			Lock rightChopStick,
			int number,
			long eatingTime,
			long thinkingTime) {
		this.leftChopStick = leftChopStick;
		this.rightChopStick = rightChopStick;
		this.number = number;
		this.eatingTime = eatingTime;
		this.thinkingTime = thinkingTime;
	}

	@Override
	public void run() {
		try {
			boolean isLeftChopStickTaken = false;
			boolean isRightChopStickTaken = false;
			while (!isLeftChopStickTaken
					|| !isRightChopStickTaken) {
				while (!(isLeftChopStickTaken = leftChopStick.tryLock())) {
					LOG.debug("Philosopher N {} tries but doesn't take {} which is left for him", number, leftChopStick);
					if (isRightChopStickTaken) {
						rightChopStick.unlock();
						isRightChopStickTaken = false;
						LOG.debug("Philosopher N {} puts {} which is right for him", number, rightChopStick);
					}
				}
				LOG.debug("Philosopher N {} takes {} which is left for him", number, leftChopStick);
				while (!(isRightChopStickTaken = rightChopStick.tryLock())) {
					LOG.debug("Philosopher N {} tries but doesn't take {} which is right for him", number, rightChopStick);
					if (isLeftChopStickTaken) {
						leftChopStick.unlock();
						isLeftChopStickTaken = false;
						LOG.debug("Philosopher N {} puts {} which is left for him", number, leftChopStick);
					}
				}
				LOG.debug("Philosopher N {} takes {} which is right for him", number, rightChopStick);
			}
			try {
				LOG.debug("Philosopher N {} starts eating", number);
				Thread.sleep(eatingTime);
				statistic();
				LOG.debug("Philosopher N {} stops eating", number);
			}
			finally {
				leftChopStick.unlock();
				LOG.debug("Philosopher N {} puts {} which is left for him", number, leftChopStick);
				rightChopStick.unlock();
				LOG.debug("Philosopher N {} puts {} which is right for him", number, rightChopStick);
				Thread.sleep(thinkingTime);
			}
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public abstract void statistic();

	@Override
	public String toString() {
		return String.format("Philosopher %d", number);
	}
}
