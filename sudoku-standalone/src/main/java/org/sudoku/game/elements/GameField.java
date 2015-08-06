package org.sudoku.game.elements;

import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.sudoku.game.conf.GameFieldConfiguration;
import org.sudoku.game.strategies.ResolverByBlock;
import org.sudoku.game.strategies.StrategiesFactory;

public class GameField
        implements StrategiesFactory {

    private static final String ROW_SEPARATOR = " --- --- --- --- --- --- --- --- --- ";
    private static final char COLUMN_SEPARATOR = '|';

    private final ReadWriteSquare[][] squares;
    private final int numberOfElementsOnSide;
    private final int numberOfElementsOnSquareSide;
    private final int numberOfSquaresOnSide;

    private GameField(
            final GameFieldConfiguration configuration,
            final ReadWriteSquare[][] squares) {
        this.squares = squares;
        this.numberOfElementsOnSide = configuration.getNumberOfElementsOnSide();
        this.numberOfElementsOnSquareSide = configuration.getNumberOfElementsOnSquareSide();
        this.numberOfSquaresOnSide = configuration.getNumberOfSquaresOnSide();
    }

    @Override
    public Runnable buildBlockResolver(final int rowIndex, final int columnIndex) {
        final ReadWriteSquare square = determinateCenterSquareForSubstitutableBlock(columnIndex, rowIndex);
        return new ResolverByBlock(square, numberOfElementsOnSide, numberOfElementsOnSquareSide);
    }

    private ReadWriteSquare determinateCenterSquareForSubstitutableBlock(final int rowIndex, final int columnIndex) {
        final int centerRowIndex = (rowIndex + numberOfSquaresOnSide) % numberOfSquaresOnSide;
        final int centerColumnIndex = (columnIndex + numberOfSquaresOnSide) % numberOfSquaresOnSide;
        return squares[centerRowIndex][centerColumnIndex];
    }

    public boolean isFilled() {
        for (ReadOnlySquare[] rows : squares) {
            for (ReadOnlySquare square : rows) {
                if (!square.isFilled()) {
                    return false;
                }
            }
        }
        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder(ROW_SEPARATOR + "\n");
        for (int i = 0; i < numberOfElementsOnSide; i++) {
            sb.append(COLUMN_SEPARATOR);
            final int squareColumnIndex = calculateSquareColumnIndex(i);
            for (int j = 0; j < numberOfElementsOnSide; j++) {
                final int squareRowIndex = calculateSquareRowIndex(j);
                sb.append(
                        squares[squareColumnIndex][squareRowIndex]
                                .readFrom(
                                        calculateColumnOffset(i),
                                        calculateRowOffset(j)
                                )
                ).append(COLUMN_SEPARATOR);
            }
            sb.append("\n" + ROW_SEPARATOR + "\n");
        }
        return sb.toString();
    }

    private int calculateRowOffset(final int rowIndex) {
        return rowIndex % numberOfElementsOnSquareSide;
    }

    private int calculateColumnOffset(final int columnIndex) {
        return columnIndex % numberOfElementsOnSquareSide;
    }

    private int calculateSquareRowIndex(final int rowIndex) {
        return rowIndex / numberOfSquaresOnSide;
    }

    private int calculateSquareColumnIndex(final int columnIndex) {
        return columnIndex / numberOfSquaresOnSide;
    }

    public static class Builder {

        private final ReadWriteSquare[][] squares;
        private GameFieldConfiguration configuration;

        public Builder(final GameFieldConfiguration configuration, final Element[][] elements) {
            isInputElementsEnoughLength(configuration, elements);
            this.configuration = configuration;
            final int numberOfSquaresOnSide = configuration.getNumberOfSquaresOnSide();
            squares = new ReadWriteSquare[numberOfSquaresOnSide][numberOfSquaresOnSide];
            populateSquares(configuration, elements, numberOfSquaresOnSide);
            setAroundSquares(numberOfSquaresOnSide);
        }

        private void setAroundSquares(final int numberOfSquaresOnSide) {
            for (int rowIndex = 0; rowIndex < numberOfSquaresOnSide; rowIndex++) {
                for (int columnIndex = 0; columnIndex < numberOfSquaresOnSide; columnIndex++) {
                    ReadWriteSquare square = squares[rowIndex][columnIndex];
                    setLeftSquareFor(square, rowIndex, columnIndex, numberOfSquaresOnSide);
                    setRightSquareFor(square, rowIndex, columnIndex, numberOfSquaresOnSide);
                    setUpperSquareFor(square, rowIndex, columnIndex, numberOfSquaresOnSide);
                    setLowerSquareFor(square, rowIndex, columnIndex, numberOfSquaresOnSide);
                }
            }
        }

        private void setLowerSquareFor(
                final ReadWriteSquare square,
                final int rowIndex,
                final int columnIndex,
                final int numberOfSquaresOnSide) {
            final int lowerRowIndex = (rowIndex + 1 + numberOfSquaresOnSide) % numberOfSquaresOnSide;
            final int lowerColumnIndex = (columnIndex + numberOfSquaresOnSide) % numberOfSquaresOnSide;
            square.setLower(squares[lowerRowIndex][lowerColumnIndex]);
        }

        private void setUpperSquareFor(
                final ReadWriteSquare square,
                final int rowIndex,
                final int columnIndex,
                final int numberOfSquaresOnSide) {
            final int upperRowIndex = (rowIndex - 1 + numberOfSquaresOnSide) % numberOfSquaresOnSide;
            final int upperColumnIndex = (columnIndex + numberOfSquaresOnSide) % numberOfSquaresOnSide;
            square.setUpper(squares[upperRowIndex][upperColumnIndex]);
        }

        private void setRightSquareFor(
                final ReadWriteSquare square,
                final int rowIndex,
                final int columnIndex,
                final int numberOfSquaresOnSide) {
            final int rightRowIndex = (rowIndex + numberOfSquaresOnSide) % numberOfSquaresOnSide;
            final int rightColumnIndex = (columnIndex + 1 + numberOfSquaresOnSide) % numberOfSquaresOnSide;
            square.setRight(squares[rightRowIndex][rightColumnIndex]);
        }

        private void setLeftSquareFor(
                final ReadWriteSquare square,
                final int rowIndex,
                final int columnIndex,
                final int numberOfSquaresOnSide) {
            final int leftRowIndex = (rowIndex + numberOfSquaresOnSide) % numberOfSquaresOnSide;
            final int leftColumnIndex = (columnIndex - 1 + numberOfSquaresOnSide) % numberOfSquaresOnSide;
            square.setLeft(squares[leftRowIndex][leftColumnIndex]);
        }

        public void populateSquares(
                final GameFieldConfiguration configuration,
                final Element[][] elements,
                final int numberOfSquaresOnSide) {
            for (int rowIndex = 0; rowIndex < numberOfSquaresOnSide; rowIndex++) {
                for (int columnIndex = 0; columnIndex < numberOfSquaresOnSide; columnIndex++) {
                    squares[rowIndex][columnIndex] = new Square.Builder(
                            configuration,
                            elements,
                            rowIndex,
                            columnIndex,
                            new ReentrantReadWriteLock()
                    ).build();
                }
            }
        }

        private void isInputElementsEnoughLength(
                final GameFieldConfiguration configuration,
                final Element[][] elements) {
            final int numberOfElementsOnSide = configuration.getNumberOfElementsOnSide();
            if (elements.length != numberOfElementsOnSide
                    && !isSubArraysHaveProperlyLengths(numberOfElementsOnSide, elements)) {
                throw new NotRightElementArrayLengthException(
                        String.format(
                                "Game field can contains only %s elements on side but is %s elements on side",
                                configuration.getNumberOfElementsOnSquareSide(),
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

        public GameField build() {
            return new GameField(configuration, squares);
        }
    }
}
