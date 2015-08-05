package com.google.jam.unit.datastructures.generaltests;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import com.google.jam.datastructures.LastIndexTaskLinkedBlockingQueue;
import com.google.jam.datastructures.LastIndexTaskQueue;

import org.junit.Before;
import org.junit.Test;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class LastIndexTaskLinkedBlockingQueueRetrieveTest {

	private static final List<Integer> DATA = Arrays.asList(1, 2, 3);

	private LastIndexTaskQueue<Integer, Integer> empty;
	private LastIndexTaskQueue<Integer, Integer> full;

	@Before
	public void setUp()
			throws Exception {
		full = new LastIndexTaskLinkedBlockingQueue<>(DATA);
		empty = new LastIndexTaskLinkedBlockingQueue<>();
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemove_shouldThrowException()
			throws Exception {
		empty.remove(1);
	}

	@Test(expected = NoSuchElementException.class)
	public void testRemoveFromEmptyQueue()
			throws Exception {
		empty.remove();
	}

	@Test
	public void testPollFromEmptyQueue()
			throws Exception {
		assertThat(empty.poll(), is(nullValue()));
	}

	@Test
	public void testTakeOnFullQueue()
			throws Exception {
		final Integer takenElement = full.take();
		final Integer expected = DATA.get(0);
		assertThat(takenElement, is(expected));
	}

	@Test
	public void testRemoveFromFullQueue()
			throws Exception {
		final Integer removedElement = full.remove();
		final Integer expected = DATA.get(0);
		assertThat(removedElement, is(expected));
	}

	@Test
	public void testPollFromFullQueue()
			throws Exception {
		final Integer polledElement = full.poll();
		final Integer expected = DATA.get(0);
		assertThat(polledElement, is(expected));
		assertThat(full.size(), is(DATA.size() - 1));
	}

	@Test
	public void testPollElementWithTimeOut()
			throws Exception {
		assertThat(full.poll(5L, SECONDS), is(DATA.get(0)));
		assertThat(full.size(), is(DATA.size() - 1));
	}

	@Test
	public void testPeekElementFromFullQueue()
			throws Exception {
		assertThat(full.peek(), is(DATA.get(0)));
		assertThat(full.size(), is(DATA.size()));
		assertThat(full.peek(), is(DATA.get(0)));
	}
}
