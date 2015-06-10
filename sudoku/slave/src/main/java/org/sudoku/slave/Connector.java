package org.sudoku.slave;

import org.sudoku.spi.net.NetworkManager;

import java.io.IOException;
import java.net.Socket;

public class Connector {

	private final int port;
	private final String host;

	public Connector(int port, String host) {
		this.port = port;
		this.host = host;
	}

	public NetworkManager connect()
			throws IOException {
		Socket socket = new Socket(host, port);
		return new SlaveNetworkManager(socket);
	}
}
