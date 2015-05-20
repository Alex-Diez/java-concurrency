package org.sudoku.game.elements;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Square {

	private final int rowIndex;
	private final int columnIndex;
	private final Map<Element, Integer> elements = new LinkedHashMap<>(GameField.NUMBER_OF_ELEMENTS, 1.0f);

	private Square(Element[][] elements, int rowIndex, int columnIndex) {
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
		for(int i = 0; i < elements.length; i++) {
			for(int j = 0; j < elements[i].length; j++) {
				Element e = elements[i][j];
				if(e != Element.EMPTY_ELEMENT) {
					this.elements.put(e, GameField.NUMBER_OF_ELEMENTS_IN_SQUARE_ROW * i + j);
				}
			}
		}
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

	static class Builder {

		private final Element[][] elements;
		private final int rowIndex;
		private final int columnIndex;

		public Builder(Element[][] elements, int rowIndex, int columnIndex) {
			if(elements.length == GameField.NUMBER_OF_ELEMENTS_IN_SQUARE_ROW
					&& checkSubArrayLength(elements)) {
				this.elements = new Element[GameField.NUMBER_OF_ELEMENTS_IN_SQUARE_COLUMN]
						[GameField.NUMBER_OF_ELEMENTS_IN_SQUARE_ROW];
				for(int i = 0; i < elements.length; i++) {
					System.arraycopy(elements[i], 0, this.elements[i], 0, elements[i].length);
				}
				this.rowIndex = rowIndex;
				this.columnIndex = columnIndex;
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
			return new Square(elements, rowIndex, columnIndex);
		}

		private boolean checkSubArrayLength(Element[][] elements) {
			for(Element[] els : elements) {
				if(els.length != GameField.NUMBER_OF_ELEMENTS_IN_SQUARE_ROW) {
					return false;
				}
			}
			return true;
		}
	}
}
