package org.fairytale;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashSet;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.LongAdder;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

import org.fairytale.matchers.MapElements;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.runners.Parameterized.Parameters;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class StarvationTest {

	private final static long NUMBERS_OF_EATING = 10;

	//configuration values
	private final int numberOfChopsticks;
	private final long numbersOfEating;

	@Parameters
	public static Collection configuration() {
		return Arrays.asList(
				new Object[][] {
						//TODO HARDCODE! for a while number of eating can be only 10
						{3, NUMBERS_OF_EATING},
						{4, NUMBERS_OF_EATING},
						{5, NUMBERS_OF_EATING},
						{6, NUMBERS_OF_EATING},
						{7, NUMBERS_OF_EATING},
						{8, NUMBERS_OF_EATING},
						{9, NUMBERS_OF_EATING},
						{10, NUMBERS_OF_EATING},
						{11, NUMBERS_OF_EATING},
						{12, NUMBERS_OF_EATING},
						{13, NUMBERS_OF_EATING},
						{14, NUMBERS_OF_EATING},
						{15, NUMBERS_OF_EATING},
						{16, NUMBERS_OF_EATING},
						{17, NUMBERS_OF_EATING},
						{18, NUMBERS_OF_EATING},
						{19, NUMBERS_OF_EATING},
						{20, NUMBERS_OF_EATING},
						{21, NUMBERS_OF_EATING},
						{22, NUMBERS_OF_EATING}
				}
		);
	}

	//tested values
	private Set<Philosopher> philosophers;
	private ConcurrentMap<Philosopher, LongAdder> statistics;
	private CountDownLatch latch;

	public StarvationTest(int numberOfChopsticks, long numbersOfEating) {
		this.numberOfChopsticks = numberOfChopsticks;
		this.numbersOfEating = numbersOfEating;
	}

	@Before
	public void init() {
		Lock[] chopSticks = new Lock[numberOfChopsticks];
		for(int i = 0; i < numberOfChopsticks; i++) {
			chopSticks[i] = new NamedReentrantLock("Chopstick " + i, new ReentrantLock());
		}
		statistics = new ConcurrentHashMap<>(numberOfChopsticks, 1.0F, numberOfChopsticks);
		latch = new CountDownLatch(numberOfChopsticks);
		philosophers = new LinkedHashSet<>(numberOfChopsticks);
		for(int i = 0; i < numberOfChopsticks; i++) {
			int leftIndex = i;
			int rightIndex = (i + 1) % numberOfChopsticks;
			philosophers.add(new Philosopher(statistics, chopSticks[leftIndex], chopSticks[rightIndex], i, latch));
		}
	}

	@Test
	public void philosophersShouldNotStarve()
			throws Exception {
		ExecutorService service = Executors.newFixedThreadPool(numberOfChopsticks);
		for(Runnable philosopher : philosophers) {
			service.submit(philosopher);
			latch.countDown();
		}
		Thread.sleep(numbersOfEating * 6_000);
		service.shutdown();
		assertThat(statistics, new MapElements<>(philosophers, numbersOfEating));
	}
}
