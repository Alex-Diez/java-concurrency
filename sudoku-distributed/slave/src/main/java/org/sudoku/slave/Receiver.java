package org.sudoku.slave;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.sudoku.conf.SquareLocation;
import org.sudoku.elements.Square;
import org.sudoku.spi.net.NetworkManager;

public class Receiver {

	private final NetworkManager networkManager;
	private final ObjectMapper objectMapper;

	public Receiver(NetworkManager networkManager) {
		this.networkManager = networkManager;
		this.objectMapper = new ObjectMapper();
	}

	public Square receiveSquare()
			throws IOException {
		final String square = networkManager.readFromNetwork();
		return objectMapper.readValue(square, Square.class);
	}

	public SquareLocation receiveSquareLocation()
			throws IOException {
		final String squareLocation = networkManager.readFromNetwork();
		return objectMapper.readValue(squareLocation, SquareLocation.class);
	}
}
