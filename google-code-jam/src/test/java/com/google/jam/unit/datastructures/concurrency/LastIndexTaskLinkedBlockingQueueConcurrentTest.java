package com.google.jam.unit.datastructures.concurrency;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.Callable;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import com.google.jam.datastructures.LastIndexTaskLinkedBlockingQueue;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.hamcrest.Matchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

@RunWith(Parameterized.class)
public class LastIndexTaskLinkedBlockingQueueConcurrentTest {

    private static final List<Integer> DATA = Arrays.asList(
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
            21, 22, 23, 24, 25, 26, 27, 28, 29, 30
    );

    private static final int COEFFICIENT = 10000;
    private static final int NUMBER_OF_READ = 10;

    @Parameters
    public static Collection<Object[]> data() {
        return new ConcurrencyTestCaseFactory().createNumberOfThreadForTestCases(10);
    }

    private final int numberOfReaders;
    private final int numberOfWriters;
    private final CyclicBarrier writerBarrier;
    private final CyclicBarrier readerBarrier;
    private final List<Future<Integer>> writeFutures;
    private final List<Future<Integer>> readFutures;

    private final ExecutorService executor;

    public LastIndexTaskLinkedBlockingQueueConcurrentTest(final int numberOfThreads, final int numberOfReaders) {
        this.numberOfReaders = numberOfReaders;
        this.numberOfWriters = numberOfThreads - numberOfReaders;
        writerBarrier = new CyclicBarrier(numberOfWriters);
        readerBarrier = new CyclicBarrier(numberOfReaders);
        writeFutures = new CopyOnWriteArrayList<>();
        readFutures = new CopyOnWriteArrayList<>();
        executor = Executors.newFixedThreadPool(numberOfReaders + numberOfWriters);
    }

    private volatile BlockingQueue<Integer> queue;

    @Before
    public void setUp()
            throws Exception {
        queue = new LastIndexTaskLinkedBlockingQueue<>();
    }

    @Test
    public void testQueueSizeInConcurrentEnvironment()
            throws Exception {
        assertThat(queue.size(), is(0));
        final Callable<Integer> writer = () -> {
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
            }
            return c;
        };
        submitWriters(writer);
        final int numberOfWriteOperations = calculateNumberOfOperations(writeFutures, "Fail on get writers results");
        final Callable<Integer> reader = () -> {
            final Thread currentThread = Thread.currentThread();
            int c = 0;
            try {
                readerBarrier.await();
                while (c < NUMBER_OF_READ) {
                    queue.poll();
                    c++;
                }
            }
            catch (InterruptedException e) {
                currentThread.interrupt();
            }
            return c;
        };
        submitReaders(reader);
        final int numberOfReadOperations = calculateNumberOfOperations(readFutures, "Fail on get readers results");
        assertThat(queue.size(), is(numberOfWriteOperations - numberOfReadOperations));
    }

    private void submitReaders(Callable<Integer> reader) {
        for (int i = 0; i < numberOfReaders; i++) {
            readFutures.add(executor.submit(reader));
        }
    }

    private void submitWriters(Callable<Integer> writer) {
        for (int i = 0; i < numberOfWriters; i++) {
            writeFutures.add(executor.submit(writer));
        }
    }

    private int calculateNumberOfOperations(
            final List<Future<Integer>> futures,
            final String failMessage) {
        final List<Integer> results = new CopyOnWriteArrayList<>();
        futures.forEach(
                (f) -> {
                    try {
                        results.add(f.get());
                    }
                    catch (InterruptedException | ExecutionException e) {
                        fail(failMessage);
                    }
                }
        );
        int sum = 0;
        for (int i : results) {
            sum += i;
        }
        return sum;
    }

    @Test
    public void testQueueSizeInAsynchronousConcurrentEnvironment()
            throws Exception {
        final Callable<Integer> writer = () -> {
            final Thread currentThread = Thread.currentThread();
            final int threadID = (int) currentThread.getId();
            int c = 0;
            int writeOperations = 0;
            while (c < DATA.size()) {
                if (queue.add(DATA.get(c) * threadID * COEFFICIENT)) {
                    writeOperations++;
                }
                c++;
            }
            return writeOperations;
        };
        submitWriters(writer);
        final Callable<Integer> reader = () -> {
            int c = 0;
            int readOperations = 0;
            while (c < NUMBER_OF_READ) {
                if (queue.poll() != null) {
                    readOperations++;
                }
                c++;
            }
            return readOperations;
        };
        submitReaders(reader);
        final int numberOfWriteOperations = calculateNumberOfOperations(writeFutures, "Fail on get writers results");
        final int numberOfReadOperations = calculateNumberOfOperations(readFutures, "Fail on get readers results");
        final int numberOfOperations = numberOfWriteOperations - numberOfReadOperations;
        final int resultSize = numberOfOperations > 0 ? numberOfOperations : 0;
        assertThat(queue.size(), is(resultSize));
    }

    @After
    public void tearDown()
            throws Exception {
        executor.shutdown();
    }
}
