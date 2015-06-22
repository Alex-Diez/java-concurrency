package org.sudoku;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.sudoku.game.conf.GameFieldConfiguration;
import org.sudoku.game.elements.Element;
import org.sudoku.game.elements.GameField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.Test;

import static org.sudoku.game.elements.Position.STUB;

public class ResolveSudokuGameFieldTest {

	private static final Logger LOG = LoggerFactory.getLogger(ResolveSudokuGameFieldTest.class);

	@Test
	public void main()
			throws Exception {
		GameField gameField = new GameField.Builder(CONFIGURATION, ELEMENTS).build();
		LOG.info("Game field at start \n{}", gameField);
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		int iteration = 1;
		final int numberOfSquaresOnSide = CONFIGURATION.getNumberOfSquaresOnSide();
		while (!gameField.isFilled()) {
			final int number = (iteration - 1) % 9;
			final int rowIndex = number / numberOfSquaresOnSide;
			final int columnIndex = number % numberOfSquaresOnSide;
			Runnable resolver = gameField.buildBlockResolver(rowIndex, columnIndex);
			executorService.submit(resolver).get();
			LOG.info("Game field after {} iteration \n{}", iteration, gameField);
			iteration++;
		}
	}

	public static final GameFieldConfiguration CONFIGURATION = new GameFieldConfiguration.Builder(81).build();
	public static final int NUMBER_OF_ELEMENTS_ON_SIDE = CONFIGURATION.getNumberOfElementsOnSide();

	public static final Element[][] ELEMENTS = new Element[][] {
			{
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 8, STUB).build(),
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 4, STUB).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 5, STUB).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 2, STUB).build()
			},
			{
					Element.EMPTY_ELEMENT,
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 5, STUB).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 9, STUB).build(),
					Element.EMPTY_ELEMENT,
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 3, STUB).build(),
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 6, STUB).build()
			},
			{
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 2, STUB).build(),
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 8, STUB).build(),
					Element.EMPTY_ELEMENT,
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 6, STUB).build(),
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 4, STUB).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT
			},
			{
					Element.EMPTY_ELEMENT,
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 6, STUB).build(),
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 8, STUB).build(),
					Element.EMPTY_ELEMENT,
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 1, STUB).build(),
					Element.EMPTY_ELEMENT,
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 5, STUB).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT
			},
			{
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 9, STUB).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 5, STUB).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 4, STUB).build()
			},
			{
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 3, STUB).build(),
					Element.EMPTY_ELEMENT,
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 6, STUB).build(),
					Element.EMPTY_ELEMENT,
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 7, STUB).build(),
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 1, STUB).build(),
					Element.EMPTY_ELEMENT,
			},
			{
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 9, STUB).build(),
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 1, STUB).build(),
					Element.EMPTY_ELEMENT,
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 7, STUB).build(),
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 3, STUB).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT
			},
			{
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 2, STUB).build(),
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 1, STUB).build(),
					Element.EMPTY_ELEMENT,
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 4, STUB).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 8, STUB).build(),
					Element.EMPTY_ELEMENT
			},
			{
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 7, STUB).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 8, STUB).build(),
					Element.EMPTY_ELEMENT,
					Element.EMPTY_ELEMENT,
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 2, STUB).build(),
					new Element.Builder(NUMBER_OF_ELEMENTS_ON_SIDE, 9, STUB).build()
			}
	};
}
