package org.sudoku.slave;

import java.io.IOException;
import java.io.ObjectInput;
import java.io.ObjectInputStream;
import java.io.ObjectOutput;
import java.io.ObjectOutputStream;
import java.net.Socket;

import org.sudoku.elements.Square;
import org.sudoku.spi.net.NetworkManager;

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
			LOG.debug("IO exception when acquire networks streams", e);
			throw new RuntimeException(e);
		}
	}

	@Override
	public void writeToNetwork(String square)
			throws IOException {
		output.writeUTF(square);
		output.flush();
	}

	@Override
	public String readFromNetwork()
			throws IOException {
		return input.readUTF();
	}

	@Override
	public void close()
			throws Exception {
		input.close();
		output.close();
		socket.close();
	}
}
