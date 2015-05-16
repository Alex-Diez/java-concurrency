package org.fairytale;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.LongAdder;

public class Synchronizer
		implements Runnable {

	private final ConcurrentMap<Integer, LongAdder> statistics;

	public Synchronizer(ConcurrentMap<Integer, LongAdder> statistics) {
		this.statistics = statistics;
	}

	@Override
	public void run() {
		try {
			while(true) {
				Thread.sleep(100_000);
				System.out.println("Statistics is - \n" + statistics);
			}
		}
		catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
