package com.google.jam.unit.datastructures.concurrency;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

import com.google.jam.datastructures.LastIndexTaskLinkedBlockingQueue;
import com.google.jam.datastructures.LastIndexTaskQueue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static java.util.concurrent.TimeUnit.*;
import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class LastIndexTaskLinkedBlockingQueueRetrieveSizeTest {

	private static final List<Integer> DATA = Arrays.asList(
			1,
			2,
			3,
			4,
			5,
			6,
			7,
			8,
			9,
			10,
			11,
			12,
			13,
			14,
			15,
			16,
			17,
			18,
			19,
			20,
			21,
			22,
			23,
			24,
			25,
			26,
			27,
			28,
			29,
			30
	);

	private static final int COEFFICIENT = 10000;

	@Parameterized.Parameters
	public static Collection<Object[]> data() {
		return Arrays.asList(
				new Object[][] {
						{2, 1},
						{3, 2},
						{4, 2},
						{6, 5},
						{8, 5},
						{12, 9},
						{16, 9},
						{24, 16},
						{32, 16},
						{48, 32},
						{64, 32}
				});
	}

	private final int numberOfThreads;
	private final int numberOfReaders;

	public LastIndexTaskLinkedBlockingQueueRetrieveSizeTest(final int numberOfThreads, final int numberOfReaders) {
		this.numberOfThreads = numberOfThreads;
		this.numberOfReaders = numberOfReaders;
	}

	@Test
	public void testQueueSizeInConcurrenceEnv()
			throws Exception {
		final int numberOfRead = 10;
		final int numberOfWriters = numberOfThreads - numberOfReaders;
		final LastIndexTaskQueue<Integer, Integer> queue = new LastIndexTaskLinkedBlockingQueue<>();
		List<Boolean> results;
		final ExecutorService executor = Executors.newFixedThreadPool(numberOfThreads);
		try {
			final CyclicBarrier writerBarrier = new CyclicBarrier(numberOfWriters);
			final List<Future<Boolean>> writeFutures = new ArrayList<>(numberOfReaders);
			for (int i = 0; i < numberOfWriters; i++) {
				writeFutures.add(
						executor.submit(
								() -> {
									final Thread currentThread = Thread.currentThread();
									final int threadID = (int) currentThread.getId();
									int c = 0;
									try {
										writerBarrier.await();
										while (c < DATA.size()) {
											queue.add(DATA.get(c) * threadID * COEFFICIENT);
											c++;
										}
									}
									catch (InterruptedException e) {
										currentThread.interrupt();
										return false;
									}
									return true;
								}
						)
				);
			}
			final List<Future<Boolean>> readFutures = new ArrayList<>(numberOfReaders);
			final CyclicBarrier readerBarrier = new CyclicBarrier(numberOfReaders);
			for (int i = 0; i < numberOfReaders; i++) {
				readFutures.add(
						executor.submit(
								() -> {
									final Thread currentThread = Thread.currentThread();
									int c = 0;
									try {
										readerBarrier.await();
										while (c < numberOfRead) {
											queue.poll();
											c++;
										}
									}
									catch (InterruptedException e) {
										currentThread.interrupt();
										return false;
									}
									return true;
								}
						)
				);
			}
			results = new ArrayList<>(writeFutures.size() + readFutures.size());
			writeFutures.forEach(
					(f) -> {
						try {
							results.add(f.get());
						}
						catch (InterruptedException | ExecutionException e) {
							fail("Fail on get writers results");
						}
					}
			);
			readFutures.forEach(
					(f) -> {
						try {
							results.add(f.get());
						}
						catch (InterruptedException | ExecutionException e) {
							fail("Fail on get readers results");
						}
					}
			);
		}
		finally {
			executor.awaitTermination(numberOfThreads*10, SECONDS);
			executor.shutdown();
		}
		assertThat(results.contains(false), is(false));
		assertThat(queue.size(), is(DATA.size() * numberOfWriters - numberOfReaders * numberOfRead));
	}
}
