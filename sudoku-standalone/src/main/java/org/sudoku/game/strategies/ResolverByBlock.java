package org.sudoku.game.strategies;

import org.sudoku.game.elements.Element;
import org.sudoku.game.elements.ReadOnlySquare;
import org.sudoku.game.elements.ReadWriteSquare;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudoku.game.elements.SubstitutableBlock;

public class ResolverByBlock
		implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(ResolverByBlock.class);

	private static final Map<Integer, Collection<Integer>> COLUMN_CLOSED_POSITIONS
			= new HashMap<Integer, Collection<Integer>>() {
		{
			put(0, Arrays.asList(0, 3, 6));
			put(1, Arrays.asList(1, 4, 7));
			put(2, Arrays.asList(2, 5, 8));
		}
	};

	private static final Map<Integer, Collection<Integer>> ROW_CLOSED_POSITIONS
			= new HashMap<Integer, Collection<Integer>>() {
		{
			put(0, Arrays.asList(0, 1, 2));
			put(1, Arrays.asList(3, 4, 5));
			put(2, Arrays.asList(6, 7, 8));
		}
	};

	private static final Set<Integer> POSSIBLE_POSITIONS = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));

	public ResolverByBlock(final SubstitutableBlock[] substitutableBlocks) {

	}

	@Override
	public void run() {
//		LOG.info("Block before resolution\n{}", center);
//		Integer position = -1;
//		Element elementToSubstitute = Element.EMPTY_ELEMENT;
//		center.lockForRead();
//		try {
//			Element[] possibleElements = Element.getPossibleElements(numberOfElementsOnSide);
//			for (int i = 0; i < possibleElements.length && position == -1; i++) {
//				position = positionToSubstitution(possibleElements[i]);
//				if (position != -1) {
//					elementToSubstitute = possibleElements[i];
//				}
//			}
//		}
//		finally {
//			center.unlockAfterRead();
//		}
//		if(position != -1) {
//			center.lockForWrite();
//			try {
//				int rowIndex = position / numberOfElementsOnSquareSide;
//				int columnIndex = position % numberOfElementsOnSquareSide;
//				center.writeTo(rowIndex, columnIndex, elementToSubstitute);
//			}
//			finally{
//				center.unlockAfterWrite();
//			}
//		}
//		LOG.info("Block after resolution\n{}", center);
	}
/*
	private Integer positionToSubstitution(Element element) {
		boolean canBeSearchable = !center.containsElement(element);
		if(canBeSearchable) {
			Collection<Integer> substitutionPosition = new ArrayList<>(POSSIBLE_POSITIONS);
			Collection<Integer> filledPositions = new ArrayList<>(center.filledPositions());
			filledPositions.addAll(closedPositionsByRows(element));
			filledPositions.addAll(closedPositionsByColumns(element));
			substitutionPosition.removeAll(filledPositions);
			if(substitutionPosition.size() == 1) {
				return substitutionPosition.iterator().next();
			}
		}
		return -1;
	}

	private Collection<Integer> closedPositionsByColumns(Element element) {
		Collection<Integer> positions = new ArrayList<>();
		horizontal.forEach(
				(square) -> {
					if (square.containsElement(element)) {
						Integer position = square.getElementPosition(element);
						Integer rowColPosition = position % numberOfElementsOnSquareSide;
						positions.addAll(COLUMN_CLOSED_POSITIONS.get(rowColPosition));
					}
				}
		);
		return positions;
	}

	private Collection<Integer> closedPositionsByRows(Element element) {
		Collection<Integer> positions = new ArrayList<>();
		vertical.forEach(
				(square) -> {
					if (square.containsElement(element)) {
						Integer position = square.getElementPosition(element);
						Integer rowColPosition = position / numberOfElementsOnSquareSide;
						positions.addAll(ROW_CLOSED_POSITIONS.get(rowColPosition));
					}
				}
		);
		return positions;
	}*/
}
