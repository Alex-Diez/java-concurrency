package com.google.jam.unit.datastructures.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.NoSuchElementException;

import com.google.jam.datastructures.LastIndexTaskQueue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

@RunWith(Parameterized.class)
public class LastIndexTaskLinkedQueuesRetrieveTest {

    private static final List<Integer> DATA = Arrays.asList(1, 2, 3);

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][] {
                        {new LinkedQueuesFactory()},
                        {new LinkedBlockingQueuesFactory()}
                }
        );
    }

    private final QueuesFactory queuesFactory;

    public LastIndexTaskLinkedQueuesRetrieveTest(final QueuesFactory queuesFactory) {
        this.queuesFactory = queuesFactory;
    }

    private LastIndexTaskQueue<Integer> full;
    private LastIndexTaskQueue<Integer> empty;

    @Before
    public void setUp() throws Exception {
        full = queuesFactory.buildFullQueue();
        empty = queuesFactory.buildEmptyQueue();
    }

    @Test(expected = UnsupportedOperationException.class)
    public void testRemove_shouldThrowException()
            throws Exception {
        empty.remove(1);
    }

    @Test(expected = NoSuchElementException.class)
    public void testRemoveFromEmptyQueue()
            throws Exception {
        empty.remove();
    }

    @Test
    public void testPollFromEmptyQueue()
            throws Exception {
        assertThat(empty.poll(), is(nullValue()));
    }

    @Test
    public void testRemoveFromFullQueue()
            throws Exception {
        final Integer removedElement = full.remove();
        final Integer expected = DATA.get(0);
        assertThat(removedElement, is(expected));
    }

    @Test
    public void testPollFromFullQueue()
            throws Exception {
        final Integer polledElement = full.poll();
        final Integer expected = DATA.get(0);
        assertThat(polledElement, is(expected));
        assertThat(full.size(), is(DATA.size() - 1));
    }

    @Test
    public void testPeekElementFromFullQueue()
            throws Exception {
        assertThat(full.peek(), is(DATA.get(0)));
        assertThat(full.size(), is(DATA.size()));
        assertThat(full.peek(), is(DATA.get(0)));
    }

    @Test
    public void testRemoveAddedElementFromEmptyQueue()
            throws Exception {
        empty.add(1);
        empty.add(2);
        empty.add(3);
        empty.add(4);
        empty.add(5);
        assertThat(empty.poll(), is(1));
        assertThat(empty.poll(), is(2));
        assertThat(empty.poll(), is(3));
        assertThat(empty.poll(), is(4));
        assertThat(empty.poll(), is(5));
    }
}
