package com.google.jam.unit.datastructures.generaltests;

import com.google.jam.datastructures.LastIndexTaskLinkedBlockingQueue;
import com.google.jam.datastructures.LastIndexTaskBlockingQueue;
import com.google.jam.datastructures.LastIndexTaskLinkedQueue;
import com.google.jam.datastructures.LastIndexTaskQueue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LastIndexTaskLinkedQueuesInsertionTest {

    private static final List<Integer> DATA = Arrays.asList(1, 2, 3);

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][] {
                        {new LastIndexTaskLinkedBlockingQueue<>(DATA), new LastIndexTaskLinkedBlockingQueue<>()},
                        {new LastIndexTaskLinkedQueue<>(DATA), new LastIndexTaskLinkedQueue<>()}
                }
        );
    }

    private final LastIndexTaskQueue<Integer> full;
    private final LastIndexTaskQueue<Integer> empty;

    public LastIndexTaskLinkedQueuesInsertionTest(
            final LastIndexTaskQueue<Integer> full,
        final LastIndexTaskQueue<Integer> empty) {
        this.full = full;
        this.empty = empty;
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
    @Ignore("for blocking queue")
    public void testPutNull_shouldThrowException()
            throws Exception {
//        empty.put(null);
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
    @Ignore("for blocking queue")
    public void testPutIntoFullQueue()
            throws Exception {
//        full.put(5);
        assertThat(full.size(), is(DATA.size() + 1));
    }

    @Test
    @Ignore("for blocking queue")
    public void testOfferIntoFullQueueWithTimeOut()
            throws Exception {
//        assertThat(full.offer(1, 5L, SECONDS), is(true));
        assertThat(full.size(), is(DATA.size() + 1));
    }
}
