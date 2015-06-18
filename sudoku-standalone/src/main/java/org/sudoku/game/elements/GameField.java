package org.sudoku.game.elements;

import org.sudoku.game.conf.GameFieldConfiguration;
import org.sudoku.game.strategies.ResolverByBlock;
import org.sudoku.game.strategies.StrategiesFactory;

import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GameField
		implements StrategiesFactory {

	public static final String ROW_SEPARATOR = " --- --- --- --- --- --- --- --- --- ";
	public static final char COLUMN_SEPARATOR = '|';

	private final Square[][] squares;
	private final int numberOfElementsOnSide;
	private final int numberOfElementsOnSquareSide;
	private final int numberOfSquaresOnSide;

	private GameField(
			final GameFieldConfiguration configuration,
			final Square[][] squares) {
		this.squares = squares;
		numberOfElementsOnSide = configuration.getNumberOfElementsOnSide();
		numberOfElementsOnSquareSide = configuration.getNumberOfElementsOnSquareSide();
		numberOfSquaresOnSide = configuration.getNumberOfSquaresOnSide();
	}

	@Override
	public Runnable buildBlockResolverOnColumn(int columnIndex) {
		return null;
	}

	@Override
	public Runnable buildBlockResolverOnRow(int rowIndex) {
		return null;
	}

	public Runnable build(final int columnIndex, final int rowIndex) {
		int upRowIndex = (columnIndex - 1 + numberOfSquaresOnSide) % numberOfSquaresOnSide;
		int upColumnIndex = (rowIndex + numberOfSquaresOnSide) % numberOfSquaresOnSide;
		int downRowIndex = (columnIndex + 1 + numberOfSquaresOnSide) % numberOfSquaresOnSide;
		int downColumnIndex = (rowIndex + numberOfSquaresOnSide) % numberOfSquaresOnSide;
		int centerRowIndex = (columnIndex + numberOfSquaresOnSide) % numberOfSquaresOnSide;
		int centerColumnIndex = (rowIndex + numberOfSquaresOnSide) % numberOfSquaresOnSide;
		int leftRowIndex = (columnIndex + numberOfSquaresOnSide) % numberOfSquaresOnSide;
		int leftColumnIndex = (rowIndex - 1 + numberOfSquaresOnSide) % numberOfSquaresOnSide;
		int rightRowIndex = (columnIndex + numberOfSquaresOnSide) % numberOfSquaresOnSide;
		int rightColumnIndex = (rowIndex + 1 + numberOfSquaresOnSide) % numberOfSquaresOnSide;
		return new ResolverByBlock(
				numberOfElementsOnSquareSide,
				numberOfElementsOnSide,
				squares[upColumnIndex][upRowIndex],
				squares[downColumnIndex][downRowIndex],
				squares[centerColumnIndex][centerRowIndex],
				squares[leftColumnIndex][leftRowIndex],
				squares[rightColumnIndex][rightRowIndex]
		);
	}

	public boolean isFilled() {
		for(Square[] rows : squares) {
			for(Square square : rows) {
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

		private final Square[][] squares;
		private GameFieldConfiguration configuration;

		public Builder(final GameFieldConfiguration configuration, final Element[][] elements) {
			isInputElementsEnoughLength(configuration, elements);
			this.configuration = configuration;
			final int numberOfSquaresOnSide = configuration.getNumberOfSquaresOnSide();
			squares = new Square[numberOfSquaresOnSide][numberOfSquaresOnSide];
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
