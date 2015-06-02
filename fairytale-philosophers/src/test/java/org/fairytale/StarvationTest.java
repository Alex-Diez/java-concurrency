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

import org.fairytale.matchers.DirectStarvationThreadMatcher;
import org.fairytale.matchers.RelativeStarvationThreadMatcher;
import org.fairytale.util.NamedReentrantLock;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.junit.runners.Parameterized.Parameters;

import static org.hamcrest.MatcherAssert.assertThat;

@RunWith(Parameterized.class)
public class StarvationTest {

	//configuration values
	private final int numberOfChopsticks;
	private final long numbersOfEating;
	private final long eatingTime;
	private final long thinkingTime;

	@Parameters
	public static Collection configuration() {
		return Arrays.asList(
				new Object[][] {
						{3, 10, 1000, 4000},
						{4, 10, 1000, 4000},
						{5, 10, 1000, 4000},
						{6, 10, 1000, 4000},
						{7, 10, 1000, 4000},
						{8, 10, 1000, 4000},
						{9, 10, 1000, 4000},
						{10, 10, 1000, 4000},
						{11, 10, 1000, 4000},
						{12, 10, 1000, 4000},
						{3, 20, 2000, 3000},
						{4, 20, 2000, 3000},
						{5, 20, 2000, 3000},
						{6, 20, 2000, 3000},
						{7, 20, 2000, 3000},
						{8, 20, 2000, 3000},
						{9, 20, 2000, 3000},
						{10, 20, 2000, 3000},
						{11, 20, 2000, 3000},
						{12, 20, 2000, 3000}
				}
		);
	}

	//tested values
	private Set<Runnable> philosophers;
	private ConcurrentMap<Runnable, LongAdder> statistics;

	public StarvationTest(int numberOfChopsticks, long numbersOfEating, long eatingTime, long thinkingTime) {
		this.numberOfChopsticks = numberOfChopsticks;
		this.numbersOfEating = numbersOfEating;
		this.eatingTime = eatingTime;
		this.thinkingTime = thinkingTime;
	}

	@Before
	public void init() {
		initStatistics();
		initPhilosophers();
	}

	private void initPhilosophers() {
		final Lock[] chopSticks = buildChopsticks();
		philosophers = new LinkedHashSet<>(numberOfChopsticks);
		for(int i = 0; i < numberOfChopsticks; i++) {
			int leftIndex = findIndexOfLeftChopstick(i);
			int rightIndex = findIndexOfRightChopstick(i);
			philosophers.add(buildPhilosopher(chopSticks[leftIndex], chopSticks[rightIndex], i));
		}
	}

	private int findIndexOfRightChopstick(int i) {
		return i;
	}

	private int findIndexOfLeftChopstick(int i) {
		return (i + 1) % numberOfChopsticks;
	}

	private Lock[] buildChopsticks() {
		final Lock[] chopSticks = new Lock[numberOfChopsticks];
		for(int i = 0; i < numberOfChopsticks; i++) {
			chopSticks[i] = new NamedReentrantLock("ChopStick " + i, false);
		}
		return chopSticks;
	}

	private Philosopher buildPhilosopher(Lock chopStickLeft, Lock chopStickRight, int number) {
		return new Philosopher(chopStickLeft, chopStickRight, number) {
			@Override
			public void statistic() {
				statistics.computeIfAbsent(this, k -> new LongAdder()).increment();
			}

			@Override
			public void eating() throws InterruptedException {
				Thread.sleep(eatingTime);
			}

			@Override
			public void thinking() throws InterruptedException {
				Thread.sleep(thinkingTime);
			}
		};
	}

	private void initStatistics() {
		statistics = new ConcurrentHashMap<>(
				numberOfChopsticks,
				1.0F,
				numberOfChopsticks
		);
	}

	@Test
	public void philosophersRelativeStarvationTest() {
		final ExecutorService service = initializeThreadPool();
		final long executionTime = calculateExecutionTime();
		submitPhilosophers(service, executionTime);
		waitTime(executionTime);
		service.shutdown();
		assertThat(statistics, new RelativeStarvationThreadMatcher<>(philosophers, 0.1F));
	}

	@Test
	public void philosophersDirectStarvationTest() {
		final ExecutorService service = initializeThreadPool();
		final long executionTime = calculateExecutionTime();
		submitPhilosophers(service, executionTime);
		waitTime(executionTime);
		service.shutdown();
		assertThat(statistics, new DirectStarvationThreadMatcher<>(philosophers, numbersOfEating));
	}

	private void waitTime(long executionTime) {
		try {
			Thread.sleep(executionTime);
		}
		catch (InterruptedException e) {
			Thread.currentThread().interrupt();
			throw new RuntimeException(e);
		}
	}

	private ExecutorService initializeThreadPool() {
		return Executors.newFixedThreadPool(numberOfChopsticks);
	}

	private long calculateExecutionTime() {
		return (eatingTime + thinkingTime) * numbersOfEating;
	}

	private void submitPhilosophers(ExecutorService service, long executionTime) {
		final CountDownLatch latch = new CountDownLatch(numberOfChopsticks);
		for(Runnable philosopher : philosophers) {
			service.submit(buildTestRunnable(latch, executionTime, philosopher));
			latch.countDown();
		}
	}

	private Runnable buildTestRunnable(CountDownLatch latch, long executionTime, Runnable philosopher) {
		return () -> {
			try {
				latch.await();
			}
			catch(InterruptedException e) {
				Thread.currentThread().interrupt();
			}
			final long startTime = System.currentTimeMillis();
			long nextIterationTime = System.currentTimeMillis();
			while((nextIterationTime - startTime) < executionTime) {
				philosopher.run();
				nextIterationTime = System.currentTimeMillis();
			}
		};
	}
}
