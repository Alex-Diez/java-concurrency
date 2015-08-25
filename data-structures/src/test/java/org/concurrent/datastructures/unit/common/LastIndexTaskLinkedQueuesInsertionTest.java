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
public class LastIndexTaskLinkedQueuesInsertionTest {

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

    public LastIndexTaskLinkedQueuesInsertionTest(final QueuesFactory queuesFactory) {
        this.queuesFactory = queuesFactory;
    }

    private LastIndexTaskQueue<Integer> full;
    private LastIndexTaskQueue<Integer> empty;

    @Before
    public void setUp() throws Exception {
        full = queuesFactory.buildFullQueue();
        empty = queuesFactory.buildEmptyQueue();
    }

    @Test(expected = NullPointerException.class)
    public void testAddNull_shouldThrowException()
            throws Exception {
        empty.add(null);
    }

    @Test(expected = NullPointerException.class)
    public void testOfferNull_shouldThrowException()
            throws Exception {
        empty.offer(null);
    }

    @Test
    public void testAddToEmptyQueue()
            throws Exception {
        assertThat(empty.add(1), is(true));
        assertThat(empty.size(), is(1));
        assertThat(empty.isEmpty(), is(false));
    }

    @Test
    public void testAddToFullQueue()
            throws Exception {
        assertThat(full.add(1), is(true));
        assertThat(full.size(), is(DATA.size() + 1));
        assertThat(full.isEmpty(), is(false));
    }

    @Test
    public void testOfferToEmptyQueue()
            throws Exception {
        assertThat(empty.offer(1), is(true));
        assertThat(empty.size(), is(1));
        assertThat(empty.isEmpty(), is(false));
    }

    @Test
    public void testOfferToFullQueue()
            throws Exception {
        assertThat(full.offer(1), is(true));
        assertThat(full.size(), is(DATA.size() + 1));
        assertThat(full.isEmpty(), is(false));
    }
}
