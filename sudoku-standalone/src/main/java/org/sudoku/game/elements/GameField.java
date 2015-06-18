package org.sudoku.game.elements;

import org.sudoku.game.conf.GameFieldConfiguration;
import org.sudoku.game.strategies.ResolverByBlock;
import org.sudoku.game.strategies.StrategiesFactory;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GameField
		implements StrategiesFactory {

	private static final String ROW_SEPARATOR = " --- --- --- --- --- --- --- --- --- ";
	private static final char COLUMN_SEPARATOR = '|';

	private final ReadWriteSquare[][] squares;
	private final int numberOfElementsOnSide;
	private final int numberOfElementsOnSquareSide;
	private final int numberOfSquaresOnSide;

	private GameField(
			final GameFieldConfiguration configuration,
			final ReadWriteSquare[][] squares) {
		this.squares = squares;
		this.numberOfElementsOnSide = configuration.getNumberOfElementsOnSide();
		this.numberOfElementsOnSquareSide = configuration.getNumberOfElementsOnSquareSide();
		this.numberOfSquaresOnSide = configuration.getNumberOfSquaresOnSide();
	}

	@Override
	public Runnable buildBlockResolverOnColumn(int columnIndex) {
		SubstitutableBlock[] data = new SubstitutableBlock[numberOfSquaresOnSide];
		for(int rowIndex = 0; rowIndex < numberOfSquaresOnSide; rowIndex++) {
			data[rowIndex] = buildSubstitutableBlock(columnIndex, rowIndex);
		}
		return new ResolverByBlock(data);
	}

	@Override
	public Runnable buildBlockResolverOnRow(int rowIndex) {
		SubstitutableBlock[] data = new SubstitutableBlock[numberOfSquaresOnSide];
		for(int columnIndex = 0; columnIndex < numberOfSquaresOnSide; columnIndex++) {
			data[columnIndex] = buildSubstitutableBlock(columnIndex, rowIndex);
		}
		return new ResolverByBlock(data);
	}

	private SubstitutableBlock buildSubstitutableBlock(final int columnIndex, final int rowIndex) {
		final Set<ReadOnlySquare> vertical = buildVerticalSetOfSquares(columnIndex, rowIndex);
		final ReadWriteSquare centerSquare = determinateCenterSquareForSubstitutableBlock(columnIndex, rowIndex);
		final Set<ReadOnlySquare> horizontal = buildHorizontalSetOfSquares(columnIndex, rowIndex);
		return new SubstitutableBlock(centerSquare, vertical, horizontal);
	}

	private Set<ReadOnlySquare> buildHorizontalSetOfSquares(int columnIndex, int rowIndex) {
		final ReadOnlySquare leftSquare = determinateLeftSquareForSubstitutableBlock(columnIndex, rowIndex);
		final ReadOnlySquare rightSquare = determinateRightSquareForSubstitutableBlock(columnIndex, rowIndex);
		final Set<ReadOnlySquare> readOnlySquares = new HashSet<>(2, 1.0f);
		readOnlySquares.add(leftSquare);
		readOnlySquares.add(rightSquare);
		return readOnlySquares;
	}

	private Set<ReadOnlySquare> buildVerticalSetOfSquares(int columnIndex, int rowIndex) {
		final ReadOnlySquare upSquare = determinateUpSquareForSubstitutableBlock(columnIndex, rowIndex);
		final ReadOnlySquare downSquare = determinateDownSquareForSubstitutableBlock(columnIndex, rowIndex);
		final Set<ReadOnlySquare> readOnlySquares = new HashSet<>(2, 1.0f);
		readOnlySquares.add(upSquare);
		readOnlySquares.add(downSquare);
		return readOnlySquares;
	}

	private ReadOnlySquare determinateRightSquareForSubstitutableBlock(int columnIndex, int rowIndex) {
		final int rightRowIndex = (columnIndex + numberOfSquaresOnSide) % numberOfSquaresOnSide;
		final int rightColumnIndex = (rowIndex + 1 + numberOfSquaresOnSide) % numberOfSquaresOnSide;
		return squares[rightRowIndex][rightColumnIndex];
	}

	private ReadOnlySquare determinateLeftSquareForSubstitutableBlock(final int columnIndex, final int rowIndex) {
		final int leftRowIndex = (columnIndex + numberOfSquaresOnSide) % numberOfSquaresOnSide;
		final int leftColumnIndex = (rowIndex - 1 + numberOfSquaresOnSide) % numberOfSquaresOnSide;
		return squares[leftRowIndex][leftColumnIndex];
	}

	private ReadWriteSquare determinateCenterSquareForSubstitutableBlock(int columnIndex, int rowIndex) {
		final int centerRowIndex = (columnIndex + numberOfSquaresOnSide) % numberOfSquaresOnSide;
		final int centerColumnIndex = (rowIndex + numberOfSquaresOnSide) % numberOfSquaresOnSide;
		return squares[centerRowIndex][centerColumnIndex];
	}

	private ReadOnlySquare determinateDownSquareForSubstitutableBlock(int columnIndex, int rowIndex) {
		final int downRowIndex = (columnIndex + 1 + numberOfSquaresOnSide) % numberOfSquaresOnSide;
		final int downColumnIndex = (rowIndex + numberOfSquaresOnSide) % numberOfSquaresOnSide;
		return squares[downRowIndex][downColumnIndex];
	}

	private ReadOnlySquare determinateUpSquareForSubstitutableBlock(int columnIndex, int rowIndex) {
		final int upRowIndex = (columnIndex - 1 + numberOfSquaresOnSide) % numberOfSquaresOnSide;
		final int upColumnIndex = (rowIndex + numberOfSquaresOnSide) % numberOfSquaresOnSide;
		return squares[upRowIndex][upColumnIndex];
	}

	public boolean isFilled() {
		for(ReadOnlySquare[] rows : squares) {
			for(ReadOnlySquare square : rows) {
				if(!square.isFilled()) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(ROW_SEPARATOR + "\n");
		for(int i = 0; i < numberOfElementsOnSide; i++) {
			sb.append(COLUMN_SEPARATOR);
			final int squareColumnIndex = calculateSquareColumnIndex(i);
			for(int j = 0; j < numberOfElementsOnSide; j++) {
				final int squareRowIndex = calculateSquareRowIndex(j);
				sb.append(
						squares[squareColumnIndex][squareRowIndex]
								.readFrom(
										calculateColumnOffset(i),
										calculateRowOffset(j)
								)
				).append(COLUMN_SEPARATOR);
			}
			sb.append("\n" + ROW_SEPARATOR + "\n");
		}
		return sb.toString();
	}

	private int calculateRowOffset(final int rowIndex) {
		return rowIndex % numberOfElementsOnSquareSide;
	}

	private int calculateColumnOffset(final int columnIndex) {
		return columnIndex % numberOfElementsOnSquareSide;
	}

	private int calculateSquareRowIndex(final int rowIndex) {
		return rowIndex / numberOfSquaresOnSide;
	}

	private int calculateSquareColumnIndex(final int columnIndex) {
		return columnIndex / numberOfSquaresOnSide;
	}

	public static class Builder {

		private final ReadWriteSquare[][] squares;
		private GameFieldConfiguration configuration;

		public Builder(final GameFieldConfiguration configuration, final Element[][] elements) {
			isInputElementsEnoughLength(configuration, elements);
			this.configuration = configuration;
			final int numberOfSquaresOnSide = configuration.getNumberOfSquaresOnSide();
			squares = new ReadWriteSquare[numberOfSquaresOnSide][numberOfSquaresOnSide];
			populateSquares(configuration, elements, numberOfSquaresOnSide);
		}

		public void populateSquares(
				final GameFieldConfiguration configuration,
				final Element[][] elements,
				final int numberOfSquaresOnSide) {
			for(int rowIndex = 0; rowIndex < numberOfSquaresOnSide; rowIndex++) {
				for(int columnIndex = 0; columnIndex < numberOfSquaresOnSide; columnIndex++) {
					squares[rowIndex][columnIndex] = new Square.Builder(
							configuration,
							elements,
							rowIndex,
							columnIndex,
							new ReentrantReadWriteLock()
					).build();
				}
			}
		}

		private void isInputElementsEnoughLength(
				final GameFieldConfiguration configuration,
				final Element[][] elements) {
			final int numberOfElementsOnSide = configuration.getNumberOfElementsOnSide();
			if(elements.length != numberOfElementsOnSide
					&& !isSubArraysHaveProperlyLengths(numberOfElementsOnSide, elements)) {
				throw new NotRightElementArrayLengthException(
						String.format(
								"Game field can contains only %s elements on side but is %s elements on side",
								configuration.getNumberOfElementsOnSquareSide(),
								elements.length
						)
				);
			}
		}

		private boolean isSubArraysHaveProperlyLengths(
				final int numberOfElementsOnSide,
				final Element[][] elements) {
			for(Element[] els : elements) {
				if(els.length != numberOfElementsOnSide) {
					return false;
				}
			}
			return true;
		}

		public GameField build() {
			return new GameField(configuration, squares);
		}
	}
}
