package org.sudoku.slave.blocks;

public class SquaresNotOnTheSamePositionsException
        extends RuntimeException {

    public SquaresNotOnTheSamePositionsException(String message) {
        super(message);
    }

    public SquaresNotOnTheSamePositionsException(Throwable cause) {
        super(cause);
    }
}
