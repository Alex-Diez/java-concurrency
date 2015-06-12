package org.sudoku.slave.blocks;

import java.util.Arrays;
import java.util.Collection;

import org.sudoku.elements.Square;

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
		int[][] result = new int[resultSize][2];
		for(int i = 0; i < sizes.length; i++) {
			final int size = sizes[i];
			for(int j = 0; j < size; j++) {
				for(int k = 0; k < size; k++) {
					result[i][0] = j;
					result[i][1] = k;
				}
			}
		}
		return Arrays.asList(result);
	}

	private final int rowIndex;
	private final int columnIndex;

	public SubstitutableBlockTest(final int columnIndex, final int rowIndex) {
		this.columnIndex = columnIndex;
		this.rowIndex = rowIndex;
	}

	@Test
	@Ignore
	public void testBuildSubstitutableBlock() {
		fail();
		SubstitutableBlock block = new SubstitutableBlock();
		//build squares and merge into block
		assertThat(block, is(notNullValue()));
	}
}
