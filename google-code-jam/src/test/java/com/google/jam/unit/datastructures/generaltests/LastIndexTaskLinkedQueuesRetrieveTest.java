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
import java.util.NoSuchElementException;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class LastIndexTaskLinkedQueuesRetrieveTest {

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

    private final LastIndexTaskQueue<Integer> empty;
    private final LastIndexTaskQueue<Integer> full;

    public LastIndexTaskLinkedQueuesRetrieveTest(
            final LastIndexTaskQueue<Integer> empty,
            final LastIndexTaskQueue<Integer> full) {
        this.empty = empty;
        this.full = full;
    }

    @Test(expected = UnsupportedOperationException.class)
    @Ignore("Stop develop queue")
    public void testRemove_shouldThrowException()
            throws Exception {
        empty.remove(1);
    }

    @Test(expected = NoSuchElementException.class)
    @Ignore("Stop develop queue")
    public void testRemoveFromEmptyQueue()
            throws Exception {
        empty.remove();
    }

    @Test
    @Ignore("Stop develop queue")
    public void testPollFromEmptyQueue()
            throws Exception {
        assertThat(empty.poll(), is(nullValue()));
    }

    @Test
    @Ignore("for blocking queue")
    public void testTakeOnFullQueue()
            throws Exception {
//        final Integer takenElement = full.take();
        final Integer expected = DATA.get(0);
//        assertThat(takenElement, is(expected));
    }

    @Test
    @Ignore("Stop develop queue")
    public void testRemoveFromFullQueue()
            throws Exception {
        final Integer removedElement = full.remove();
        final Integer expected = DATA.get(0);
        assertThat(removedElement, is(expected));
    }

    @Test
    @Ignore("Stop develop queue")
    public void testPollFromFullQueue()
            throws Exception {
        final Integer polledElement = full.poll();
        final Integer expected = DATA.get(0);
        assertThat(polledElement, is(expected));
        assertThat(full.size(), is(DATA.size() - 1));
    }

    @Test
    @Ignore("for blocking queue")
    public void testPollElementWithTimeOut()
            throws Exception {
//        assertThat(full.poll(5L, SECONDS), is(DATA.get(0)));
        assertThat(full.size(), is(DATA.size() - 1));
    }

    @Test
    @Ignore("Stop develop queue")
    public void testPeekElementFromFullQueue()
            throws Exception {
        assertThat(full.peek(), is(DATA.get(0)));
        assertThat(full.size(), is(DATA.size()));
        assertThat(full.peek(), is(DATA.get(0)));
    }
}
