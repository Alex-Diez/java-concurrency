package org.sudoku.game.elements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.locks.ReadWriteLock;

import org.sudoku.game.conf.GameFieldConfiguration;

public class Square
        implements ReadWriteSquare {

    private static final String ROW_SEPARATOR = " --- --- --- ";
    private static final String COLUMN_SEPARATOR = "|";

    private final Element[][] matrix;
    private final int numberOfElementsOnSquareSide;
    private final ReadWriteLock readWriteLock;

    private ReadOnlySquare left;
    private ReadOnlySquare right;
    private ReadOnlySquare up;
    private ReadOnlySquare down;

    private Square(
            final int numberOfElementsOnSquareSide,
            final Element[][] matrix,
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
        for (Element[] array : matrix) {
            for (Element e : array) {
                if (e.compareTo(element) == 0) {
                    return true;
                }
            }
        }
        return false;
    }

    @Override
    public boolean lockForWrite() {
        return readWriteLock.writeLock().tryLock();
    }

    @Override
    public void writeTo(int rowIndex, int columnIndex, Element element) {
        final Element e = new Element.Builder(
                numberOfElementsOnSquareSide * numberOfElementsOnSquareSide,
                element.value,
                new Position(rowIndex, columnIndex)
        ).build();
        matrix[rowIndex][columnIndex] = e;
    }

    @Override
    public void unlockAfterWrite() {
        readWriteLock.writeLock().unlock();
    }

    public boolean isFilled() {
        return !containsElement(Element.EMPTY_ELEMENT);
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

    @Override
    public String printableLine(int lineNumber) {
        StringBuilder sb = new StringBuilder();
        final int lineLength = matrix[lineNumber].length;
        for (int i = 0; i < lineLength; i++) {
            final Element e = matrix[lineNumber][i];
            sb.append(e);
            if (i != lineLength - 1) {
                sb.append(COLUMN_SEPARATOR);
            }
        }
        return sb.toString();
    }

    public Collection<Position> filledPositions() {
        Collection<Position> result = new ArrayList<>();
        for (Element[] array : matrix) {
            for (Element e : array) {
                if (e.compareTo(Element.EMPTY_ELEMENT) != 0) {
                    result.add(e.position);
                }
            }
        }
        return result;
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
        for (Element[] array : matrix) {
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
            for (Element[] innerArray : matrix) {
                boolean arraysEquality = false;
                for (Element[] outerArray : square.matrix) {
                    arraysEquality |= Arrays.equals(innerArray, outerArray);
                }
                if (!arraysEquality) {
                    return false;
                }
            }
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(ROW_SEPARATOR).append("\n");
        for (int i = 0; i < numberOfElementsOnSquareSide; i++) {
            sb.append(COLUMN_SEPARATOR).append(printableLine(i)).append(COLUMN_SEPARATOR).append("\n");
            if (i != numberOfElementsOnSquareSide - 1) {
                sb.append(ROW_SEPARATOR).append("\n");
            }
        }
        sb.append(ROW_SEPARATOR).append("\n");
        return sb.toString();
    }

    public static class Builder {

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
            this.numberOfElementsOnSide = configuration.getNumberOfElementsOnSide();
            this.numberOfElementsOnSquareSide = configuration.getNumberOfElementsOnSquareSide();
            isInputElementsEnoughLength(numberOfElementsOnSide, numberOfElementsOnSquareSide, elements);
            this.matrix = new Element[numberOfElementsOnSquareSide][numberOfElementsOnSquareSide];
            populateMatrix(elements, rowIndex, columnIndex);
            this.readWriteLock = readWriteLock;
        }

        private int calculateRowIndexOffset(int rowIndex, int j) {
            return rowIndex * numberOfElementsOnSquareSide + j;
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
                    numberOfElementsOnSquareSide,
                    matrix,
                    readWriteLock
            );
        }
    }
}
