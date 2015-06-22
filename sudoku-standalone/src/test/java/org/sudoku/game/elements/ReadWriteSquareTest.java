package org.sudoku.game.elements;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.junit.Before;
import org.junit.Test;
import org.sudoku.game.elements.Element.Builder;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.sudoku.ResolveSudokuGameFieldTest.CONFIGURATION;
import static org.sudoku.ResolveSudokuGameFieldTest.ELEMENTS;
import static org.sudoku.game.elements.Position.STUB;

public class ReadWriteSquareTest {

	private ReadWriteSquare readWriteSquare;

	@Before
	public void setUp() {
		readWriteSquare =  new Square.Builder(CONFIGURATION, ELEMENTS, 0, 0, new ReentrantReadWriteLock()).build();
	}

	@Test
	public void testSquareFreeSquareLockOnReading()
			throws Exception {
		Thread t1 = new Thread() {
			public void run() {
				try {
					readWriteSquare.lockForRead();
					Thread.sleep(2000L);
				}
				catch(InterruptedException e) {
					Thread.currentThread().interrupt();
					fail();
				}
				finally {
					readWriteSquare.unlockAfterRead();
				}
			}
		};
		Thread t2 = new Thread() {
			public void run() {
				try {
					Thread.sleep(1000L);
					assertThat(readWriteSquare.lockForRead(), is(true));
				}
				catch(InterruptedException e) {
					Thread.currentThread().interrupt();
					fail();
				}
				finally {
					readWriteSquare.unlockAfterRead();
				}
			}
		};
		t1.start();
		t2.start();
	}

	@Test
	public void testReadElementFrom() throws Exception {
		final Element e = readWriteSquare.readFrom(0, 0);
		assertThat(e, is(ELEMENTS[0][0]));
	}

	@Test
	public void testSquareExclusiveLockOnWriting()
			throws Exception {
		Thread t1 = new Thread() {
			public void run() {
				try {
					readWriteSquare.lockForWrite();
					Thread.sleep(2000L);
				}
				catch(InterruptedException e) {
					Thread.currentThread().interrupt();
					fail();
				}
				finally {
					readWriteSquare.unlockAfterWrite();
				}
			}
		};
		Thread t2 = new Thread() {
			public void run() {
				try {
					Thread.sleep(1000L);
					assertThat(readWriteSquare.lockForWrite(), is(false));
				}
				catch(InterruptedException e) {
					Thread.currentThread().interrupt();
					fail();
				}
				finally {
					readWriteSquare.unlockAfterWrite();
				}
			}
		};
		t1.start();
		t2.start();
	}

	@Test
	public void testWriteElementTo() throws Exception {
		final Element e = new Element.Builder(9, 6, new Position(0, 2)).build();
		readWriteSquare.writeTo(0, 2, e);
		assertThat(readWriteSquare.readFrom(0, 2), is(not(ELEMENTS[0][2])));
		assertThat(readWriteSquare.readFrom(0, 2), is(e));
	}

	@Test
	public void testSquareContainsElement()
			throws Exception {
		assertThat(readWriteSquare.containsElement(ELEMENTS[0][0]), is(true));
	}

	@Test
	public void testFilledSquare() throws Exception {
		assertThat(readWriteSquare.isFilled(), is(false));
	}
}
