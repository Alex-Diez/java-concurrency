package org.sudoku.slave;

import java.io.IOException;
import java.net.Socket;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.sudoku.spi.net.NetworkManager;

public class Slave {

	private static final Logger LOG = LoggerFactory.getLogger(Slave.class);

	private static final String DEFAULT_MASTER_HOST = "localhost";
	private static final int DEFAULT_MASTER_PORT = 10567;

	public static void main(String[] args) {
		try {
			Connector connector = new Connector(DEFAULT_MASTER_PORT, DEFAULT_MASTER_HOST);
			NetworkManager networkManager = connector.connect();

		}
		catch(IOException e) {
			LOG.debug("IO while create connection to master");
		}
	}
}
