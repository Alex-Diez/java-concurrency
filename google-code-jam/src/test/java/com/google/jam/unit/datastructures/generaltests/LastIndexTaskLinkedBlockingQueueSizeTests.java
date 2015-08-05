package com.google.jam.unit.datastructures.generaltests;

import java.util.Arrays;
import java.util.List;

import com.google.jam.datastructures.LastIndexTaskLinkedBlockingQueue;
import com.google.jam.datastructures.LastIndexTaskQueue;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LastIndexTaskLinkedBlockingQueueSizeTests {

	private static final List<Integer> DATA = Arrays.asList(1, 2, 3);

	private LastIndexTaskQueue<Integer, Integer> empty;
	private LastIndexTaskQueue<Integer, Integer> full;

	@Before
	public void setUp()
			throws Exception {
		full = new LastIndexTaskLinkedBlockingQueue<>(DATA);
		empty = new LastIndexTaskLinkedBlockingQueue<>();
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

	@Test
	public void testRemainingCapacity()
			throws Exception {
		assertThat(empty.remainingCapacity(), is(Integer.MAX_VALUE));
	}
}
