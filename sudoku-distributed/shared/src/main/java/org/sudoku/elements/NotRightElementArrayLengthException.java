package org.sudoku.elements;

public class NotRightElementArrayLengthException
		extends RuntimeException {

	public NotRightElementArrayLengthException() {
	}

	public NotRightElementArrayLengthException(String message) {
		super(message);
	}

	public NotRightElementArrayLengthException(String message, Throwable cause) {
		super(message, cause);
	}

	public NotRightElementArrayLengthException(Throwable cause) {
		super(cause);
	}
}
