package org.sudoku.slave.blocks;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.EnumMap;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Set;

import org.sudoku.conf.SquareLocation;
import org.sudoku.elements.Element;
import org.sudoku.elements.Square;

public class SubstitutableBlock {

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

	private final Map<SquareLocation, Square> container;

	public SubstitutableBlock() {
		container = new EnumMap<>(SquareLocation.class);
	}

	public void mergeInto(Square newSquare, SquareLocation location) {
		final Square oldSquare = container.get(location);
		if(oldSquare != null
				&& !oldSquare.onSamePositionWith(newSquare)) {
			throw new SquaresNotOnTheSamePositionsException(
					String.format(
							"Old square %s and new square %s has different column/row indexes",
							oldSquare,
							newSquare
					)
			);
		}
		container.put(location, newSquare);
	}

	public boolean isFilled() {
		return getCenterSquare().size() == 9;
	}

	public Integer positionToSubstitution(Element element) {
		boolean canBeSearchable = !getCenterSquare().hasElement(element);
		if(canBeSearchable) {
			Iterator<Square> iterator = new SquareElementIterator(
					container.get(SquareLocation.LEFT),
					container.get(SquareLocation.RIGHT),
					container.get(SquareLocation.UP),
					container.get(SquareLocation.DOWN)
			);
			Collection<Integer> substitutionPosition = new ArrayList<>(POSSIBLE_POSITIONS);
			while(iterator.hasNext()) {
				Square square = iterator.next();
				Collection<Integer> filledPositions = new ArrayList<>(getCenterSquare().filledPositions());
				filledPositions.addAll(closedPositionsByRows(element));
				filledPositions.addAll(closedPositionsByColumns(element));
				substitutionPosition.removeAll(filledPositions);
			}
			if(substitutionPosition.size() == 1) {
				return substitutionPosition.iterator().next();
			}
		}
		return -1;
	}

	private Collection<Integer> closedPositionsByRows(Element element) {
		Collection<Integer> positions = new ArrayList<>();
		getSquaresOnHorizontalPositions().forEach(
				(square) -> {
					if(square.hasElement(element)) {
						Integer position = square.getElementPosition(element);
						Integer rowPosition = position / square.elementsInRow();
						positions.addAll(ROW_CLOSED_POSITIONS.get(rowPosition));
					}
				}
		);
		return positions;
	}

	private Collection<Integer> closedPositionsByColumns(Element element) {
		Collection<Integer> positions = new ArrayList<>();
		getSquaresOnVerticalPositions().forEach(
				(square) -> {
					if(square.hasElement(element)) {
						Integer position = square.getElementPosition(element);
						Integer columnPosition = position % square.elementsInColumn();
						positions.addAll(COLUMN_CLOSED_POSITIONS.get(columnPosition));
					}
				}
		);
		return positions;
	}

	public void putIn(Element element, Integer position) {
		getCenterSquare().putElement(element, position);
	}

	@Override
	public String toString() {
		return getCenterSquare().toString();
	}

	private Square getCenterSquare() {
		return container.get(SquareLocation.CENTER);
	}

	private Collection<Square> getSquaresOnHorizontalPositions() {
		final Collection<Square> result = new HashSet<>(2, 1.0F);
		result.add(container.get(SquareLocation.LEFT));
		result.add(container.get(SquareLocation.RIGHT));
		return result;
	}

	private Collection<Square> getSquaresOnVerticalPositions() {
		final Collection<Square> result = new HashSet<>(2, 1.0F);
		result.add(container.get(SquareLocation.UP));
		result.add(container.get(SquareLocation.DOWN));
		return result;
	}

	private static class SquareElementIterator
			implements Iterator<Square> {

		private final Square[] squares;
		private int current;

		SquareElementIterator(Square... squares) {
			this.squares = squares;
			this.current = 0;
		}

		@Override
		public boolean hasNext() {
			return current < squares.length;
		}

		@Override
		public Square next() {
			if(hasNext()) {
				final Square currentSquare = squares[current];
				current++;
				return currentSquare;
			}
			throw new NoSuchElementException();
		}
	}
}
