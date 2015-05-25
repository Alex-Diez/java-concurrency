package org.sudoku.game.elements;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Square {

	private final int rowIndex;
	private final int columnIndex;
	private final Element[][] matrix;
	private final Map<Element, Integer> elements = new LinkedHashMap<>(GameField.NUMBER_OF_ELEMENTS, 1.0f);

	private Square(Element[][] matrix, Map<Element, Integer> elements, int rowIndex, int columnIndex) {
		this.matrix = matrix;
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
		this.elements.putAll(elements);
	}

	public Element get(int i, int j) {
		int position = i * GameField.NUMBER_OF_ELEMENTS_IN_SQUARE_COLUMN + j;
		for(Map.Entry<Element, Integer> entry : elements.entrySet()) {
			if(position == entry.getValue()) {
				return entry.getKey();
			}
		}
		return Element.EMPTY_ELEMENT;
	}

	public void putElement(Element element, Integer position) {
		elements.put(element, position);
		int i = columnIndex * GameField.NUMBER_OF_ELEMENTS_IN_COLUMN + position % GameField.NUMBER_OF_ELEMENTS_IN_COLUMN;
		int j = rowIndex * GameField.NUMBER_OF_ELEMENTS_IN_ROW + position / GameField.NUMBER_OF_ELEMENTS_IN_ROW;
		matrix[i][j] = element;
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

	@Override
	public int hashCode() {
		int result = 17;
		result = 31 * result + (elements.hashCode());
		result = 31 * result + rowIndex;
		result = 31 * result + columnIndex;
		return result;
	}

	@Override
	public boolean equals(Object object) {
		if(this == object) {
			return true;
		}
		if(object != null
				&& object.getClass().equals(getClass())) {
			Square square = (Square) object;
			return square.elements.equals(elements)
					&& square.rowIndex == rowIndex
					&& square.columnIndex == columnIndex;
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		final int minI = columnIndex * GameField.NUMBER_OF_ELEMENTS_IN_SQUARE_COLUMN;
		final int maxI = (columnIndex + 1) * GameField.NUMBER_OF_ELEMENTS_IN_SQUARE_COLUMN;
		final int minJ = rowIndex * GameField.NUMBER_OF_ELEMENTS_IN_SQUARE_ROW;
		final int maxJ = (columnIndex + 1) * GameField.NUMBER_OF_ELEMENTS_IN_SQUARE_ROW;
		sb.append("  === === === \n");
		for(int i = minI; i < maxI; i++) {
			sb.append("||");
			for(int j = minJ; j < maxJ; j++) {
				sb.append(matrix[i][j]);
				if((j + 1) % 3 == 0) {
					sb.append("||");
				}
				else {
					sb.append("|");
				}
			}
			if((i + 1) % 3 == 0) {
				sb.append("\n  === === === \n");
			}
			else {
				sb.append("\n  --- --- --- \n");
			}
		}
		return sb.toString();
	}

	static class Builder {

		private final Map<Element, Integer> elements = new LinkedHashMap<>(GameField.NUMBER_OF_ELEMENTS, 1.0f);
		private final Element[][] matrix;
		private final int rowIndex;
		private final int columnIndex;

		public Builder(Element[][] elements, int columnIndex, int rowIndex) {
			if(elements.length == GameField.NUMBER_OF_ELEMENTS
					&& checkSubArrayLength(elements)) {
				this.matrix = elements;
				this.rowIndex = rowIndex;
				this.columnIndex = columnIndex;
				for(int i = 0; i < GameField.NUMBER_OF_ELEMENTS_IN_SQUARE_COLUMN; i++) {
					for(int j = 0; j < GameField.NUMBER_OF_ELEMENTS_IN_SQUARE_ROW; j++) {
						int cI = columnIndex * GameField.NUMBER_OF_ELEMENTS_IN_SQUARE_COLUMN + i;
						int rI = rowIndex * GameField.NUMBER_OF_ELEMENTS_IN_SQUARE_ROW + j;
						Element e = elements[cI][rI];
						if(Element.EMPTY_ELEMENT.compareTo(e) != 0) {
							int elementPosition = GameField.NUMBER_OF_ELEMENTS_IN_SQUARE_ROW * i + j;
							this.elements.put(e, elementPosition);
						}
					}
				}
			}
			else {
				throw new IllegalArgumentException(
						String.format(
								"Square can contains only %s elements in rows and %s elements in columns",
								GameField.NUMBER_OF_ELEMENTS_IN_SQUARE_ROW,
								GameField.NUMBER_OF_ELEMENTS_IN_SQUARE_COLUMN
						)
				);
			}
		}

		public Square build() {
			return new Square(matrix, elements, rowIndex, columnIndex);
		}

		private boolean checkSubArrayLength(Element[][] elements) {
			for(Element[] els : elements) {
				if(els.length != GameField.NUMBER_OF_ELEMENTS) {
					return false;
				}
			}
			return true;
		}
	}
}
