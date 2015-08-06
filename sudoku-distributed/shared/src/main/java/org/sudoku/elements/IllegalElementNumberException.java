package org.sudoku.elements;

public class IllegalElementNumberException
        extends RuntimeException {

    public IllegalElementNumberException() {
        super();
    }

    public IllegalElementNumberException(String message) {
        super(message);
    }

    public IllegalElementNumberException(String message, Throwable cause) {
        super(message, cause);
    }

    public IllegalElementNumberException(Throwable cause) {
        super(cause);
    }
}
