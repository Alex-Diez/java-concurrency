package org.sudoku.elements;

import org.sudoku.conf.GameFieldConfiguration;

import java.io.Serializable;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Square
		implements Serializable {

	private static final String ROW_SEPARATOR = " --- --- --- ";
	private static final char COLUMN_SEPARATOR = '|';

	private final int rowIndex;
	private final int columnIndex;
	private final GameFieldConfiguration configuration;
	private final Element[][] matrix;
	private final Map<Element, Integer> elements;

	private Square(
			final GameFieldConfiguration configurations,
			final int rowIndex,
			final int columnIndex,
			final Element[][] matrix,
			final Map<Element, Integer> elements) {
		this.configuration = configurations;
		this.matrix = matrix;
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
		this.elements = new LinkedHashMap<>(configurations.getNumberOfElements(), 1.0f);
		this.elements.putAll(elements);
	}

	public boolean onSamePositionWith(Square square) {
		return hasSameRowIndex(square)
				&& hasSameColumnIndex(square);
	}

	private boolean hasSameColumnIndex(Square square) {
		return square.columnIndex == columnIndex;
	}

	private boolean hasSameRowIndex(Square square) {
		return square.rowIndex == rowIndex;
	}

	public int elementsInRow() {
		return configuration.getNumberOfElementsInSquareRow();
	}

	public int elementsInColumn() {
		return configuration.getNumberOfElementsInSquareColumn();
	}

	public Element get(int i, int j) {
		return matrix[i][j];
	}

	public void putElement(Element element, Integer position) {
		elements.put(element, position);
		int i = calculateColumnOffset(position);
		int j = calculateRowOffset(position);
		matrix[i][j] = element;
	}

	private int calculateColumnOffset(Integer position) {
		return position / configuration.getNumberOfElementsInSquareColumn();
	}

	private int calculateRowOffset(Integer position) {
		return position % configuration.getNumberOfElementsInSquareRow();
	}

	public Integer getElementPosition(Element element) {
		return elements.get(element);
	}

	public boolean isFilled() {
		return elements.size() == 9;
	}

	public boolean hasElement(Element element) {
		return elements.containsKey(element);
	}

	public Collection<Integer> filledPositions() {
		return elements.values();
	}

	public int size() {
		return elements.size();
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
		if (this == object) {
			return true;
		}
		if (object != null
				&& object.getClass().equals(getClass())) {
			Square square = (Square) object;
			return square.elements.equals(elements)
					&& hasSameRowIndex(square)
					&& hasSameColumnIndex(square);
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(ROW_SEPARATOR + "\n");
		final int numberOfElementsInSquareColumn = configuration.getNumberOfElementsInSquareColumn();
		final int numberOfElementsInSquareRow = configuration.getNumberOfElementsInSquareRow();
		for (int i = 0; i < numberOfElementsInSquareColumn; i++) {
			sb.append(COLUMN_SEPARATOR);
			for (int j = 0; j < numberOfElementsInSquareRow; j++) {
				sb.append(matrix[i][j]).append(COLUMN_SEPARATOR);
			}
			sb.append("\n" + ROW_SEPARATOR + "\n");
		}
		return sb.toString();
	}

	public static class Builder {

		private final GameFieldConfiguration configuration;
		private final int rowIndex;
		private final int columnIndex;
		private final Map<Element, Integer> elements;
		private final Element[][] matrix;

		public Builder(
				final GameFieldConfiguration configuration,
				final Element[][] elements,
				final int columnIndex,
				final int rowIndex) {
			isInputElementsEnoughLength(configuration, elements);
			this.configuration = configuration;
			this.elements = new LinkedHashMap<>(
					configuration.getNumberOfElements(),
					1.0f
			);
			final int numberOfElementsInSquareColumn = configuration.getNumberOfElementsInSquareColumn();
			final int numberOfElementsInSquareRow = configuration.getNumberOfElementsInSquareRow();
			final int numberOfSquaresInColumn = configuration.getNumberOfSquaresInColumn();
			this.matrix = new Element[numberOfElementsInSquareColumn][numberOfElementsInSquareRow];
			for (int i = 0; i < numberOfSquaresInColumn; i++) {
				System.arraycopy(
						elements[columnIndex * numberOfElementsInSquareColumn + i],
						rowIndex * numberOfElementsInSquareRow,
						matrix[i],
						0,
						numberOfElementsInSquareRow
				);
			}
			this.rowIndex = rowIndex;
			this.columnIndex = columnIndex;
			for (int i = 0; i < numberOfElementsInSquareColumn; i++) {
				for (int j = 0; j < numberOfElementsInSquareRow; j++) {
					int cI = columnIndex * numberOfElementsInSquareColumn + i;
					int rI = rowIndex * numberOfElementsInSquareRow + j;
					Element e = elements[cI][rI];
					if (!Element.EMPTY_ELEMENT.equals(e)) {
						int elementPosition = numberOfElementsInSquareRow * i + j;
						this.elements.put(e, elementPosition);
					}
				}
			}
		}

		private void isInputElementsEnoughLength(GameFieldConfiguration configuration, Element[][] elements) {
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

		private boolean isSubArraysHaveProperlyLengths(GameFieldConfiguration configurations, Element[][] elements) {
			for (Element[] els : elements) {
				if (els.length != configurations.getNumberOfElements()) {
					return false;
				}
			}
			return true;
		}

		public Square build() {
			return new Square(configuration, rowIndex, columnIndex, matrix, elements);
		}
	}
}
