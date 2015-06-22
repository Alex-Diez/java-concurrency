package org.sudoku.game.elements;

import org.sudoku.game.conf.GameFieldConfiguration;

import java.util.Arrays;
import java.util.Collection;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.concurrent.locks.ReadWriteLock;

public class Square
		implements ReadWriteSquare {

	private static final String ROW_SEPARATOR = " --- --- --- ";
	private static final char COLUMN_SEPARATOR = '|';

	private final Element[][] matrix;
	private final int numberOfElementsOnSquareSide;
	private final ReadWriteLock readWriteLock;

	private ReadOnlySquare left;
	private ReadOnlySquare right;
	private ReadOnlySquare up;
	private ReadOnlySquare down;

	private Square(
			final int numberOfElementsOnSquare,
			final int numberOfElementsOnSquareSide,
			final Element[][] matrix,
			final Map<Element, Integer> elements,
			final ReadWriteLock readWriteLock) {
		this.matrix = matrix;
		this.numberOfElementsOnSquareSide = numberOfElementsOnSquareSide;
		this.readWriteLock = readWriteLock;
	}

	@Override
	public boolean lockForRead() {
		return readWriteLock.readLock().tryLock();
	}

	@Override
	public Element readFrom(int rowIndex, int columnIndex) {
		return matrix[rowIndex][columnIndex];
	}

	@Override
	public void unlockAfterRead() {
		readWriteLock.readLock().unlock();
	}

	@Override
	public boolean containsElement(Element element) {
		return Element.EMPTY_ELEMENT.compareTo(matrix[element.position.row][element.position.column]) != 0;
	}

	@Override
	public boolean lockForWrite() {
		return readWriteLock.writeLock().tryLock();
	}

	@Override
	public void writeTo(int rowIndex, int columnIndex, Element element) {
		int position = rowIndex * numberOfElementsOnSquareSide + columnIndex;
		matrix[rowIndex][columnIndex] = element;
	}

	@Override
	public void unlockAfterWrite() {
		readWriteLock.writeLock().unlock();
	}

	public int getElementPosition(Element element) {
		return element.position.column * numberOfElementsOnSquareSide + element.position.row;
	}

	public boolean isFilled() {
		return elements.size() == 9;
	}

	@Override
	public ReadOnlySquare getLeft() {
		return left;
	}

	@Override
	public ReadOnlySquare setLeft(ReadOnlySquare square) {
		final ReadOnlySquare previous = this.left;
		this.left = square;
		return previous;
	}

	@Override
	public ReadOnlySquare getRight() {
		return right;
	}

	@Override
	public ReadOnlySquare setRight(ReadWriteSquare square) {
		final ReadOnlySquare previous = this.right;
		this.right = square;
		return previous;
	}

	@Override
	public ReadOnlySquare getUpper() {
		return up;
	}

	@Override
	public ReadOnlySquare setUpper(ReadOnlySquare square) {
		final ReadOnlySquare previous = this.up;
		this.up = square;
		return previous;
	}

	@Override
	public ReadOnlySquare getLower() {
		return down;
	}

	@Override
	public ReadOnlySquare setLower(ReadOnlySquare square) {
		final ReadOnlySquare previous = this.down;
		this.down = square;
		return previous;
	}

	public Collection<Integer> filledPositions() {
		return elements.values();
	}

	@Override
	public int hashCode() {
		int matrixHash = calculateMatrixHash();
		int result = 31;
		result *= (17 + matrixHash);
		return result;
	}

	public int calculateMatrixHash() {
		int matrixHash = 0;
		for(Element[] array : matrix) {
			matrixHash += Arrays.hashCode(array);
		}
		return matrixHash;
	}

	@Override
	public boolean equals(Object object) {
		if (this == object) {
			return true;
		}
		if (object != null
				&& object.getClass().equals(getClass())) {
			Square square = (Square) object;
			for(Element[] innerArray : matrix) {
				boolean arraysEquality = false;
				for(Element[] outerArray : square.matrix) {
					arraysEquality |= Arrays.equals(innerArray, outerArray);
				}
				if(!arraysEquality) {
					return false;
				}
			}
			return true;
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

	public static class Builder {

		private final Map<Element, Integer> elements;
		private final Element[][] matrix;
		private final int numberOfElementsOnSide;
		private final int numberOfElementsOnSquareSide;
		private final ReadWriteLock readWriteLock;

		public Builder(
				final GameFieldConfiguration configuration,
				final Element[][] elements,
				final int rowIndex,
				final int columnIndex,
				final ReadWriteLock readWriteLock) {
			this.readWriteLock = readWriteLock;
			this.numberOfElementsOnSide = configuration.getNumberOfElementsOnSide();
			this.numberOfElementsOnSquareSide = configuration.getNumberOfElementsOnSquareSide();
			isInputElementsEnoughLength(numberOfElementsOnSide, numberOfElementsOnSquareSide, elements);
			this.elements = new LinkedHashMap<>(numberOfElementsOnSide, 1.0f);
			this.matrix = new Element[numberOfElementsOnSquareSide][numberOfElementsOnSquareSide];
			populateMatrix(elements, rowIndex, columnIndex);
			populateElements(elements, rowIndex, columnIndex);
		}

		private void populateElements(Element[][] elements, int rowIndex, int columnIndex) {
			for (int i = 0; i < numberOfElementsOnSquareSide; i++) {
				for (int j = 0; j < numberOfElementsOnSquareSide; j++) {
					int columnIndexOffset = calculateColumnIndexOffset(columnIndex, j);
					int rowIndexOffset = calculateRowIndexOffset(rowIndex, i);
					Element e = elements[rowIndexOffset][columnIndexOffset];
					if (Element.EMPTY_ELEMENT.compareTo(e) != 0) {
						int elementPosition = calculateElementPosition(i, j);
						this.elements.put(e, elementPosition);
					}
				}
			}
		}

		private int calculateElementPosition(int i, int j) {
			return numberOfElementsOnSquareSide * i + j;
		}

		private int calculateRowIndexOffset(int rowIndex, int j) {
			return rowIndex * numberOfElementsOnSquareSide + j;
		}

		private int calculateColumnIndexOffset(int columnIndex, int i) {
			return columnIndex * numberOfElementsOnSquareSide + i;
		}

		private void populateMatrix(Element[][] elements, int rowIndex, int columnIndex) {
			for (int i = 0; i < numberOfElementsOnSquareSide; i++) {
				System.arraycopy(
						elements[calculateRowIndexOffset(rowIndex, i)],
						columnIndex * numberOfElementsOnSquareSide,
						matrix[i],
						0,
						numberOfElementsOnSquareSide
				);
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
					numberOfElementsOnSide,
					numberOfElementsOnSquareSide,
					matrix,
					elements,
					readWriteLock
			);
		}
	}
}
