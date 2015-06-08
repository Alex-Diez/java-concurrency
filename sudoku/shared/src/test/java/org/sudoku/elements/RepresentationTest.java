package org.sudoku.elements;

import org.junit.Before;
import org.junit.Test;

import org.sudoku.conf.GameFieldConfiguration;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.sudoku.TestsConstants.ELEMENTS;

public class RepresentationTest {

	private GameFieldConfiguration configuration;

	@Before
	public void startUp() {
		configuration = new GameFieldConfiguration.Builder(9).build();
	}

	@Test
	public void elementRepresentationTest() {
		Element e = new Element.Builder(configuration, 8).build();
		assertThat(e.toString(), is(" 8 "));
	}

	@Test
	public void emptyElementRepresentationTest() {
		Element e = Element.EMPTY_ELEMENT;
		assertThat(e.toString(), is("   "));
	}

	@Test
	public void squareRepresentationTest() {
		Square square = new Square.Builder(configuration, ELEMENTS, 0, 0).build();
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

	@Test
	public void gameFieldRepresentationTest() {
		GameField gameField = new GameField.Builder(configuration, ELEMENTS).build();
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
