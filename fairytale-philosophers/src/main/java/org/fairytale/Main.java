package org.fairytale;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
	private static final int CHOP_STICKS_COUNT = 5;

	public static void main(String[] args) {
		ExecutorService service = Executors.newFixedThreadPool(CHOP_STICKS_COUNT);
		Lock[] chopSticks = createChopSticks();
		CountDownLatch latch = new CountDownLatch(CHOP_STICKS_COUNT);
		for (int i = 0; i < CHOP_STICKS_COUNT; i++) {
			int leftIndex = i;
			int rightIndex = (i + 1) % CHOP_STICKS_COUNT;
			Runnable philosopher = new Philosopher(i, chopSticks[leftIndex], chopSticks[rightIndex], latch);
			service.submit(philosopher);
			latch.countDown();
		}
		latch.countDown();
	}

	private static Lock[] createChopSticks() {
		Lock[] chopSticks = new Lock[CHOP_STICKS_COUNT];
		for (int i = 0; i < CHOP_STICKS_COUNT; i++) {
			chopSticks[i] = new NamedReentrantLock("Chopstick "+i, new ReentrantLock());
		}
		return chopSticks;
	}
}
