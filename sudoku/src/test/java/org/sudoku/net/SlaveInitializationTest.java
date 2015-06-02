package org.sudoku.net;

import org.sudoku.TestsConstants;
import org.sudoku.conf.GameFieldConfiguration;
import org.sudoku.elements.GameField;
import org.sudoku.net.master.MasterNetworkManager;
import org.sudoku.net.master.SlaveConfiguration;
import org.sudoku.net.master.SlaveInitializer;
import org.sudoku.net.spi.NetworkManager;

import org.junit.Test;
import org.junit.Before;

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
		SlaveConfiguration configuration = slaveInitializer.initializeSlave(slaveIndex, gameField, networkManager);
	}
}
