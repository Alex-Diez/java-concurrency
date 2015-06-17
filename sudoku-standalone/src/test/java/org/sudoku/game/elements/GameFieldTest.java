package org.sudoku.game.elements;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.sudoku.ResolveSudokuGameFieldTest.CONFIGURATION;
import static org.sudoku.ResolveSudokuGameFieldTest.ELEMENTS;

public class GameFieldTest {

	private GameField gameField;

	@Before
	public void setUp()
			throws Exception {
		gameField = new GameField.Builder(CONFIGURATION, ELEMENTS).build();
	}

	@Test
	public void gameFieldRepresentationTest() {
		String printableGameField = " --- --- --- --- --- --- --- --- --- \n" +
				"| 8 | 4 |   |   | 6 | 8 |   |   | 9 |\n" +
				" --- --- --- --- --- --- --- --- --- \n" +
				"|   | 5 |   | 9 |   |   | 2 | 1 |   |\n" +
				" --- --- --- --- --- --- --- --- --- \n" +
				"|   |   | 2 |   |   | 3 | 7 |   |   |\n" +
				" --- --- --- --- --- --- --- --- --- \n" +
				"|   | 5 |   |   | 1 |   | 1 |   | 7 |\n" +
				" --- --- --- --- --- --- --- --- --- \n" +
				"|   |   | 9 | 5 |   |   | 4 |   |   |\n" +
				" --- --- --- --- --- --- --- --- --- \n" +
				"| 8 |   | 6 |   | 6 |   |   | 8 |   |\n" +
				" --- --- --- --- --- --- --- --- --- \n" +
				"|   |   | 2 | 5 |   |   | 3 |   |   |\n" +
				" --- --- --- --- --- --- --- --- --- \n" +
				"|   | 3 | 6 |   |   | 4 |   | 8 |   |\n" +
				" --- --- --- --- --- --- --- --- --- \n" +
				"| 4 |   |   | 7 | 1 |   |   | 2 | 9 |\n" +
				" --- --- --- --- --- --- --- --- --- \n";
		assertThat(gameField.toString(), is(printableGameField));
	}

	@Test
	public void testIsGameFieldFilled()
			throws Exception {
		assertThat(gameField.isFilled(), is(false));
	}
}
