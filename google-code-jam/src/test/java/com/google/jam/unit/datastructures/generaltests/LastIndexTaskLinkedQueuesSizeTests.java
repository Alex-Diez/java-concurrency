package com.google.jam.unit.datastructures.generaltests;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.jam.datastructures.LastIndexTaskQueue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class LastIndexTaskLinkedQueuesSizeTests {

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

    public LastIndexTaskLinkedQueuesSizeTests(final QueuesFactory queuesFactory) {
        this.queuesFactory = queuesFactory;
    }

    private LastIndexTaskQueue<Integer> full;
    private LastIndexTaskQueue<Integer> empty;

    @Before
    public void setUp() throws Exception {
        full = queuesFactory.buildFullQueue();
        empty = queuesFactory.buildEmptyQueue();
    }

    @Test
    public void testSizeOfFullQueue()
            throws Exception {
        assertThat(false, is(full.isEmpty()));
        assertThat(full.size(), is(DATA.size()));
    }

    @Test
    public void testSizeOfEmptyQueue()
            throws Exception {
        assertThat(true, is(empty.isEmpty()));
        assertThat(empty.size(), is(0));
    }
}
