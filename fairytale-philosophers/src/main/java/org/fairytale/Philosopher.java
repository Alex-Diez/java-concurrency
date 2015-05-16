package org.fairytale;

import java.util.Random;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;

public class Philosopher
		implements Runnable {

	private final ConcurrentMap<Integer, LongAdder> statistics;
	private final Lock leftChopStick;
	private final Lock rightChopStick;
	private final int number;

	public Philosopher(ConcurrentMap<Integer, LongAdder> statistics, Lock leftChopStick, Lock rightChopStick, int number) {
		this.statistics = statistics;
		this.leftChopStick = leftChopStick;
		this.rightChopStick = rightChopStick;
		this.number = number;
	}

	@Override
	public void run() {
		try {
			while(true) {
				eating();
			}
		}
		catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}

	private void eating()
			throws InterruptedException {
		boolean isLeftChopStickTaken = false;
		boolean isRightChopStickTaken = false;
		Random random = new Random();
		while(!isLeftChopStickTaken || !isRightChopStickTaken) {
			int timeOut = random.nextInt(10);
			System.out.printf("Philosopher N %d Time out is %d\n", number, timeOut);
			System.out.printf("Philosopher N %d tries to take %s which is left for him\n", number, leftChopStick);
			isLeftChopStickTaken = leftChopStick.tryLock(timeOut, TimeUnit.SECONDS);
			if(isLeftChopStickTaken) {
				System.out.printf("Philosopher N %d takes %s which is left for him\n", number, leftChopStick);
				System.out.printf("Philosopher N %d tries to take %s which is right for him\n", number, rightChopStick);
				isRightChopStickTaken = rightChopStick.tryLock(timeOut, TimeUnit.SECONDS);
				if(isRightChopStickTaken) {
					System.out.printf("Philosopher N %d takes %s which is right for him\n", number, rightChopStick);
				}
			}
		}
		try {
			System.out.printf("Philosopher N %d starts eating\n", number);
			Thread.sleep(1000);
			statistics.computeIfAbsent(this.number, k -> new LongAdder()).increment();
			System.out.printf("Philosopher N %d stops eating\n", number);
		}
		finally {
			leftChopStick.unlock();
			rightChopStick.unlock();
			Thread.sleep(5000);
		}
	}
}
