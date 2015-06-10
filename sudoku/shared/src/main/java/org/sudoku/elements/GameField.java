package org.sudoku.elements;

import org.sudoku.conf.GameFieldConfiguration;
import org.sudoku.strategies.ResolverByBlock;
import org.sudoku.strategies.StrategiesFactory;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GameField
		implements StrategiesFactory {

	public static final String ROW_SEPARATOR = " --- --- --- --- --- --- --- --- --- ";
	public static final char COLUMN_SEPARATOR = '|';

	private final Square[][] squares;
	private final ReadWriteLock[][] squaresLocks;
	private final GameFieldConfiguration configuration;

	private GameField(
			final GameFieldConfiguration configuration,
			final Square[][] squares,
			final ReadWriteLock[][] squaresLocks) {
		this.configuration = configuration;
		this.squares = squares;
		this.squaresLocks = squaresLocks;
	}

	@Override
	public Runnable build(final int columnIndex, final int rowIndex) {
		final int numberOfSquaresInColumn = configuration.getNumberOfSquaresInColumn();
		final int numberOfSquaresInRow = configuration.getNumberOfSquaresInRow();
		final int upRowIndex = calculateUpRowIndex(rowIndex, numberOfSquaresInRow);
		final int upColumnIndex = calculateUpColumnIndex(columnIndex, numberOfSquaresInColumn);
		final int downRowIndex = calculateDownRowIndex(rowIndex, numberOfSquaresInRow);
		final int downColumnIndex = calculateDownColumnIndex(columnIndex, numberOfSquaresInColumn);
		final int centerRowIndex = calculateCenterRowIndex(rowIndex, numberOfSquaresInRow);
		final int centerColumnIndex = calculateCenterColumnIndex(columnIndex, numberOfSquaresInColumn);
		final int leftRowIndex = calculateLeftRowIndex(rowIndex, numberOfSquaresInRow);
		final int leftColumnIndex = calculateLeftColumnIndex(columnIndex, numberOfSquaresInColumn);
		final int rightRowIndex = calculateRightRowIndex(rowIndex, numberOfSquaresInRow);
		final int rightColumnIndex = calculateRightColumnIndex(columnIndex, numberOfSquaresInColumn);
		return new ResolverByBlock(
				configuration,
				squares[upColumnIndex][upRowIndex],
				squaresLocks[upRowIndex][upRowIndex].readLock(),
				squares[downColumnIndex][downRowIndex],
				squaresLocks[downColumnIndex][downRowIndex].readLock(),
				squares[centerColumnIndex][centerRowIndex],
				squaresLocks[centerColumnIndex][centerRowIndex],
				squares[leftColumnIndex][leftRowIndex],
				squaresLocks[leftColumnIndex][leftRowIndex].readLock(),
				squares[rightColumnIndex][rightRowIndex],
				squaresLocks[rightColumnIndex][rightRowIndex].readLock()
		);
	}

	private int calculateRightColumnIndex(int columnIndex, int numberOfSquaresInColumn) {
		return (columnIndex + 1 + numberOfSquaresInColumn) % numberOfSquaresInColumn;
	}

	private int calculateRightRowIndex(int rowIndex, int numberOfSquaresInRow) {
		return (rowIndex + numberOfSquaresInRow) % numberOfSquaresInRow;
	}

	private int calculateLeftRowIndex(int rowIndex, int numberOfSquaresInRow) {
		return (rowIndex + numberOfSquaresInRow) % numberOfSquaresInRow;
	}

	private int calculateCenterColumnIndex(int columnIndex, int numberOfSquaresInColumn) {
		return (columnIndex + numberOfSquaresInColumn) % numberOfSquaresInColumn;
	}

	private int calculateCenterRowIndex(int rowIndex, int numberOfSquaresInRow) {
		return (rowIndex + numberOfSquaresInRow) % numberOfSquaresInRow;
	}

	private int calculateDownColumnIndex(int columnIndex, int numberOfSquaresInColumn) {
		return (columnIndex + numberOfSquaresInColumn) % numberOfSquaresInColumn;
	}

	private int calculateDownRowIndex(int rowIndex, int numberOfSquaresInRow) {
		return (rowIndex + 1 + numberOfSquaresInRow) % numberOfSquaresInRow;
	}

	private int calculateUpColumnIndex(int columnIndex, int numberOfSquaresInColumn) {
		return (columnIndex + numberOfSquaresInColumn) % numberOfSquaresInColumn;
	}

	private int calculateLeftColumnIndex(int columnIndex, int numberOfSquaresInColumn) {
		return (columnIndex - 1 + numberOfSquaresInColumn) % numberOfSquaresInColumn;
	}

	private int calculateUpRowIndex(int rowIndex, int numberOfSquaresInRow) {
		return (rowIndex - 1 + numberOfSquaresInRow) % numberOfSquaresInRow;
	}

	public boolean isFilled() {
		for (Square[] rows : squares) {
			for (Square square : rows) {
				if (!square.isFilled()) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(ROW_SEPARATOR + "\n");
		final int numberOfElements = configuration.getNumberOfElements();
		for (int i = 0; i < numberOfElements; i++) {
			sb.append(COLUMN_SEPARATOR);
			final int squareColumnIndex = calculateSquareColumnIndex(i);
			for (int j = 0; j < numberOfElements; j++) {
				final int squareRowIndex = calculateSquareRowIndex(j);
				sb.append(
						squares[squareColumnIndex][squareRowIndex]
								.get(
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
		return rowIndex % configuration.getNumberOfElementsInSquareRow();
	}

	private int calculateColumnOffset(final int columnIndex) {
		return columnIndex % configuration.getNumberOfElementsInSquareColumn();
	}

	private int calculateSquareRowIndex(final int rowIndex) {
		return rowIndex / configuration.getNumberOfSquaresInRow();
	}

	private int calculateSquareColumnIndex(final int columnIndex) {
		return columnIndex / configuration.getNumberOfSquaresInColumn();
	}

	public Square retrieveSquare(final int index) {
		return null;
	}

	public SubstitutableBlock buildBlock(final int columnIndex, final int rowIndex) {
		return null;
	}

	public Square buildSquareAt(final int columnIndex, final int rowIndex) {
		return null;
	}

	public static class Builder {

		private final Square[][] squares;
		private final ReadWriteLock[][] squaresLocks;
		private GameFieldConfiguration configuration;

		public Builder(final GameFieldConfiguration configuration, final Element[][] elements) {
			isInputElementsEnoughLength(configuration, elements);
			this.configuration = configuration;
			final int numberOfSquaresInColumn = configuration.getNumberOfSquaresInColumn();
			final int numberOfSquaresInRow = configuration.getNumberOfSquaresInRow();
			squares = new Square[numberOfSquaresInColumn][numberOfSquaresInRow];
			squaresLocks = new ReadWriteLock[numberOfSquaresInColumn][numberOfSquaresInRow];
			for (int i = 0; i < numberOfSquaresInColumn; i++) {
				for (int j = 0; j < numberOfSquaresInRow; j++) {
					squares[i][j] = new Square.Builder(configuration, elements, i, j).build();
					squaresLocks[i][j] = new ReentrantReadWriteLock();
				}
			}
		}

		private void isInputElementsEnoughLength(
				final GameFieldConfiguration configuration,
				final Element[][] elements) {
			final int numberOfElements = configuration.getNumberOfElements();
			if (elements.length != numberOfElements
					&& !isSubArraysHaveProperlyLengths(configuration, elements)) {
				throw new NotRightElementArrayLengthException(
						String.format(
								"Game field can contains only %s elements in rows and %s elements in columns",
								configuration.getNumberOfElementsInSquareRow(),
								configuration.getNumberOfElementsInSquareColumn()
						)
				);
			}
		}

		private boolean isSubArraysHaveProperlyLengths(
				final GameFieldConfiguration configuration,
				final Element[][] elements) {
			final int numberOfElementsInRow = configuration.getNumberOfElementsInRow();
			for (Element[] els : elements) {
				if (els.length != numberOfElementsInRow) {
					return false;
				}
			}
			return true;
		}

		public GameField build() {
			return new GameField(configuration, squares, squaresLocks);
		}
	}
}
