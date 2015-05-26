package org.sudoku.game.elements;

import org.sudoku.game.strategies.StrategiesFactory;

public class GameField
		implements StrategiesFactory {

	public static final int NUMBER_OF_ELEMENTS = 9;
	public static final int NUMBER_OF_ELEMENTS_IN_ROW = NUMBER_OF_ELEMENTS;
	public static final int NUMBER_OF_ELEMENTS_IN_COLUMN = NUMBER_OF_ELEMENTS;
	public static final int NUMBER_OF_SQUARES = 3;
	public static final int NUMBER_OF_ELEMENTS_IN_SQUARE_ROW = NUMBER_OF_ELEMENTS_IN_ROW / NUMBER_OF_SQUARES;
	public static final int NUMBER_OF_ELEMENTS_IN_SQUARE_COLUMN = NUMBER_OF_ELEMENTS_IN_COLUMN / NUMBER_OF_SQUARES;
	public static final int NUMBER_OF_SUBSTITUTABLE_BLOCKS = NUMBER_OF_SQUARES;

	private final Element[][] elements;

	private GameField(Element[][] elements) {
		this.elements = elements;
	}

	@Override
	public Runnable build(int columnIndex, int rowIndex) {
//		Square[][] squares = new Square[NUMBER_OF_SQUARES][NUMBER_OF_SQUARES];
//		for (int i = 0; i < NUMBER_OF_SQUARES; i++) {
//			for (int j = 0; j < NUMBER_OF_SQUARES; j++) {
//				Square square = new Square.Builder(this.elements, i, j).build();
//				squares[i][j] = square;
//			}
//		}
//		substitutableBlocks =
//				new SubstitutableBlock[NUMBER_OF_SUBSTITUTABLE_BLOCKS][NUMBER_OF_SUBSTITUTABLE_BLOCKS];
//		for (int i = 0; i < NUMBER_OF_SQUARES; i++) {
//			for (int j = 0; j < NUMBER_OF_SQUARES; j++) {
//				int upRowIndex = (i - 1 + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
//				int upColumnIndex = (j + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
//				int downRowIndex = (i + 1 + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
//				int downColumnIndex = (j + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
//				int centerRowIndex = (i + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
//				int centerColumnIndex = (j + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
//				int leftRowIndex = (i + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
//				int leftColumnIndex = (j - 1 + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
//				int rightRowIndex = (i + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
//				int rightColumnIndex = (j + 1 + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
//				substitutableBlocks[i][j] = new SubstitutableBlock(
//						squares[upRowIndex][upColumnIndex],
//						squares[downRowIndex][downColumnIndex],
//						squares[centerRowIndex][centerColumnIndex],
//						squares[leftRowIndex][leftColumnIndex],
//						squares[rightRowIndex][rightColumnIndex]
//				);
//			}
//		}
		return null;
	}

	public boolean isFilled() {
		for (Element[] rows : elements) {
			for (Element element : rows) {
				if (Element.EMPTY_ELEMENT.equals(element)) {
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
				sb.append(elements[i][j]);
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

		private final Element[][] elements;

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
			this.elements = new Element[NUMBER_OF_ELEMENTS_IN_ROW][NUMBER_OF_ELEMENTS_IN_COLUMN];
			for (int i = 0; i < elements.length; i++) {
				System.arraycopy(elements[i], 0, this.elements[i], 0, elements[i].length);
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
			return new GameField(elements);
		}
	}
}
