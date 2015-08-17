package com.google.jam.unit.datastructures.generaltests;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import com.google.jam.datastructures.LastIndexTaskLinkedBlockingQueue;
import com.google.jam.datastructures.LastIndexTaskLinkedQueue;
import com.google.jam.datastructures.LastIndexTaskQueue;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class LastIndexTaskLinkedQueuesTasksIdentifierTest {

    private static final List<Integer> DATA = Arrays.asList(1, 2, 3);

    @Parameters
    public static Collection<Object[]> data() {
        return Arrays.asList(
                new Object[][] {
                        {new LastIndexTaskLinkedBlockingQueue<>(DATA)},
                        {new LastIndexTaskLinkedQueue<>(DATA)}
                }
        );
    }

    private final LastIndexTaskQueue<Integer> full;

    public LastIndexTaskLinkedQueuesTasksIdentifierTest(
            final LastIndexTaskQueue<Integer> full) {
        this.full = full;
    }

    @Test
    public void testLastTaskIdRetrieving()
            throws Exception {
        final Integer task = full.poll();
        final Integer index = full.getLastRetrievedTaskIndex();
        assertThat("[ " + full.getClass().getSimpleName() + " ]", index, is(1));
    }
}
