package org.sudoku.slave;

import org.sudoku.elements.Square;
import org.sudoku.spi.net.NetworkManager;

public class Receiver {

	private final NetworkManager networkManager;

	public Receiver(NetworkManager networkManager) {
		this.networkManager = networkManager;
	}

	public Square receiveSquare() {

	}
}
