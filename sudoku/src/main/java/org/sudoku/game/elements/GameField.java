package org.sudoku.game.elements;

public class GameField {

	static final int NUMBER_OF_ELEMENTS = 9;
	static final int NUMBER_OF_ELEMENTS_IN_ROW = NUMBER_OF_ELEMENTS;
	static final int NUMBER_OF_ELEMENTS_IN_COLUMN = NUMBER_OF_ELEMENTS;
	static final int NUMBER_OF_SQUARES = 3;
	static final int NUMBER_OF_ELEMENTS_IN_SQUARE_ROW = NUMBER_OF_ELEMENTS_IN_ROW / NUMBER_OF_SQUARES;
	static final int NUMBER_OF_ELEMENTS_IN_SQUARE_COLUMN = NUMBER_OF_ELEMENTS_IN_COLUMN / NUMBER_OF_SQUARES;
	public static final int NUMBER_OF_SUBSTITUTABLE_BLOCKS = NUMBER_OF_SQUARES;

	private final SubstitutableBlock[][] substitutableBlocks;
	private final Element[][] elements;

	private GameField(SubstitutableBlock[][] substitutableBlocks, Element[][] elements) {
		this.substitutableBlocks = substitutableBlocks;
		this.elements = elements;
	}

	public boolean isFilled() {
		for (SubstitutableBlock[] rows : substitutableBlocks) {
			for (SubstitutableBlock block : rows) {
				if (!block.isFilled()) {
					return false;
				}
			}
		}
		return true;
	}

	public SubstitutableBlock get(int i, int j) {
		return substitutableBlocks[i][j];
	}

	public String toString() {
		StringBuilder sb = new StringBuilder();
		sb.append("  === === ===  === === ===  === === === \n");
		for (int i = 0; i < NUMBER_OF_ELEMENTS; i++) {
			sb.append("||");
			for (int j = 0; j < NUMBER_OF_ELEMENTS; j++) {
				sb.append(elements[i][j]);
				if((j+1)%3 == 0) {
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
		private final SubstitutableBlock[][] substitutableBlocks;

		public Builder(Element[][] elements) {
			if(elements.length == GameField.NUMBER_OF_ELEMENTS_IN_ROW
					&& checkSubArrayLength(elements)) {
				this.elements = new Element[NUMBER_OF_ELEMENTS_IN_ROW][NUMBER_OF_ELEMENTS_IN_COLUMN];
				for(int i = 0; i < elements.length; i++) {
					System.arraycopy(elements[i], 0, this.elements[i], 0, elements[i].length);
				}
				Square[][] squares = new Square[NUMBER_OF_SQUARES][NUMBER_OF_SQUARES];
				for(int i = 0; i < NUMBER_OF_SQUARES; i++) {
					for(int j = 0; j < NUMBER_OF_SQUARES; j++) {
						Square square = new Square.Builder(this.elements, i, j).build();
						squares[i][j] = square;
					}
				}
				substitutableBlocks = new SubstitutableBlock[NUMBER_OF_SUBSTITUTABLE_BLOCKS][NUMBER_OF_SUBSTITUTABLE_BLOCKS];
				for(int i = 0; i < NUMBER_OF_SQUARES; i++) {
					for(int j = 0; j < NUMBER_OF_SQUARES; j++) {
						int upRowIndex = (i - 1 + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
						int upColumnIndex = (j + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
						int downRowIndex = (i + 1 + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
						int downColumnIndex = (j + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
						int centerRowIndex = (i + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
						int centerColumnIndex = (j + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
						int leftRowIndex = (i + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
						int leftColumnIndex = (j - 1 + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
						int rightRowIndex = (i + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
						int rightColumnIndex = (j + 1 + NUMBER_OF_SQUARES) % NUMBER_OF_SQUARES;
						substitutableBlocks[i][j] = new SubstitutableBlock(
								squares[upRowIndex][upColumnIndex],
								squares[downRowIndex][downColumnIndex],
								squares[centerRowIndex][centerColumnIndex],
								squares[leftRowIndex][leftColumnIndex],
								squares[rightRowIndex][rightColumnIndex]
						);
					}
				}
			}
			else {
				throw new IllegalArgumentException(
						String.format(
								"Game field can contains only %s elements in rows and %s elements in columns",
								GameField.NUMBER_OF_ELEMENTS_IN_SQUARE_ROW,
								GameField.NUMBER_OF_ELEMENTS_IN_SQUARE_COLUMN
						)
				);
			}
		}

		private boolean checkSubArrayLength(Element[][] elements) {
			for(Element[] els : elements) {
				if(els.length != GameField.NUMBER_OF_ELEMENTS_IN_ROW) {
					return false;
				}
			}
			return true;
		}

		public GameField build() {
			return new GameField(substitutableBlocks, elements);
		}
	}
}
