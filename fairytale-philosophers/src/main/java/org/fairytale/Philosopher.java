package org.fairytale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Philosopher
		implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(Philosopher.class);

	private final ChopStick leftChopStick;
	private final ChopStick rightChopStick;
	private final int number;
	private final long eatingTime;
	private final long thinkingTime;

	//todo to much parameters
	public Philosopher(
			ChopStick leftChopStick,
			ChopStick rightChopStick,
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
					&& !isRightChopStickTaken) {
				LOG.debug("Philosopher N {} tries to take {} which is left for him", number, leftChopStick);
				take(leftChopStick);
				isLeftChopStickTaken = true;
				LOG.debug("Philosopher N {} takes {} which is left for him", number, leftChopStick);
				if(isRightChopStickTaken) {
					LOG.debug("Philosopher N {} has to put {} which is right for him", number, rightChopStick);
					put(rightChopStick);
				}
				LOG.debug("Philosopher N {} tries to take {} which is right for him", number, rightChopStick);
				take(rightChopStick);
				isRightChopStickTaken = true;
				if(isLeftChopStickTaken) {
					LOG.debug("Philosopher N {} has to put {} which is left for him", number, leftChopStick);
					put(leftChopStick);
				}
				LOG.debug("Philosopher N {} takes {} which is right for him", number, rightChopStick);
			}
			try {
				LOG.debug("Philosopher N {} starts eating", number);
				Thread.sleep(eatingTime);
				LOG.debug("Philosopher N {} stops eating", number);
			}
			finally {
				put(leftChopStick);
				LOG.debug("Philosopher N {} puts {} which is left for him", number, leftChopStick);
				put(rightChopStick);
				LOG.debug("Philosopher N {} puts {} which is right for him", number, rightChopStick);
				Thread.sleep(thinkingTime);
			}
		}
		catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	public void take(ChopStick chopStick) {
		Philosopher currentOwner = chopStick.changeOwner(this, null);
		while(this != currentOwner) {
			currentOwner = chopStick.changeOwner(this, currentOwner);
		}
	}

	public void put(ChopStick chopStick) {
		Philosopher currentOwner = chopStick.changeOwner(null, this);
		while(null != currentOwner) {
			currentOwner = chopStick.changeOwner(currentOwner, null);
		}
	}

	@Override
	public String toString() {
		return String.format("Philosopher %d", number);
	}
}
