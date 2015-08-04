package com.google.jam.unit.datastructures;

import java.util.Arrays;
import java.util.List;
import java.util.NoSuchElementException;

import com.google.jam.datastructures.LastIndexTaskLinkedBlockingQueue;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.nullValue;

public class LastIndexTaskLinkedBlockingQueueTest {

	private static final List<Integer> DATA = Arrays.asList(1, 2, 3);

	private LastIndexTaskLinkedBlockingQueue<Integer, Integer> empty;
	private LastIndexTaskLinkedBlockingQueue<Integer, Integer> full;

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

	@Test(expected = NullPointerException.class)
	public void testAddNull()
			throws Exception {
		empty.add(null);
	}

	@Test(expected = NullPointerException.class)
	public void testOfferNull()
			throws Exception {
		empty.offer(null);
	}

	@Test(expected = NullPointerException.class)
	public void testPutNull()
			throws Exception {
		empty.put(null);
	}

	@Test
	public void testRemainingCapacity()
			throws Exception {
		assertThat(empty.remainingCapacity(), is(Integer.MAX_VALUE));
	}

	@Test(expected = UnsupportedOperationException.class)
	public void testRemove()
			throws Exception {
		empty.remove(1);
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
	}
}
