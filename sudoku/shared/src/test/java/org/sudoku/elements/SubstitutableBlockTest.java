package org.sudoku.elements;

import java.util.Arrays;
import java.util.Collection;

import org.sudoku.TestsConstants;
import org.sudoku.conf.GameFieldConfiguration;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.fail;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SubstitutableBlockTest {

	@Parameters
	public static Collection conf() {
		int[] sizes = new int[] {3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
		int resultSize = 0;
		for(int size : sizes) {
			resultSize += size*size;
		}
		int[][] result = new int[resultSize][3];
		for(int i = 0; i < sizes.length; i++) {
			final int size = sizes[i];
			for(int j = 0; j < size; j++) {
				for(int k = 0; k < size; k++) {
					result[i][0] = (int)Math.pow(size, 4);
					result[i][1] = j;
					result[i][2] = k;
				}
			}
		}
		return Arrays.asList(result);
	}

	private final int columnIndex;
	private final int rowIndex;
	private final GameFieldConfiguration configuration;

	public SubstitutableBlockTest(final int numberOfElements, final int columnIndex, final int rowIndex) {
		this.configuration = new GameFieldConfiguration.Builder(numberOfElements).build();
		this.columnIndex = columnIndex;
		this.rowIndex = rowIndex;
	}

	private GameField gameField;

	@Before
	public void setUp() {
		gameField = new GameField.Builder(configuration, TestsConstants.ELEMENTS).build();
	}

	@Test
	public void testBuildSubstitutableBlock() {
		SubstitutableBlock block = new SubstitutableBlock.Builder(configuration, gameField, columnIndex, rowIndex).build();
		assertThat(block, is(notNullValue()));
	}

	@Test
	@Ignore
	public void testBuildFormGameField() {
		SubstitutableBlock block = null;
		SubstitutableBlock toTest = gameField.buildBlock(columnIndex, rowIndex);
		assertThat(toTest, is(block));
	}

	@Test
	@Ignore
	public void testExclusiveLock() {
		fail();
	}
}
