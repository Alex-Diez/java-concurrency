package org.sudoku.game.elements;

import org.junit.Before;
import org.junit.Test;
import org.sudoku.game.conf.GameFieldConfiguration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.sudoku.ResolveSudokuGameFieldTest.CONFIGURATION;
import static org.sudoku.ResolveSudokuGameFieldTest.ELEMENTS;

public class GameFieldTest {

	@Test
	public void gameFieldRepresentationTest() {
		GameField gameField = new GameField.Builder(CONFIGURATION, ELEMENTS).build();
		String printableGameField =
				" --- --- --- --- --- --- --- --- --- \n" +
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
}
