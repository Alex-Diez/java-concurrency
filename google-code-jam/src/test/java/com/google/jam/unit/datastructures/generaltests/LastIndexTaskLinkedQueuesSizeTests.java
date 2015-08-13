package com.google.jam.unit.datastructures.generaltests;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.jam.datastructures.LastIndexTaskLinkedBlockingQueue;

import com.google.jam.datastructures.LastIndexTaskBlockingQueue;
import com.google.jam.datastructures.LastIndexTaskLinkedQueue;
import com.google.jam.datastructures.LastIndexTaskQueue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LastIndexTaskLinkedQueuesSizeTests {

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

    public LastIndexTaskLinkedQueuesSizeTests(
            final LastIndexTaskQueue<Integer> full,
        final LastIndexTaskQueue<Integer> empty) {
        this.full = full;
        this.empty = empty;
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
    @Ignore("for blocking queue")
    public void testRemainingCapacity()
            throws Exception {
//        assertThat(empty.remainingCapacity(), is(Integer.MAX_VALUE));
    }
}
