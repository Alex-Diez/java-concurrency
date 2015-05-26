package org.sudoku.game.strategies;

import org.sudoku.game.elements.Element;
import org.sudoku.game.elements.SubstitutableBlock;

import java.util.concurrent.Callable;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SearchPossibleSubstitution
		implements Callable<Element> {

	private static final Logger LOG = LoggerFactory.getLogger(SearchPossibleSubstitution.class);

	private final SubstitutableBlock block;

	public SearchPossibleSubstitution(SubstitutableBlock block) {
		this.block = block;
	}

	@Override
	public Element call() throws Exception {
		Element result = Element.EMPTY_ELEMENT;
		try {
			block.lockForReading();
			if (block.filledEnough()) {
				result = block.elementToSubstitution();
				LOG.info("Result element is {} on block\n{}", result, block);
			}
		}
		finally {
			block.unlockForReading();
		}
		return result;
	}
}