package com.google.jam.unit.datastructures.generaltests;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.jam.datastructures.LastIndexTaskLinkedBlockingQueue;
import com.google.jam.datastructures.LastIndexTaskLinkedQueue;
import com.google.jam.datastructures.LastIndexTaskQueue;

import org.junit.Ignore;
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
                        {new LastIndexTaskLinkedBlockingQueue<>(DATA), new LastIndexTaskLinkedBlockingQueue<>()},
                        {new LastIndexTaskLinkedQueue<>(DATA), new LastIndexTaskLinkedQueue<>()}
                }
        );
    }

    private final LastIndexTaskQueue<Integer> full;
    private final LastIndexTaskQueue<Integer> empty;

    public LastIndexTaskLinkedQueuesContainsTest(
            final LastIndexTaskQueue<Integer> full,
            final LastIndexTaskQueue<Integer> empty) {
        this.full = full;
        this.empty = empty;
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
