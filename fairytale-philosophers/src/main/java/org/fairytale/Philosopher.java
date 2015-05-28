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
			Runnable leftOwner = null;
			Runnable rightOwner = null;
			while(this != leftOwner
					&& this != rightOwner) {
				LOG.debug("Philosopher N {} tries to take {} which is left for him", number, leftChopStick);
				leftOwner = leftChopStick.changeOwner(this, leftOwner);
				LOG.debug("Philosopher N {} takes {} which is left for him", number, leftChopStick);
				LOG.debug("Philosopher N {} tries to take {} which is right for him", number, rightChopStick);
				rightOwner = rightChopStick.changeOwner(null, this);
				if(this == leftOwner
						&& this != rightOwner) {
					LOG.debug("Philosopher N {} has to put {} which is right for him", number, rightChopStick);
					leftOwner = leftChopStick.changeOwner(null, this);
				}
				LOG.debug("Philosopher N {} tries to take {} which is right for him", number, rightChopStick);
				rightOwner = leftChopStick.changeOwner(this, rightOwner);
				if(this != leftOwner
						&& this == rightOwner) {
					LOG.debug("Philosopher N {} has to put {} which is left for him", number, leftChopStick);
					rightOwner = rightChopStick.changeOwner(null, this);
				}
				LOG.debug("Philosopher N {} takes {} which is right for him", number, rightChopStick);
			}
			try {
				LOG.debug("Philosopher N {} starts eating", number);
				Thread.sleep(eatingTime);
				LOG.debug("Philosopher N {} stops eating", number);
			}
			finally {
				leftChopStick.changeOwner(null, this);
				LOG.debug("Philosopher N {} puts {} which is left for him", number, leftChopStick);
				rightChopStick.changeOwner(null, this);
				LOG.debug("Philosopher N {} puts {} which is right for him", number, rightChopStick);
				Thread.sleep(thinkingTime);
			}
		}
		catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	@Override
	public String toString() {
		return String.format("Philosopher %d", number);
	}
}
