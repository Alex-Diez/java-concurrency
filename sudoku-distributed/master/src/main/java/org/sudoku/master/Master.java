package org.sudoku.master;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

import org.sudoku.conf.GameFieldConfiguration;
import org.sudoku.elements.Element;
import org.sudoku.elements.GameField;
import org.sudoku.spi.net.NetworkManager;

public class Master {

    private static final Element[][] ELEMENTS = new Element[9][9];

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
        for (int i = 0; i < numberOfSquares; i++) {
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
}
