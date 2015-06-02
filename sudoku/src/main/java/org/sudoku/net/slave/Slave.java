package org.sudoku.net.slave;

import java.io.IOException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Slave {

	private static final Logger LOG = LoggerFactory.getLogger(Slave.class);

	private static final String DEFAULT_MASTER_HOST = "localhost";
	private static final int DEFAULT_MASTER_PORT = 10567;

	public static void main(String[] args) {
		try {
			Socket socket = new Socket(DEFAULT_MASTER_HOST, DEFAULT_MASTER_PORT);
			SlaveNetworkManager networkManager = new SlaveNetworkManager(socket);

		}
		catch(IOException e) {
			LOG.debug("IO while create connection to master");
		}
	}
}
