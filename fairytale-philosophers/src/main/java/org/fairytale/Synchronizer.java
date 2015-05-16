package org.fairytale;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.LongAdder;

public class Synchronizer
		implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(Synchronizer.class);

	private final ConcurrentMap<Integer, LongAdder> statistics;

	public Synchronizer(ConcurrentMap<Integer, LongAdder> statistics) {
		this.statistics = statistics;
	}

	@Override
	public void run() {
		try {
			int i = 0;
			while(i < 6) {
				Thread.sleep(10_000);
				LOG.info("Statistics is - {}", statistics);
				i++;
			}
		}
		catch(InterruptedException e) {
			Thread.currentThread().interrupt();
		}
	}
}
