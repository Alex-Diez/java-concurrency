package org.sudoku.game;

import org.sudoku.game.elements.Element;
import org.sudoku.game.elements.GameField;
import org.sudoku.game.elements.SubstitutableBlock;
import org.sudoku.game.strategies.ResolveSubstitution;
import org.sudoku.game.strategies.SearchPossibleSubstitution;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {

	private static final Logger LOG = LoggerFactory.getLogger(Main.class);

	public static void main(String[] args)
			throws Exception {
		GameField gameField = new GameField.Builder(ELEMENTS).build();
		LOG.info("Game field at start \n{}", gameField);
		ExecutorService executorService = Executors.newFixedThreadPool(1);
		int iteration = 1;
		while (!gameField.isFilled()) {
			int number = (iteration - 1) % 9;
			int i = number / GameField.NUMBER_OF_SUBSTITUTABLE_BLOCKS;
			int j = number % GameField.NUMBER_OF_SUBSTITUTABLE_BLOCKS;
			SubstitutableBlock block = gameField.get(i, j);
			Callable<Element> searchPossibleSubstitution = new SearchPossibleSubstitution(block);
			Element possibleSubstitution = executorService.submit(searchPossibleSubstitution).get();
			if (Element.EMPTY_ELEMENT.equals(possibleSubstitution)) {
				Runnable substitutionResolver = new ResolveSubstitution(block, possibleSubstitution);
				executorService.submit(substitutionResolver).get();
			}
			LOG.info("Game field after {} iteration \n{}", iteration, gameField);
			iteration++;
		}
	}

	static final Element[][] ELEMENTS;

	static {
		ELEMENTS = new Element[][]{
				{
						new Element.Builder(8).build(),
						new Element.Builder(4).build(),
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						new Element.Builder(5).build(),
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						new Element.Builder(2).build()
				},
				{
						Element.EMPTY_ELEMENT,
						new Element.Builder(5).build(),
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						new Element.Builder(9).build(),
						Element.EMPTY_ELEMENT,
						new Element.Builder(3).build(),
						new Element.Builder(6).build()
				},
				{
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						new Element.Builder(2).build(),
						new Element.Builder(8).build(),
						Element.EMPTY_ELEMENT,
						new Element.Builder(6).build(),
						new Element.Builder(4).build(),
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT
				},
				{
						Element.EMPTY_ELEMENT,
						new Element.Builder(6).build(),
						new Element.Builder(8).build(),
						Element.EMPTY_ELEMENT,
						new Element.Builder(1).build(),
						Element.EMPTY_ELEMENT,
						new Element.Builder(5).build(),
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT
				},
				{
						new Element.Builder(9).build(),
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						new Element.Builder(5).build(),
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						new Element.Builder(4).build()
				},
				{
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						new Element.Builder(3).build(),
						Element.EMPTY_ELEMENT,
						new Element.Builder(6).build(),
						Element.EMPTY_ELEMENT,
						new Element.Builder(7).build(),
						new Element.Builder(1).build(),
						Element.EMPTY_ELEMENT,
				},
				{
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						new Element.Builder(9).build(),
						new Element.Builder(1).build(),
						Element.EMPTY_ELEMENT,
						new Element.Builder(7).build(),
						new Element.Builder(3).build(),
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT
				},
				{
						new Element.Builder(2).build(),
						new Element.Builder(1).build(),
						Element.EMPTY_ELEMENT,
						new Element.Builder(4).build(),
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						new Element.Builder(8).build(),
						Element.EMPTY_ELEMENT
				},
				{
						new Element.Builder(7).build(),
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						new Element.Builder(8).build(),
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						new Element.Builder(2).build(),
						new Element.Builder(9).build()
				}
		};
	}

}
