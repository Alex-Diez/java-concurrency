package org.sudoku.game.strategies;

import org.sudoku.game.elements.Element;
import org.sudoku.game.elements.SubstitutableBlock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

public class ResolveSubstitution
		implements Runnable {

	private static final Set<Integer> POSSIBLE_POSITIONS = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));

	private final SubstitutableBlock block;
	private final Element elementToSubstitute;

	public ResolveSubstitution(SubstitutableBlock block, Element elementToSubstitute) {
		this.block = block;
		this.elementToSubstitute = elementToSubstitute;
	}

	@Override
	public void run() {
		Collection<Integer> substitutionPosition = new ArrayList<>(POSSIBLE_POSITIONS);
		block.lockForReading();
		try {
			Collection<Integer> filledPositions = new ArrayList<>(block.filledPositions());
			filledPositions.addAll(block.closedPositionsByColumns(elementToSubstitute));
			filledPositions.addAll(block.closedPositionsByRows(elementToSubstitute));
			substitutionPosition.removeAll(filledPositions);
		}
		finally {
			block.unlockForReading();
		}
		if (substitutionPosition.size() == 1) {
			block.lockForWriting();
			try {
				block.putIn(elementToSubstitute, substitutionPosition.iterator().next());
			}
			finally {
				block.unlockForWriting();
			}
		}
	}
}
