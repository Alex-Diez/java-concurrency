package org.sudoku.game.elements;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.sudoku.ResolveSudokuGameFieldTest.CONFIGURATION;
import static org.sudoku.ResolveSudokuGameFieldTest.ELEMENTS;

public class SquareTest {

	private Square square;

	@Before
	public void setUp()
			throws Exception {
		square = new Square.Builder(CONFIGURATION, ELEMENTS, 0, 0).build();
	}

	@Test
	public void squareRepresentationTest() {
		String printableSquare = " --- --- --- \n" +
				"| 8 | 4 |   |\n" +
				" --- --- --- \n" +
				"|   | 5 |   |\n" +
				" --- --- --- \n" +
				"|   |   | 2 |\n" +
				" --- --- --- \n";
		assertThat(square.toString(), is(printableSquare));
	}

	@Test
	public void testHasElement()
			throws Exception {
		final Element e = new Element.Builder(CONFIGURATION, 6).build();
		square.putElement(e, 2);
		assertThat(square.hasElement(e), is(true));
	}

	@Test
	public void testInsertedElementPosition()
			throws Exception {
		final Element e = new Element.Builder(CONFIGURATION, 6).build();
		final int position = 2;
		square.putElement(e, position);
		assertThat(square.getElementPosition(e), is(position));
	}
}
