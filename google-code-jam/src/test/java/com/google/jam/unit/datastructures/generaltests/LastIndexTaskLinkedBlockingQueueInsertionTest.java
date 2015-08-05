package com.google.jam.unit.datastructures.generaltests;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.BlockingQueue;

import com.google.jam.datastructures.LastIndexTaskLinkedBlockingQueue;

import org.junit.Before;
import org.junit.Test;

import static java.util.concurrent.TimeUnit.SECONDS;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;

public class LastIndexTaskLinkedBlockingQueueInsertionTest {

	private static final List<Integer> DATA = Arrays.asList(1, 2, 3);

	private BlockingQueue<Integer> empty;
	private BlockingQueue<Integer> full;

	@Before
	public void setUp()
			throws Exception {
		full = new LastIndexTaskLinkedBlockingQueue<>(DATA);
		empty = new LastIndexTaskLinkedBlockingQueue<>();
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

	@Test(expected = NullPointerException.class)
	public void testPutNull_shouldThrowException()
			throws Exception {
		empty.put(null);
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

	@Test
	public void testPutIntoFullQueue()
			throws Exception {
		full.put(5);
		assertThat(full.size(), is(DATA.size() + 1));
	}

	@Test
	public void testOfferIntoFullQueueWithTimeOut()
			throws Exception {
		assertThat(full.offer(1, 5L, SECONDS), is(true));
		assertThat(full.size(), is(DATA.size() + 1));
	}
}
