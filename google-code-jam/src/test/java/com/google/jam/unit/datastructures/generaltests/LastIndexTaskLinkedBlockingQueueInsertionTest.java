package com.google.jam.unit.datastructures.generaltests;

import com.google.jam.datastructures.LastIndexLinkedTaskBlockingQueue;
import com.google.jam.datastructures.LastIndexTaskBlockingQueue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LastIndexTaskLinkedBlockingQueueInsertionTest {

    private static final List<Integer> DATA = Arrays.asList(1, 2, 3);

    private LastIndexTaskBlockingQueue<Integer> empty;
    private LastIndexTaskBlockingQueue<Integer> full;

    @Before
    public void setUp()
            throws Exception {
        full = new LastIndexLinkedTaskBlockingQueue<>(DATA);
        empty = new LastIndexLinkedTaskBlockingQueue<>();
    }

    @Test(expected = NullPointerException.class)
    @Ignore("Stop develop queue")
    public void testAddNull_shouldThrowException()
            throws Exception {
        empty.add(null);
    }

    @Test(expected = NullPointerException.class)
    @Ignore("Stop develop queue")
    public void testOfferNull_shouldThrowException()
            throws Exception {
        empty.offer(null);
    }

    @Test(expected = NullPointerException.class)
    @Ignore("Stop develop queue")
    public void testPutNull_shouldThrowException()
            throws Exception {
        empty.put(null);
    }

    @Test
    @Ignore("Stop develop queue")
    public void testAddToEmptyQueue()
            throws Exception {
        assertThat(empty.add(1), is(true));
        assertThat(empty.size(), is(1));
        assertThat(empty.isEmpty(), is(false));
    }

    @Test
    @Ignore("Stop develop queue")
    public void testAddToFullQueue()
            throws Exception {
        assertThat(full.add(1), is(true));
        assertThat(full.size(), is(DATA.size() + 1));
        assertThat(full.isEmpty(), is(false));
    }

    @Test
    @Ignore("Stop develop queue")
    public void testOfferToEmptyQueue()
            throws Exception {
        assertThat(empty.offer(1), is(true));
        assertThat(empty.size(), is(1));
        assertThat(empty.isEmpty(), is(false));
    }

    @Test
    @Ignore("Stop develop queue")
    public void testOfferToFullQueue()
            throws Exception {
        assertThat(full.offer(1), is(true));
        assertThat(full.size(), is(DATA.size() + 1));
        assertThat(full.isEmpty(), is(false));
    }

    @Test
    @Ignore("Stop develop queue")
    public void testPutIntoFullQueue()
            throws Exception {
        full.put(5);
        assertThat(full.size(), is(DATA.size() + 1));
    }

    @Test
    @Ignore("Stop develop queue")
    public void testOfferIntoFullQueueWithTimeOut()
            throws Exception {
        assertThat(full.offer(1, 5L, SECONDS), is(true));
        assertThat(full.size(), is(DATA.size() + 1));
    }
}
