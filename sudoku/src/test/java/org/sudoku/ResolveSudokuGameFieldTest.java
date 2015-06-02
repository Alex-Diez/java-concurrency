package org.sudoku;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.sudoku.conf.GameFieldConfiguration;
import org.sudoku.elements.GameField;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.junit.Before;
import org.junit.Test;

public class ResolveSudokuGameFieldTest {

	//todo temporary
	private static final Logger LOG = LoggerFactory.getLogger(ResolveSudokuGameFieldTest.class);

	private GameFieldConfiguration configuration;

	@Before
	public void startUp() {
		configuration = new GameFieldConfiguration.Builder(9).build();
	}

	@Test
	public void main()
			throws Exception {
		GameField gameField = new GameField.Builder(TestsConstants.CONFIGURATION, TestsConstants.ELEMENTS).build();
		LOG.info("Game field at start \n{}", gameField);
		ExecutorService executorService = Executors.newFixedThreadPool(3);
		int iteration = 1;
		while (!gameField.isFilled()) {
			int number = (iteration - 1) % 9;
			int i = number / configuration.getNumberOfSquaresInColumn();
			int j = number % configuration.getNumberOfSquaresInRow();
			Runnable resolver = gameField.build(i, j);
			executorService.submit(resolver).get();
			LOG.info("Game field after {} iteration \n{}", iteration, gameField);
			iteration++;
		}
	}
}
