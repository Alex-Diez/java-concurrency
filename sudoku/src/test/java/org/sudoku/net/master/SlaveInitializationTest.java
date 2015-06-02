package org.sudoku.net.master;

import org.sudoku.conf.GameFieldConfiguration;
import org.sudoku.elements.Element;
import org.sudoku.elements.GameField;
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
		final GameFieldConfiguration configuration = new GameFieldConfiguration.Builder(9, 9).build();
		gameField = new GameField.Builder(configuration, ELEMENTS).build();
		networkManager = new MasterNetworkManager();
	}

	@Test
	public void testSlaveInitialization() {
		SlaveConfiguration configuration = slaveInitializer.initializeSlave(slaveIndex, gameField, networkManager);
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
