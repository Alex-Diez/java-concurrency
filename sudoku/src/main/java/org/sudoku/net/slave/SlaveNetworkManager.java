package org.sudoku.net.slave;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.sudoku.game.elements.Square;
import org.sudoku.net.spi.NetworkManager;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class SlaveNetworkManager
		implements NetworkManager {

	private static final Logger LOG = LoggerFactory.getLogger(SlaveNetworkManager.class);

	private final ObjectInput input;
	private final ObjectOutput output;
	private final Socket socket;

	public SlaveNetworkManager(Socket socket) {
		this.socket = socket;
		try {
			input = new ObjectInputStream(socket.getInputStream());
			output = new ObjectOutputStream(socket.getOutputStream());
		}
		catch(IOException e) {
			LOG.debug("IO exception when acquire networks streams");
			throw new RuntimeException(e);
		}
	}

	public void writeToNetwork(Square square)
			throws IOException {
		output.writeObject(square);
		output.flush();
	}

	public Square readFromNetwork()
			throws IOException {
		try {
			return (Square) input.readObject();
		}
		catch(ClassNotFoundException e) {
			throw new RuntimeException(e);
		}
	}

	@Override
	public void close()
			throws Exception {
		input.close();
		output.close();
		socket.close();
	}
}
