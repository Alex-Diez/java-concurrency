package org.sudoku.master;

import java.io.IOException;

import org.sudoku.elements.Square;
import org.sudoku.spi.net.NetworkManager;

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
