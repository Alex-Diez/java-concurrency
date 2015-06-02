package org.sudoku.net.master;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.sudoku.game.conf.GameFieldConfiguration;
import org.sudoku.game.elements.Element;
import org.sudoku.game.elements.GameField;
import org.sudoku.net.spi.NetworkManager;

public class Master {

	public static void main(String[] args)
			throws Exception {
		Master master = new Master();
		master.start();
	}

	private void start()
			throws Exception {
		final GameFieldConfiguration configuration = buildConfiguration();
		final ServerSocket socket = new ServerSocket();
		initializeSlaves(configuration, socket);
	}

	private void initializeSlaves(final GameFieldConfiguration configuration, final ServerSocket serverSocket)
			throws IOException {
		final int numberOfSquares = configuration.getNumberOfSquares();
		//todo hardcoded
		final GameField gameField = new GameField.Builder(configuration, ELEMENTS).build();
		for(int i = 0; i < numberOfSquares; i++) {
			initializeSlave(i, gameField, serverSocket);
		}
	}

	private void initializeSlave(final int index, final GameField gameField, final ServerSocket serverSocket)
			throws IOException {
		//todo run slave
		final Socket slaveSocket = serverSocket.accept();
		final NetworkManager networkManager = new MasterNetworkManager();
		networkManager.writeToNetwork(gameField.retrieveSquare(index));
	}

	private GameFieldConfiguration buildConfiguration() {
		return null;
	}



	private static final Element[][] ELEMENTS;

	static {
		ELEMENTS = new Element[][] {
				{
						new Element.Builder(8).build(),
						new Element.Builder(4).build(),
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						new Element.Builder(5).build(),
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						new Element.Builder(2).build()
				},
				{
						Element.EMPTY_ELEMENT,
						new Element.Builder(5).build(),
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						new Element.Builder(9).build(),
						Element.EMPTY_ELEMENT,
						new Element.Builder(3).build(),
						new Element.Builder(6).build()
				},
				{
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						new Element.Builder(2).build(),
						new Element.Builder(8).build(),
						Element.EMPTY_ELEMENT,
						new Element.Builder(6).build(),
						new Element.Builder(4).build(),
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT
				},
				{
						Element.EMPTY_ELEMENT,
						new Element.Builder(6).build(),
						new Element.Builder(8).build(),
						Element.EMPTY_ELEMENT,
						new Element.Builder(1).build(),
						Element.EMPTY_ELEMENT,
						new Element.Builder(5).build(),
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT
				},
				{
						new Element.Builder(9).build(),
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						new Element.Builder(5).build(),
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						new Element.Builder(4).build()
				},
				{
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						new Element.Builder(3).build(),
						Element.EMPTY_ELEMENT,
						new Element.Builder(6).build(),
						Element.EMPTY_ELEMENT,
						new Element.Builder(7).build(),
						new Element.Builder(1).build(),
						Element.EMPTY_ELEMENT,
				},
				{
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						new Element.Builder(9).build(),
						new Element.Builder(1).build(),
						Element.EMPTY_ELEMENT,
						new Element.Builder(7).build(),
						new Element.Builder(3).build(),
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT
				},
				{
						new Element.Builder(2).build(),
						new Element.Builder(1).build(),
						Element.EMPTY_ELEMENT,
						new Element.Builder(4).build(),
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						new Element.Builder(8).build(),
						Element.EMPTY_ELEMENT
				},
				{
						new Element.Builder(7).build(),
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						new Element.Builder(8).build(),
						Element.EMPTY_ELEMENT,
						Element.EMPTY_ELEMENT,
						new Element.Builder(2).build(),
						new Element.Builder(9).build()
				}
		};
	}
}
