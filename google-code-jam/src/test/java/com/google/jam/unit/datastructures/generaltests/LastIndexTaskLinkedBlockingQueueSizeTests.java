package com.google.jam.unit.datastructures.generaltests;

import java.util.Arrays;
import java.util.List;

import com.google.jam.datastructures.LastIndexLinkedTaskBlockingQueue;

import com.google.jam.datastructures.LastIndexTaskBlockingQueue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LastIndexTaskLinkedBlockingQueueSizeTests {

    private static final List<Integer> DATA = Arrays.asList(1, 2, 3);

    private LastIndexTaskBlockingQueue<Integer> empty;
    private LastIndexTaskBlockingQueue<Integer> full;

    @Before
    public void setUp()
            throws Exception {
        full = new LastIndexLinkedTaskBlockingQueue<>(DATA);
        empty = new LastIndexLinkedTaskBlockingQueue<>();
    }

    @Test
    @Ignore("Stop develop queue")
    public void testSizeOfFullQueue()
            throws Exception {
        assertThat(false, is(full.isEmpty()));
        assertThat(full.size(), is(DATA.size()));
    }

    @Test
    @Ignore("Stop develop queue")
    public void testSizeOfEmptyQueue()
            throws Exception {
        assertThat(true, is(empty.isEmpty()));
        assertThat(empty.size(), is(0));
    }

    @Test
    @Ignore("Stop develop queue")
    public void testRemainingCapacity()
            throws Exception {
        assertThat(empty.remainingCapacity(), is(Integer.MAX_VALUE));
    }
}
