package org.sudoku.game.elements;

import java.util.ArrayList;
import java.util.Collection;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.everyItem;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.isIn;
import static org.junit.Assert.fail;
import static org.sudoku.ResolveSudokuGameFieldTest.CONFIGURATION;
import static org.sudoku.ResolveSudokuGameFieldTest.ELEMENTS;

public class ReadOnlySquareTest {

	private ReadOnlySquare readOnlySquare;

	@Before
	public void setUp() {
		readOnlySquare =  new Square.Builder(CONFIGURATION, ELEMENTS, 0, 0, new ReentrantReadWriteLock()).build();
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
				catch(InterruptedException e) {
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
				catch(InterruptedException e) {
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
		for(int i = 0; i < numberOfElementsOnSquareSide; i++) {
			for(int j = 0; j < numberOfElementsOnSquareSide; j++) {
				if(!Element.EMPTY_ELEMENT.equals(ELEMENTS[i][j])) {
					filledPosition.add(i* numberOfElementsOnSquareSide + j);
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
}
