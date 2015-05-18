package org.sudoku.game.strategies;

import org.sudoku.game.elements.Element;
import org.sudoku.game.elements.SubstitutableBlock;

import java.util.concurrent.Callable;

public class SearchPossibleSubstitution
		implements Callable<Element> {

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
			}
		}
		finally {
			block.unlockForReading();
		}
		return result;
	}
}