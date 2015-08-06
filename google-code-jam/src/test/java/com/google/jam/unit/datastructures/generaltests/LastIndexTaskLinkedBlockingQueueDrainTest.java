package com.google.jam.unit.datastructures.generaltests;

import java.util.Arrays;
import java.util.List;

import com.google.jam.datastructures.LastIndexTaskLinkedBlockingQueue;
import com.google.jam.datastructures.LastIndexTaskQueue;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LastIndexTaskLinkedBlockingQueueDrainTest {

	private static final List<Integer> DATA = Arrays.asList(1, 2, 3);

	private LastIndexTaskQueue<Integer> empty;
	private LastIndexTaskQueue<Integer> full;

	@Before
	public void setUp()
			throws Exception {
		full = new LastIndexTaskLinkedBlockingQueue<>(DATA);
		empty = new LastIndexTaskLinkedBlockingQueue<>();
	}

	@Test
	public void testDrainIntoAnotherQueue()
			throws Exception {
		assertThat(full.drainTo(empty, DATA.size()), is(DATA.size()));
		assertThat(full.isEmpty(), is(true));
		assertThat(empty.size(), is(DATA.size()));
	}

	@Test(expected = NullPointerException.class)
	public void testDrainToNull_shouldThrowException()
			throws Exception {
		full.drainTo(null, DATA.size());
	}

	@Test
	public void testDrainPartOfQueueToAnotherQueue()
			throws Exception {
		assertThat(full.drainTo(empty, 2), is(2));
		assertThat(empty.size(), is(2));
	}
}
