package org.sudoku.game.elements;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.sudoku.ResolveSudokuGameFieldTest.CONFIGURATION;
import static org.sudoku.ResolveSudokuGameFieldTest.ELEMENTS;

public class ReadOnlySquareTest {

	private ReadOnlySquare readOnlySquare;

	@Before
	public void setUp() {
		readOnlySquare =  new Square.Builder(CONFIGURATION, ELEMENTS, 0, 0).build();
	}

	@Test
	public void testReadLockOnSquare() throws Exception {
		readOnlySquare.lockForRead();
	}

	@Test
	public void testReadElementFrom() throws Exception {
		final Element e = readOnlySquare.readFrom(0, 0);
		assertThat(e, is(ELEMENTS[0][0]));
	}
}
