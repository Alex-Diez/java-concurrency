package org.sudoku.sudoku.net;

import java.io.IOException;
import java.net.Socket;

import org.sudoku.TestsConstants;
import org.sudoku.conf.GameFieldConfiguration;
import org.sudoku.conf.NodeConfiguration;
import org.sudoku.elements.GameField;
import org.sudoku.elements.Square;
import org.sudoku.master.MasterNetworkManager;
import org.sudoku.master.SlaveInitializer;
import org.sudoku.slave.SlaveNetworkManager;
import org.sudoku.spi.net.NetworkManager;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;

public class NetworkMessagingTest {

    private SlaveInitializer slaveInitializer;
    private int slaveIndex;
    private GameField gameField;
    private NetworkManager networkManager;

    @Before
    public void setUp() {
        slaveInitializer = new SlaveInitializer();
        slaveIndex = 1;
        final GameFieldConfiguration configuration = new GameFieldConfiguration.Builder(9).build();
        gameField = new GameField.Builder(configuration, TestsConstants.ELEMENTS).build();
        networkManager = new MasterNetworkManager();
    }

    @Test
    @Ignore
    public void testMasterToSlaveSendMessage()
            throws IOException {
        final NodeConfiguration configuration = slaveInitializer.initializeSlave(slaveIndex, gameField, networkManager);
        final int slaveControllerPort = configuration.getControllerPort();
        final String slaveHost = configuration.getHost();
        final Socket socket = new Socket(slaveHost, slaveControllerPort);
        final NetworkManager manager = new SlaveNetworkManager(socket);
        final Square toWrite = buildFrom(gameField, 0, 0);
        networkManager.writeToNetwork(toWrite);
        final Square readSquare = manager.readFromNetwork();
        assertThat(readSquare, is(toWrite));
    }

    private Square buildFrom(final GameField gameField, final int columnIndex, int rowIndex) {
        return null;
    }

    @Test
    @Ignore
    public void testSlaveToMasterSendMessage()
            throws IOException {
        final NodeConfiguration configuration = slaveInitializer.initializeSlave(slaveIndex, gameField, networkManager);
        final int slaveControllerPort = configuration.getControllerPort();
        final String slaveHost = configuration.getHost();
        final Socket socket = new Socket(slaveHost, slaveControllerPort);
        final NetworkManager manager = new SlaveNetworkManager(socket);
        final Square toWrite = buildFrom(gameField, 0, 0);
        manager.writeToNetwork(toWrite);
        final Square readSquare = networkManager.readFromNetwork();
        assertThat(readSquare, is(toWrite));
    }

    @Test
    @Ignore
    public void testSlaveDoesNotKnowHowToResolveWaitDataFromMaster()
            throws IOException {
        final NodeConfiguration configuration = slaveInitializer.initializeSlave(slaveIndex, gameField, networkManager);
        final int slaveControllerPort = configuration.getControllerPort();
        final String slaveHost = configuration.getHost();
        final Socket socket = new Socket(slaveHost, slaveControllerPort);
        final NetworkManager manager = new SlaveNetworkManager(socket);
        final Square toWrite = buildFrom(gameField, 0, 0);
        manager.writeToNetwork(toWrite);
        final Square readSquare = networkManager.readFromNetwork();
        assertThat(readSquare, is(toWrite));
    }
}
