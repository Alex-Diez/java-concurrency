package com.google.jam.unit.datastructures.generaltests;

import com.google.jam.datastructures.LastIndexLinkedTaskBlockingQueue;
import com.google.jam.datastructures.LastIndexTaskBlockingQueue;
import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LastIndexTaskLinkedBlockingQueueDrainTest {

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
    public void testDrainIntoAnotherQueue()
            throws Exception {
        assertThat(full.drainTo(empty, DATA.size()), is(DATA.size()));
        assertThat(full.isEmpty(), is(true));
        assertThat(empty.size(), is(DATA.size()));
    }

    @Test(expected = NullPointerException.class)
    @Ignore("Stop develop queue")
    public void testDrainToNull_shouldThrowException()
            throws Exception {
        full.drainTo(null, DATA.size());
    }

    @Test
    @Ignore("Stop develop queue")
    public void testDrainPartOfQueueToAnotherQueue()
            throws Exception {
        assertThat(full.drainTo(empty, 2), is(2));
        assertThat(empty.size(), is(2));
    }
}
