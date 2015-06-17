package org.sudoku.game.elements;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.sudoku.ResolveSudokuGameFieldTest.CONFIGURATION;
import static org.sudoku.ResolveSudokuGameFieldTest.ELEMENTS;

public class SquareTest {

	@Test
	public void squareRepresentationTest() {
		Square square = new Square.Builder(CONFIGURATION, ELEMENTS, 0, 0).build();
		String printableSquare =
				" --- --- --- \n" +
						"| 8 | 4 |   |\n" +
						" --- --- --- \n" +
						"|   | 5 |   |\n" +
						" --- --- --- \n" +
						"|   |   | 2 |\n" +
						" --- --- --- \n";
		assertThat(square.toString(), is(printableSquare));
	}
}
