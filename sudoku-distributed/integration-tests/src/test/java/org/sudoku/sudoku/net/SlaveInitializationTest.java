package org.sudoku.sudoku.net;

import org.sudoku.TestsConstants;
import org.sudoku.conf.GameFieldConfiguration;
import org.sudoku.conf.NodeConfiguration;
import org.sudoku.elements.GameField;
import org.sudoku.master.MasterNetworkManager;
import org.sudoku.master.SlaveInitializer;
import org.sudoku.spi.net.NetworkManager;

import org.junit.Before;
import org.junit.Test;

public class SlaveInitializationTest {

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
    public void testSlaveInitialization() {
        NodeConfiguration configuration = slaveInitializer.initializeSlave(slaveIndex, gameField, networkManager);
    }
}
