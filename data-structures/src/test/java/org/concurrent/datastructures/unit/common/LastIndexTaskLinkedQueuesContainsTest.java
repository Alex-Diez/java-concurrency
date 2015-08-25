package org.concurrent.datastructures.unit.common;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import org.concurrent.datastructures.LastIndexTaskQueue;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import org.junit.runners.Parameterized.Parameters;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

@RunWith(Parameterized.class)
public class LastIndexTaskLinkedQueuesContainsTest {

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

    public LastIndexTaskLinkedQueuesContainsTest(final QueuesFactory queuesFactory) {
        this.queuesFactory = queuesFactory;
    }

    private  LastIndexTaskQueue<Integer> full;
    private  LastIndexTaskQueue<Integer> empty;

    @Before
    public void setUp()
            throws Exception {
        empty = queuesFactory.buildEmptyQueue();
        full = queuesFactory.buildFullQueue();
    }

    @Test
    public void testContainsOnEmptyQueue()
            throws Exception {
        assertThat("[ " + empty + " " + empty.getClass().getSimpleName() + " ]", empty.contains(1), is(false));
    }

    @Test
    public void testContainsOnFullQueue()
            throws Exception {
        final Integer expected = DATA.get(0);
        assertThat("[ " + full.getClass().getSimpleName() + " ]", full.contains(expected), is(true));
    }
}
