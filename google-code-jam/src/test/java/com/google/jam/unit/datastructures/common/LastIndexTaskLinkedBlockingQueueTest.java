package com.google.jam.unit.datastructures.common;

import java.util.Arrays;
import java.util.List;

import com.google.jam.datastructures.LastIndexTaskBlockingQueue;
import com.google.jam.datastructures.LastIndexTaskLinkedBlockingQueue;

import org.junit.Before;
import org.junit.Test;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LastIndexTaskLinkedBlockingQueueTest {

    private static final List<Integer> DATA = Arrays.asList(1, 2, 3);

    private LastIndexTaskBlockingQueue<Integer> empty;
    private LastIndexTaskBlockingQueue<Integer> full;

    @Before
    public void setUp()
            throws Exception {
        full = new LastIndexTaskLinkedBlockingQueue<>(DATA);
        empty = new LastIndexTaskLinkedBlockingQueue<>();
    }

    @Test
    public void testDrainIntoAnotherQueue()
            throws Exception {
        assertThat(full.drainTo(empty, DATA.size()), is(DATA.size()));
        assertThat(full.isEmpty(), is(true));
        assertThat(empty.size(), is(DATA.size()));
    }

    @Test(expected = NullPointerException.class)
    public void testDrainToNull_shouldThrowException()
            throws Exception {
        full.drainTo(null, DATA.size());
    }

    @Test
    public void testDrainPartOfQueueToAnotherQueue()
            throws Exception {
        assertThat(full.drainTo(empty, 2), is(2));
        assertThat(empty.size(), is(2));
    }

    @Test(expected = NullPointerException.class)
    public void testPutNull_shouldThrowException()
            throws Exception {
        empty.put(null);
    }

    @Test
    public void testPutIntoFullQueue()
            throws Exception {
        full.put(5);
        assertThat(full.size(), is(DATA.size() + 1));
    }

    @Test
    public void testOfferIntoFullQueueWithTimeOut()
            throws Exception {
        assertThat(full.offer(1, 5L, SECONDS), is(true));
        assertThat(full.size(), is(DATA.size() + 1));
    }

    @Test
    public void testTakeOnFullQueue()
            throws Exception {
        final Integer takenElement = full.take();
        final Integer expected = DATA.get(0);
        assertThat(takenElement, is(expected));
    }

    @Test
    public void testPollElementWithTimeOut()
            throws Exception {
        assertThat(full.poll(5L, SECONDS), is(DATA.get(0)));
        assertThat(full.size(), is(DATA.size() - 1));
    }

    @Test
    public void testRemainingCapacity()
            throws Exception {
        assertThat(empty.remainingCapacity(), is(Integer.MAX_VALUE));
    }
}
