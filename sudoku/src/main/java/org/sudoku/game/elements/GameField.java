package org.sudoku.game.elements;

import org.sudoku.game.strategies.ResolverByBlock;
import org.sudoku.game.strategies.StrategiesFactory;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class GameField
		implements StrategiesFactory {

	public static final int NUMBER_OF_ELEMENTS = 9;
	public static final int NUMBER_OF_ELEMENTS_IN_ROW = NUMBER_OF_ELEMENTS;
	public static final int NUMBER_OF_ELEMENTS_IN_COLUMN = NUMBER_OF_ELEMENTS;
	public static final int NUMBER_OF_SQUARES = 3;
	public static final int NUMBER_OF_ELEMENTS_IN_SQUARE_ROW = NUMBER_OF_ELEMENTS_IN_ROW / NUMBER_OF_SQUARES;
	public static final int NUMBER_OF_ELEMENTS_IN_SQUARE_COLUMN = NUMBER_OF_ELEMENTS_IN_COLUMN / NUMBER_OF_SQUARES;
	public static final int NUMBER_OF_SUBSTITUTABLE_BLOCKS = NUMBER_OF_SQUARES;

	private final Square[][] squares;
	private final ReadWriteLock[][] squaresLocks;

	private GameField(Square[][] squares, ReadWriteLock[][] squaresLocks) {
		this.squares = squares;
		this.squaresLocks = squaresLocks;
	}

	@Override
	public Runnable build(int columnIndex, int rowIndex) {
		int upRowIndex = (columnIndex - 1 + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
		int upColumnIndex = (rowIndex + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
		int downRowIndex = (columnIndex + 1 + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
		int downColumnIndex = (rowIndex + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
		int centerRowIndex = (columnIndex + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
		int centerColumnIndex = (rowIndex + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
		int leftRowIndex = (columnIndex + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
		int leftColumnIndex = (rowIndex - 1 + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
		int rightRowIndex = (columnIndex + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
		int rightColumnIndex = (rowIndex + 1 + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
		return new ResolverByBlock(
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

	public boolean isFilled() {
		for (Square[] rows : squares) {
			for (Square square : rows) {
				if (square.isFilled()) {
					return false;
				}
			}
		}
		return true;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("  === === ===  === === ===  === === === \n");
		for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
			sb.append("||");
			for (int j = 0; j < NUMBER_OF_ELEMENTS; j++) {
				sb.append(
						squares[i / NUMBER_OF_SQUARES][j / NUMBER_OF_SQUARES]
								.get(
										i / NUMBER_OF_ELEMENTS_IN_SQUARE_COLUMN,
										j % NUMBER_OF_ELEMENTS_IN_SQUARE_ROW
						)
				);
				if ((j + 1) % 3 == 0) {
					sb.append("||");
				}
				else {
					sb.append("|");
				}
			}
			if ((i + 1) % 3 == 0) {
				sb.append("\n  === === ===  === === ===  === === === \n");
			}
			else {
				sb.append("\n  --- --- ---  --- --- ---  --- --- --- \n");
			}
		}
		return sb.toString();
	}

	public static class Builder {

		private final Square[][] squares;
		private final ReadWriteLock[][] squaresLocks;

		public Builder(Element[][] elements) {
			if (elements.length != GameField.NUMBER_OF_ELEMENTS_IN_ROW
					&& !isSubArraysHaveProperlyLengths(elements)) {
				throw new IllegalArgumentException(
						String.format(
								"Game field can contains only %s elements in rows and %s elements in columns",
								GameField.NUMBER_OF_ELEMENTS_IN_SQUARE_ROW,
								GameField.NUMBER_OF_ELEMENTS_IN_SQUARE_COLUMN
						)
				);
			}
			squares = new Square[NUMBER_OF_SQUARES][NUMBER_OF_SQUARES];
			squaresLocks = new ReadWriteLock[NUMBER_OF_SQUARES][NUMBER_OF_SQUARES];
			for (int i = 0; i < NUMBER_OF_SQUARES; i++) {
				for (int j = 0; j < NUMBER_OF_SQUARES; j++) {
					squares[i][j] = new Square.Builder(elements, i, j).build();
					squaresLocks[i][j] = new ReentrantReadWriteLock();
				}
			}
		}

		private boolean isSubArraysHaveProperlyLengths(Element[][] elements) {
			for (Element[] els : elements) {
				if (els.length != GameField.NUMBER_OF_ELEMENTS_IN_ROW) {
					return false;
				}
			}
			return true;
		}

		public GameField build() {
			return new GameField(squares, squaresLocks);
		}
	}
}
