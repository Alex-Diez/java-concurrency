package org.sudoku.slave.conf;

import org.sudoku.conf.NodeConfiguration;

public class SlaveConfiguration
		implements NodeConfiguration {

	private final String host;
	private final int port;

	public SlaveConfiguration(String host, int port) {
		this.host = host;
		this.port = port;
	}

	public String getHost() {
		return host;
	}

	public int getControllerPort() {
		return port;
	}
}
