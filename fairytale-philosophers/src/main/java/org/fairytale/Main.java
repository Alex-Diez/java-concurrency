package org.fairytale;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Main {
	private static final int CHOP_STICKS_COUNT = 5;

	public static void main(String[] args)
			throws Exception {
		ExecutorService service = Executors.newFixedThreadPool(CHOP_STICKS_COUNT+1);
		Lock[] chopSticks = createChopSticks();
		ConcurrentMap<Integer, LongAdder> statistics = new ConcurrentHashMap<>(CHOP_STICKS_COUNT, 1.0F, CHOP_STICKS_COUNT);
		for(int i = 0; i < CHOP_STICKS_COUNT; i++) {
			int leftIndex = i;
			int rightIndex = (i + 1) % CHOP_STICKS_COUNT;
			Runnable philosopher = new Philosopher(statistics, chopSticks[leftIndex], chopSticks[rightIndex], i);
			service.submit(philosopher);
		}
		Runnable syncer = new Synchronizer(statistics);
		service.submit(syncer);
		Thread.sleep(500_000);
		service.shutdown();
	}

	private static Lock[] createChopSticks() {
		Lock[] chopSticks = new Lock[CHOP_STICKS_COUNT];
		for(int i = 0; i < CHOP_STICKS_COUNT; i++) {
			chopSticks[i] = new NamedReentrantLock("Chopstick " + i, new ReentrantLock());
		}
		return chopSticks;
	}
}
