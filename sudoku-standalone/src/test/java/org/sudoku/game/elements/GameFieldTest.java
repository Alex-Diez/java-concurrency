package org.sudoku.game.elements;

import org.junit.Before;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.CoreMatchers.nullValue;
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
				"| 8 | 4 |   |   | 5 |   |   |   | 2 |\n" +
				" --- --- --- --- --- --- --- --- --- \n" +
				"|   | 5 |   |   |   | 9 |   | 3 | 6 |\n" +
				" --- --- --- --- --- --- --- --- --- \n" +
				"|   |   | 2 | 8 |   | 6 | 4 |   |   |\n" +
				" --- --- --- --- --- --- --- --- --- \n" +
				"|   | 6 | 8 |   | 1 |   | 5 |   |   |\n" +
				" --- --- --- --- --- --- --- --- --- \n" +
				"| 9 |   |   | 5 |   |   |   |   | 4 |\n" +
				" --- --- --- --- --- --- --- --- --- \n" +
				"|   |   | 3 |   | 6 |   | 7 | 1 |   |\n" +
				" --- --- --- --- --- --- --- --- --- \n" +
				"|   |   | 9 | 1 |   | 7 | 3 |   |   |\n" +
				" --- --- --- --- --- --- --- --- --- \n" +
				"| 2 | 1 |   | 4 |   |   |   | 8 |   |\n" +
				" --- --- --- --- --- --- --- --- --- \n" +
				"| 7 |   |   |   | 8 |   |   | 2 | 9 |\n" +
				" --- --- --- --- --- --- --- --- --- \n";
		assertThat(gameField.toString(), is(printableGameField));
	}

	@Test
	public void testIsGameFieldFilled()
			throws Exception {
		assertThat(gameField.isFilled(), is(false));
	}

	@Test
	public void testBuildBlockResolver()
			throws Exception {
		assertThat(gameField.buildBlockResolver(0, 0), is(not(nullValue())));
	}
}
