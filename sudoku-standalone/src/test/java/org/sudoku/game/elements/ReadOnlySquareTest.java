package org.sudoku.game.elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.hamcrest.Matchers;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.nullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isIn;
import static org.junit.Assert.fail;
import static org.sudoku.ResolveSudokuGameFieldTest.CONFIGURATION;
import static org.sudoku.ResolveSudokuGameFieldTest.ELEMENTS;

public class ReadOnlySquareTest {

	private ReadOnlySquare readOnlySquare;

	@Before
	public void setUp() {
		readOnlySquare = new Square.Builder(CONFIGURATION, ELEMENTS, 0, 0, new ReentrantReadWriteLock()).build();
	}

	@Test
	public void testSquareFreeSquareLockOnReading()
			throws Exception {
		Thread t1 = new Thread() {
			public void run() {
				try {
					readOnlySquare.lockForRead();
					Thread.sleep(2000L);
				}
				catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					fail();
				}
				finally {
					readOnlySquare.unlockAfterRead();
				}
			}
		};
		Thread t2 = new Thread() {
			public void run() {
				try {
					Thread.sleep(1000L);
					assertThat(readOnlySquare.lockForRead(), is(true));
				}
				catch (InterruptedException e) {
					Thread.currentThread().interrupt();
					fail();
				}
				finally {
					readOnlySquare.unlockAfterRead();
				}
			}
		};
		t1.start();
		t2.start();
	}

	@Test
	public void testReadElementFrom() throws Exception {
		final Element e = readOnlySquare.readFrom(0, 0);
		assertThat(e, is(ELEMENTS[0][0]));
	}

	@Test
	public void testSquareContainsElement()
			throws Exception {
		assertThat(readOnlySquare.containsElement(ELEMENTS[0][0]), is(true));
	}

	@Test
	public void testSquareFilledPositions()
			throws Exception {
		final Collection<Integer> filledPosition = new ArrayList<>();
		final int numberOfElementsOnSquareSide = CONFIGURATION.getNumberOfElementsOnSquareSide();
		for (int i = 0; i < numberOfElementsOnSquareSide; i++) {
			for (int j = 0; j < numberOfElementsOnSquareSide; j++) {
				if (!Element.EMPTY_ELEMENT.equals(ELEMENTS[i][j])) {
					filledPosition.add(i * numberOfElementsOnSquareSide + j);
				}
			}
		}
		assertThat(readOnlySquare.filledPositions(), everyItem(isIn(filledPosition)));
	}

	@Test
	public void testElementPosition()
			throws Exception {
		final Element e = ELEMENTS[0][0];
		assertThat(readOnlySquare.getElementPosition(e), is(0));
	}

	@Test
	public void testFilledSquare() throws Exception {
		assertThat(readOnlySquare.isFilled(), is(false));
	}

	@Test
	public void testMoveLeft() throws Exception {
		final Square l = new Square.Builder(CONFIGURATION, ELEMENTS, 0, 2, new ReentrantReadWriteLock()).build();
		readOnlySquare.setLeft(l);
		assertThat(readOnlySquare.getLeft(), is(l));
	}

	@Test
	public void testPreviousLeft() throws Exception {
		final Square l = new Square.Builder(CONFIGURATION, ELEMENTS, 0, 2, new ReentrantReadWriteLock()).build();
		final ReadOnlySquare previousLeft = readOnlySquare.setLeft(l);
		assertThat(previousLeft, is(nullValue()));
	}

	@Test
	public void testMoveRight() throws Exception {
		final Square r = new Square.Builder(CONFIGURATION, ELEMENTS, 0, 1, new ReentrantReadWriteLock()).build();
		readOnlySquare.setRight(r);
		Assert.assertThat(readOnlySquare.getRight(), Matchers.is(r));
	}

	@Test
	public void testPreviousRight() throws Exception {
		final Square r = new Square.Builder(CONFIGURATION, ELEMENTS, 0, 1, new ReentrantReadWriteLock()).build();
		final ReadOnlySquare previousRight = readOnlySquare.setRight(r);
		assertThat(previousRight, is(nullValue()));
	}

	@Test
	public void testMoveUp() throws Exception {
		final Square u = new Square.Builder(CONFIGURATION, ELEMENTS, 2, 0, new ReentrantReadWriteLock()).build();
		readOnlySquare.setUp(u);
		Assert.assertThat(readOnlySquare.getUp(), Matchers.is(u));
	}

	@Test
	public void testPreviousUp() throws Exception {
		final Square u = new Square.Builder(CONFIGURATION, ELEMENTS, 2, 0, new ReentrantReadWriteLock()).build();
		final ReadOnlySquare previousUp = readOnlySquare.setUp(u);
		assertThat(previousUp, is(nullValue()));
	}

	@Test
	public void testMoveDown() throws Exception {
		final Square d = new Square.Builder(CONFIGURATION, ELEMENTS, 1, 0, new ReentrantReadWriteLock()).build();
		readOnlySquare.setDown(d);
		Assert.assertThat(readOnlySquare.getDown(), Matchers.is(d));
	}

	@Test
	public void testPreviousDown() throws Exception {
		final Square d = new Square.Builder(CONFIGURATION, ELEMENTS, 1, 0, new ReentrantReadWriteLock()).build();
		final ReadOnlySquare previousDown = readOnlySquare.setLeft(d);
		assertThat(previousDown, is(nullValue()));
	}
}
