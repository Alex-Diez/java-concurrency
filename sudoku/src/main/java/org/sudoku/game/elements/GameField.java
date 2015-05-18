package org.sudoku.game.elements;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class GameField {

	static final int NUMBER_OF_ELEMENTS = 9;
	static final int NUMBER_OF_ELEMENTS_IN_ROW = NUMBER_OF_ELEMENTS;
	static final int NUMBER_OF_ELEMENTS_IN_COLUMN = NUMBER_OF_ELEMENTS;
	static final int NUMBER_OF_SUB_SQUARES = 3;
	static final int NUMBER_OF_SUBSTITUTABLE_BLOCKS = NUMBER_OF_SUB_SQUARES;
	static final int NUMBER_OF_ELEMENTS_IN_SUB_SQUARE_ROW = NUMBER_OF_ELEMENTS_IN_ROW / NUMBER_OF_SUB_SQUARES;
	static final int NUMBER_OF_ELEMENTS_IN_SUB_SQUARE_COLUMN = NUMBER_OF_ELEMENTS_IN_COLUMN / NUMBER_OF_SUB_SQUARES;

	private final SubstitutableBlock[][] substitutableBlocks;
	private final Element[][] elements;

	private GameField(Element[][] elements) {
		this.elements = elements;
		SubSquare[][] subSquares = new SubSquare[NUMBER_OF_SUB_SQUARES][NUMBER_OF_SUB_SQUARES];
		for (int i = 0; i < NUMBER_OF_SUB_SQUARES; i++) {
			for (int j = 0; j < NUMBER_OF_SUB_SQUARES; j++) {
				SubSquare subSquare = buildSubSquareAt(elements, i, j);
				subSquares[i][j] = subSquare;
			}
		}
		substitutableBlocks = new SubstitutableBlock[NUMBER_OF_SUBSTITUTABLE_BLOCKS][NUMBER_OF_SUBSTITUTABLE_BLOCKS];
		for (int i = 0; i < NUMBER_OF_SUB_SQUARES; i++) {
			for (int j = 0; j < NUMBER_OF_SUB_SQUARES; j++) {
				int upRowIndex = (i - 1 + NUMBER_OF_SUB_SQUARES) % NUMBER_OF_SUB_SQUARES;
				int upColumnIndex = (j + NUMBER_OF_SUB_SQUARES) % NUMBER_OF_SUB_SQUARES;
				int downRowIndex = (i + 1 + NUMBER_OF_SUB_SQUARES) % NUMBER_OF_SUB_SQUARES;
				int downColumnIndex = (j + NUMBER_OF_SUB_SQUARES) % NUMBER_OF_SUB_SQUARES;
				int centerRowIndex = (i + NUMBER_OF_SUB_SQUARES) % NUMBER_OF_SUB_SQUARES;
				int centerColumnIndex = (j + NUMBER_OF_SUB_SQUARES) % NUMBER_OF_SUB_SQUARES;
				int leftRowIndex = (i + NUMBER_OF_SUB_SQUARES) % NUMBER_OF_SUB_SQUARES;
				int leftColumnIndex = (j - 1 + NUMBER_OF_SUB_SQUARES) % NUMBER_OF_SUB_SQUARES;
				int rightRowIndex = (i + NUMBER_OF_SUB_SQUARES) % NUMBER_OF_SUB_SQUARES;
				int rightColumnIndex = (j + 1 + NUMBER_OF_SUB_SQUARES) % NUMBER_OF_SUB_SQUARES;
				substitutableBlocks[i][j] = new SubstitutableBlock(
						subSquares[upRowIndex][upColumnIndex],
						subSquares[downRowIndex][downColumnIndex],
						subSquares[centerRowIndex][centerColumnIndex],
						subSquares[leftRowIndex][leftColumnIndex],
						subSquares[rightRowIndex][rightColumnIndex]
				);
			}
		}
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

	private SubSquare buildSubSquareAt(
			Element[][] elements,
			int columnIndex,
			int rowIndex) {
		Element[][] subSquare
				= new Element[NUMBER_OF_ELEMENTS_IN_SUB_SQUARE_COLUMN][NUMBER_OF_ELEMENTS_IN_SUB_SQUARE_ROW];
		for (int i = 0; i < NUMBER_OF_ELEMENTS_IN_SUB_SQUARE_COLUMN; i++) {
			for (int j = 0; j < NUMBER_OF_ELEMENTS_IN_SUB_SQUARE_ROW; j++) {
				int columnPosition = columnIndex * NUMBER_OF_ELEMENTS_IN_SUB_SQUARE_COLUMN + i;
				int rowPosition = rowIndex * NUMBER_OF_ELEMENTS_IN_SUB_SQUARE_ROW + j;
				subSquare[i][j] = elements[columnPosition][rowPosition];
			}
		}
		return new SubSquare.Builder(subSquare, rowIndex, columnIndex).build();
	}

	public static class SubSquare {

		private final int rowIndex;
		private final int columnIndex;
		private final Map<Element, Integer> elements = new LinkedHashMap<>(GameField.NUMBER_OF_ELEMENTS, 1.0f);

		private SubSquare(Element[][] elements, int rowIndex, int columnIndex) {
			this.rowIndex = rowIndex;
			this.columnIndex = columnIndex;
			for (int i = 0; i < elements.length; i++) {
				for (int j = 0; j < elements[i].length; j++) {
					Element e = elements[i][j];
					if (e != Element.EMPTY_ELEMENT) {
						this.elements.put(e, GameField.NUMBER_OF_ELEMENTS_IN_SUB_SQUARE_ROW * i + j);
					}
				}
			}
		}

		public Element get(int i, int j) {
			int position = i * NUMBER_OF_ELEMENTS_IN_SUB_SQUARE_COLUMN + j;
			for (Map.Entry<Element, Integer> entry : elements.entrySet()) {
				if (position == entry.getValue()) {
					return entry.getKey();
				}
			}
			return Element.EMPTY_ELEMENT;
		}

		public void putElement(Element element, Integer position) {
			elements.put(element, position);
		}

		public Integer getElementPosition(Element element) {
			return elements.get(element);
		}

		public int size() {
			return elements.size();
		}

		public boolean hasElement(Element element) {
			return elements.containsKey(element);
		}

		public Collection<Integer> filledPositions() {
			return elements.values();
		}

		public int hashCode() {
			int result = 17;
			result = 31 * result + (elements.hashCode());
			result = 31 * result + rowIndex;
			result = 31 * result + columnIndex;
			return result;
		}

		public boolean equals(Object object) {
			if (this == object) {
				return true;
			}
			if (object != null
					&& object.getClass().equals(getClass())) {
				SubSquare subSquare = (SubSquare) object;
				return subSquare.elements.equals(elements)
						&& subSquare.rowIndex == rowIndex
						&& subSquare.columnIndex == columnIndex;
			}
			return false;
		}

		static class Builder {

			private final Element[][] elements;
			private final int rowIndex;
			private final int columnIndex;

			public Builder(Element[][] elements, int rowIndex, int columnIndex) {
				if (elements.length == GameField.NUMBER_OF_ELEMENTS_IN_SUB_SQUARE_ROW
						&& checkSubArrayLength(elements)) {
					this.elements = new Element[GameField.NUMBER_OF_ELEMENTS_IN_SUB_SQUARE_COLUMN]
							[GameField.NUMBER_OF_ELEMENTS_IN_SUB_SQUARE_ROW];
					for (int i = 0; i < elements.length; i++) {
						System.arraycopy(elements[i], 0, this.elements[i], 0, elements[i].length);
					}
					this.rowIndex = rowIndex;
					this.columnIndex = columnIndex;
				}
				else {
					throw new IllegalArgumentException(
							String.format(
									"Sub square can contains only %s elements in rows and %s elements in columns",
									GameField.NUMBER_OF_ELEMENTS_IN_SUB_SQUARE_ROW,
									GameField.NUMBER_OF_ELEMENTS_IN_SUB_SQUARE_COLUMN
							)
					);
				}
			}

			public SubSquare build() {
				return new SubSquare(elements, rowIndex, columnIndex);
			}

			private boolean checkSubArrayLength(Element[][] elements) {
				for (Element[] els : elements) {
					if (els.length != GameField.NUMBER_OF_ELEMENTS_IN_SUB_SQUARE_ROW) {
						return false;
					}
				}
				return true;
			}
		}
	}

	public static class Builder {

		private final Element[][] elements;

		public Builder(Element[][] elements) {
			this.elements = new Element[elements.length][elements[0].length];
			for (int i = 0; i < elements.length; i++) {
				System.arraycopy(elements[i], 0, this.elements[i], 0, elements.length);
			}
		}

		public GameField build() {
			return new GameField(elements);
		}
	}
}
