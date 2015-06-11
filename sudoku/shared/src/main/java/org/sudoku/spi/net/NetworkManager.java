package org.sudoku.spi.net;

import java.io.IOException;

import org.sudoku.elements.Square;

public interface NetworkManager
		extends AutoCloseable {

	String readFromNetwork()
			throws IOException;

	void writeToNetwork(String message)
			throws IOException;

}
