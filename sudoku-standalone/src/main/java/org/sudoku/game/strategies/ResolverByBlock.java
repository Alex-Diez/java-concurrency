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

public class ResolverByBlock
		implements Runnable {

	private static final Logger LOG = LoggerFactory.getLogger(ResolverByBlock.class);

	private static final Map<Integer, Collection<Integer>> COLUMN_CLOSED_POSITIONS
			= new HashMap<Integer, Collection<Integer>>() {
		{
			put(0, Arrays.asList(0, 1, 2));
			put(1, Arrays.asList(3, 4, 5));
			put(2, Arrays.asList(6, 7, 8));
		}
	};

	private static final Map<Integer, Collection<Integer>> ROW_CLOSED_POSITIONS
			= new HashMap<Integer, Collection<Integer>>() {
		{
			put(0, Arrays.asList(0, 3, 6));
			put(1, Arrays.asList(1, 4, 7));
			put(2, Arrays.asList(2, 5, 8));
		}
	};

	private static final Set<Integer> POSSIBLE_POSITIONS = new HashSet<>(Arrays.asList(0, 1, 2, 3, 4, 5, 6, 7, 8));
	private final ReadWriteSquare square;
	private final int numberOfElementsOnSide;
	private final int numberOfElementsOnSquareSide;

	public ResolverByBlock(
			final ReadWriteSquare square,
			final int numberOfElementsOnSide,
			final int numberOfElementsOnSquareSide) {
		this.square = square;
		this.numberOfElementsOnSide = numberOfElementsOnSide;
		this.numberOfElementsOnSquareSide = numberOfElementsOnSquareSide;
	}

	@Override
	public void run() {
		LOG.info("Block before resolution\n{}", square);
		Integer position = -1;
		Element elementToSubstitute = Element.EMPTY_ELEMENT;
		lockForRead();
		try {
			Element[] possibleElements = Element.getPossibleElements(numberOfElementsOnSide);
			for(int i = 0; i < possibleElements.length && position == -1; i++) {
				position = positionToSubstitution(possibleElements[i]);
				if(position != -1) {
					elementToSubstitute = possibleElements[i];
				}
			}
		}
		finally {
			unlockAfterRead();
		}
		if(position != -1) {
			lockForWrite();
			try {
				int rowIndex = position / numberOfElementsOnSquareSide;
				int columnIndex = position % numberOfElementsOnSquareSide;
				square.writeTo(rowIndex, columnIndex, elementToSubstitute);
			}
			finally {
				unlockAfterWrite();
			}
		}
		LOG.info("Block after resolution\n{}", square);
	}

	private boolean lockForWrite() {
		boolean result = square.lockForWrite();
		result &= lockVerticalSquares();
		result &= lockHorizontalSquares();
		if(!result) {
			square.unlockAfterWrite();
			unlockVerticalSquares();
			unlockHorizontalSquares();
		}
		return result;
	}

	private void unlockAfterWrite() {
		square.unlockAfterWrite();
		unlockVerticalSquares();
		unlockHorizontalSquares();
	}

	private void unlockAfterRead() {
		square.unlockAfterRead();
		unlockVerticalSquares();
		unlockHorizontalSquares();
	}

	private boolean lockForRead() {
		boolean result = square.lockForRead();
		result &= lockHorizontalSquares();
		result &= lockVerticalSquares();
		if(!result) {
			square.unlockAfterRead();
			unlockVerticalSquares();
			unlockHorizontalSquares();
		}
		return result;
	}

	private void unlockHorizontalSquares() {
		ReadOnlySquare horizontalCurrentSquare;
		horizontalCurrentSquare = square.getLower();
		while(horizontalCurrentSquare != square) {
			horizontalCurrentSquare.unlockAfterRead();
			horizontalCurrentSquare = horizontalCurrentSquare.getLower();
		}
	}

	private void unlockVerticalSquares() {
		ReadOnlySquare verticalCurrentSquare;
		verticalCurrentSquare = square.getLeft();
		while(verticalCurrentSquare != square) {
			verticalCurrentSquare.unlockAfterRead();
			verticalCurrentSquare = verticalCurrentSquare.getLeft();
		}
	}

	private boolean lockHorizontalSquares() {
		boolean result = true;
		ReadOnlySquare horizontalCurrentSquare = square.getLower();
		while(horizontalCurrentSquare != square) {
			result &= horizontalCurrentSquare.lockForRead();
			horizontalCurrentSquare = horizontalCurrentSquare.getLower();
		}
		return result;
	}

	private boolean lockVerticalSquares() {
		boolean result = true;
		ReadOnlySquare verticalCurrentSquare = square.getLeft();
		while(verticalCurrentSquare != square) {
			result &= verticalCurrentSquare.lockForRead();
			verticalCurrentSquare = verticalCurrentSquare.getLeft();
		}
		return result;
	}

	private Integer positionToSubstitution(Element element) {
		boolean canBeSearchable = !square.containsElement(element);
		if(canBeSearchable) {
			Collection<Integer> substitutionPosition = new ArrayList<>(POSSIBLE_POSITIONS);
			Collection<Integer> filledPositions = new ArrayList<>(square.filledPositions());
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
		ReadOnlySquare horizontalCurrentSquare = square.getLeft();
		while(horizontalCurrentSquare != square) {
			if(horizontalCurrentSquare.containsElement(element)) {
				Integer position = horizontalCurrentSquare.getElementPosition(element);
				Integer rowColPosition = position / numberOfElementsOnSquareSide;
				positions.addAll(COLUMN_CLOSED_POSITIONS.get(rowColPosition));
			}
			horizontalCurrentSquare = horizontalCurrentSquare.getLeft();
		}
		return positions;
	}

	private Collection<Integer> closedPositionsByRows(Element element) {
		Collection<Integer> positions = new ArrayList<>();
		ReadOnlySquare verticalCurrentSquare = square.getLower();
		while(verticalCurrentSquare != square) {
			if(verticalCurrentSquare.containsElement(element)) {
				Integer position = verticalCurrentSquare.getElementPosition(element);
				Integer rowColPosition = position % numberOfElementsOnSquareSide;
				positions.addAll(ROW_CLOSED_POSITIONS.get(rowColPosition));
			}
			verticalCurrentSquare = verticalCurrentSquare.getLower();
		}
		return positions;
	}
}
