package org.sudoku.game.elements;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.sudoku.ResolveSudokuGameFieldTest.CONFIGURATION;
import static org.sudoku.ResolveSudokuGameFieldTest.ELEMENTS;

public class ReadWriteSquareTest {

	private ReadWriteSquare readWriteSquare;

	@Before
	public void setUp() {
		readWriteSquare =  new Square.Builder(CONFIGURATION, ELEMENTS, 0, 0, new ReentrantReadWriteLock()).build();
	}

	@Test
	public void testReadLockOnSquare() throws Exception {
		readWriteSquare.lockForRead();
		readWriteSquare.unlockAfterRead();
	}

	@Test
	public void testReadElementFrom() throws Exception {
		final Element e = readWriteSquare.readFrom(0, 0);
		assertThat(e, is(ELEMENTS[0][0]));
	}

	@Test
	public void testWriteLockOnSquare() throws Exception {
		readWriteSquare.lockForWrite();
		readWriteSquare.unlockAfterWrite();
	}

	@Test
	public void testWriteElementTo() throws Exception {
		final Element e = new Element.Builder(9, 6).build();
		readWriteSquare.writeTo(0, 2, e);
		assertThat(readWriteSquare.readFrom(0, 2), is(not(ELEMENTS[0][2])));
		assertThat(readWriteSquare.readFrom(0, 2), is(e));
	}

	@Test
	public void testSquareContainsElement()
			throws Exception {
		assertThat(readWriteSquare.containsElement(ELEMENTS[0][0]), is(true));
	}
}
