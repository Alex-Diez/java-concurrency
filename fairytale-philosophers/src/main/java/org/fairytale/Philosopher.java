package org.fairytale;

import java.util.LinkedList;
import java.util.Queue;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class Philosopher
		implements Runnable {

	private static final int SLEEPING_ENERGY_COEFFICIENT = 10000;
	private static final int MIN_SLEEPING_TIME = 100;
	private static final int MAX_SLEEPING_TIME = 800;

	private static final int THINKING_ENERGY_COEFFICIENT = 5000;
	private static final int MIN_THINKING_TIME = 100;
	public static final int MAX_THINKING_TIME = 250;

	private static final int MAX_ENERGY = 100;

	private final Queue<Action> actions;
	private final Lock leftChopStick;
	private final Lock rightChopStick;
	private final int number;
	private long energy;

	public Philosopher(int number, Lock leftChopStick, Lock rightChopStick) {
		this.actions = new LinkedList<>();
		this.energy = MAX_ENERGY;
		this.number = number;
		this.leftChopStick = leftChopStick;
		this.rightChopStick = rightChopStick;
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

	private void sleeping()
			throws InterruptedException {
		Random random = new Random();
		long sleepingTime = (random.nextInt(MAX_SLEEPING_TIME - MIN_SLEEPING_TIME) + MIN_SLEEPING_TIME) * 1000L;
		Thread.sleep(sleepingTime);
		energy = sleepingTime / SLEEPING_ENERGY_COEFFICIENT;
	}

	private void thinking()
			throws InterruptedException {
		Random random = new Random();
		long thinkingTime = (random.nextInt(MAX_THINKING_TIME - MIN_THINKING_TIME) + MIN_THINKING_TIME) * 1000L;
		Thread.sleep(thinkingTime);
		energy = thinkingTime / THINKING_ENERGY_COEFFICIENT;
	}

	private void eating()
			throws InterruptedException {
		boolean isLeftChopStickTaken = false;
		boolean isRightChopStickTaken = false;
		while(!isLeftChopStickTaken && !isRightChopStickTaken) {
			isLeftChopStickTaken = leftChopStick.tryLock(energy, TimeUnit.SECONDS);
			if(isLeftChopStickTaken) {
				System.out.printf("Philosopher N %d takes %s which is left for him", number, leftChopStick);
				System.out.println();
				isRightChopStickTaken = rightChopStick.tryLock(energy, TimeUnit.SECONDS);
				if(isRightChopStickTaken) {
					System.out.printf("Philosopher N %d takes %s which is right for him", number, rightChopStick);
					System.out.println();
				}
			}
		}
		try {
			long timeToEat = (MAX_ENERGY - energy) * 1000;
			System.out.printf("Philosopher N %d starts eating", number);
			System.out.println();
			Thread.sleep(timeToEat);
			System.out.printf("Philosopher N %d stops eating", number);
			System.out.println();
		}
		finally {
			leftChopStick.unlock();
			rightChopStick.unlock();
		}
	}
}
