package com.google.jam.unit.datastructures.generaltests;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.google.jam.datastructures.LastIndexTaskLinkedBlockingQueue;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LastIndexTaskLinkedBlockingQueueContainsTest {

	private static final List<Integer> DATA = Arrays.asList(1, 2, 3);

	private BlockingQueue<Integer> empty;
	private BlockingQueue<Integer> full;

	@Before
	public void setUp()
			throws Exception {
		full = new LastIndexTaskLinkedBlockingQueue<>(DATA);
		empty = new LastIndexTaskLinkedBlockingQueue<>();
	}

	@Test
	public void testContainsOnEmptyQueue()
			throws Exception {
		assertThat(empty.contains(1), is(false));
	}

	@Test
	public void testContainsOnFullQueue()
			throws Exception {
		final Integer expected = DATA.get(0);
		assertThat(full.contains(expected), is(true));
	}
}
