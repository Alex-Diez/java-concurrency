package org.fairytale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;

public class Philosopher
		implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(Philosopher.class);

	private final ConcurrentMap<Integer, LongAdder> statistics;
	private final Lock leftChopStick;
	private final Lock rightChopStick;
	private final int number;
	private final CountDownLatch latch;

	public Philosopher(
			ConcurrentMap<Integer, LongAdder> statistics,
			Lock leftChopStick,
			Lock rightChopStick,
			int number,
			CountDownLatch latch) {
		this.statistics = statistics;
		this.leftChopStick = leftChopStick;
		this.rightChopStick = rightChopStick;
		this.number = number;
		this.latch = latch;
	}

	@Override
	public void run() {
		try {
			latch.await();
			long startTime = System.currentTimeMillis();
			long nextIterationTime = System.currentTimeMillis();
			while ((nextIterationTime - startTime) < 60_000) {
				eating();
				nextIterationTime = System.currentTimeMillis();
			}
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void eating()
			throws InterruptedException {
		boolean isLeftChopStickTaken = false;
		boolean isRightChopStickTaken = false;
		while (!isLeftChopStickTaken
				&& !isRightChopStickTaken) {
			LOG.info("Philosopher N {} tries to take {} which is left for him", number, leftChopStick);
			while (!(isLeftChopStickTaken = leftChopStick.tryLock())) {
				LOG.info("Philosopher N {} doesn't take {} which is left for him", number, leftChopStick);
				if(isRightChopStickTaken) {
					LOG.info("Philosopher N {} has to put {} which is right for him", number, rightChopStick);
					rightChopStick.unlock();
				}
			}
			LOG.info("Philosopher N {} takes {} which is left for him", number, leftChopStick);
			LOG.info("Philosopher N {} tries to take {} which is right for him", number, rightChopStick);
			while (!(isRightChopStickTaken = rightChopStick.tryLock())) {
				LOG.info("Philosopher N {} doesn't take {} which is right for him", number, rightChopStick);
				if(isLeftChopStickTaken) {
					LOG.info("Philosopher N {} has to put {} which is left for him", number, leftChopStick);
					leftChopStick.unlock();
				}
			}
			LOG.info("Philosopher N {} takes {} which is right for him", number, rightChopStick);
		}
		try {
			LOG.info("Philosopher N {} starts eating", number);
			Thread.sleep(1000);
			statistics.computeIfAbsent(this.number, k -> new LongAdder()).increment();
			LOG.info("Philosopher N {} stops eating", number);
		}
		finally {
			leftChopStick.unlock();
			LOG.info("Philosopher N {} puts {} which is left for him", number, leftChopStick);
			rightChopStick.unlock();
			LOG.info("Philosopher N {} puts {} which is right for him", number, rightChopStick);
			Thread.sleep(5000);
		}
	}
}
