package org.sudoku.net.master;

import java.io.IOException;

import org.sudoku.elements.Square;
import org.sudoku.net.spi.NetworkManager;

public class MasterNetworkManager
		implements NetworkManager {

	@Override
	public Square readFromNetwork()
			throws IOException {
		return null;
	}

	@Override
	public void writeToNetwork(Square square)
			throws IOException {

	}

	@Override
	public void close()
			throws Exception {

	}
}
