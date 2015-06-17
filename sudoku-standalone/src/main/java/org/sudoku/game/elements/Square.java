package org.sudoku.game.elements;

import org.sudoku.game.conf.GameFieldConfiguration;

import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;

public class Square {

	private static final String ROW_SEPARATOR = " --- --- --- ";
	private static final char COLUMN_SEPARATOR = '|';

	private final int rowIndex;
	private final int columnIndex;
	private final Element[][] matrix;
	private final Map<Element, Integer> elements;
	private final int numberOfElementsOnSquareSide;

	private Square(
			final int rowIndex,
			final int columnIndex,
			final int numberOfElementsOnSquare,
			final int numberOfElementsOnSquareSide,
			final Element[][] matrix,
			final Map<Element, Integer> elements) {
		this.matrix = matrix;
		this.rowIndex = rowIndex;
		this.columnIndex = columnIndex;
		this.elements = new LinkedHashMap<>(numberOfElementsOnSquare, 1.0f);
		this.elements.putAll(elements);
		this.numberOfElementsOnSquareSide = numberOfElementsOnSquareSide;
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
		return position / numberOfElementsOnSquareSide;
	}

	private int calculateRowOffset(Integer position) {
		return position % numberOfElementsOnSquareSide;
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
					&& square.rowIndex == rowIndex
					&& square.columnIndex == columnIndex;
		}
		return false;
	}

	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder(ROW_SEPARATOR + "\n");
		final int numberOfElementsOnSquareSide = this.numberOfElementsOnSquareSide;
		for (int i = 0; i < numberOfElementsOnSquareSide; i++) {
			sb.append(COLUMN_SEPARATOR);
			for (int j = 0; j < numberOfElementsOnSquareSide; j++) {
				sb.append(matrix[i][j]).append(COLUMN_SEPARATOR);
			}
			sb.append("\n" + ROW_SEPARATOR + "\n");
		}
		return sb.toString();
	}

	static class Builder {

		private final int rowIndex;
		private final int columnIndex;
		private final Map<Element, Integer> elements;
		private final Element[][] matrix;
		private final int numberOfElementsOnSide;
		private final int numberOfElementsOnSquareSide;

		public Builder(
				final GameFieldConfiguration configuration,
				final Element[][] elements,
				final int columnIndex,
				final int rowIndex) {
			this.numberOfElementsOnSide = configuration.getNumberOfElementsOnSide();
			this.numberOfElementsOnSquareSide = configuration.getNumberOfElementsOnSquareSide();
			isInputElementsEnoughLength(numberOfElementsOnSide, numberOfElementsOnSquareSide, elements);
			this.elements = new LinkedHashMap<>(numberOfElementsOnSide, 1.0f);
			this.matrix = new Element[numberOfElementsOnSquareSide][numberOfElementsOnSquareSide];
			for (int i = 0; i < numberOfElementsOnSquareSide; i++) {
				System.arraycopy(
						elements[rowIndex * numberOfElementsOnSquareSide + i],
						columnIndex * numberOfElementsOnSquareSide,
						matrix[i],
						0,
						numberOfElementsOnSquareSide
				);
			}
			this.rowIndex = rowIndex;
			this.columnIndex = columnIndex;
			for (int i = 0; i < numberOfElementsOnSquareSide; i++) {
				for (int j = 0; j < numberOfElementsOnSquareSide; j++) {
					int cI = columnIndex * numberOfElementsOnSquareSide + i;
					int rI = rowIndex * numberOfElementsOnSquareSide + j;
					Element e = elements[cI][rI];
					if (!Element.EMPTY_ELEMENT.equals(e)) {
						int elementPosition = numberOfElementsOnSquareSide * i + j;
						this.elements.put(e, elementPosition);
					}
				}
			}
		}

		private void isInputElementsEnoughLength(
				final int numberOfElementsOnSide,
				final int numberOfElementsOnSquareSide,
				Element[][] elements) {
			if (elements.length != numberOfElementsOnSide
					&& !isSubArraysHaveProperlyLengths(numberOfElementsOnSide, elements)) {
				throw new NotRightElementArrayLengthException(
						String.format(
								"Game field can contains only %s elements on side but is %s elements on side",
								numberOfElementsOnSquareSide,
								elements.length
						)
				);
			}
		}

		private boolean isSubArraysHaveProperlyLengths(
				final int numberOfElementsOnSide,
				final Element[][] elements) {
			for (Element[] els : elements) {
				if (els.length != numberOfElementsOnSide) {
					return false;
				}
			}
			return true;
		}

		public Square build() {
			return new Square(
					rowIndex,
					columnIndex,
					numberOfElementsOnSide,
					numberOfElementsOnSquareSide,
					matrix,
					elements
			);
		}
	}
}
