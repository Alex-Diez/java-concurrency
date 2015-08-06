package org.sudoku.slave.blocks;

import java.util.Arrays;
import java.util.Collection;

import org.sudoku.conf.SquareLocation;
import org.sudoku.elements.Square;

import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.runners.Parameterized.Parameters;
import static org.sudoku.TestsConstants.CONFIGURATION;
import static org.sudoku.TestsConstants.ELEMENTS;

@RunWith(Parameterized.class)
public class SubstitutableBlockTest {

    private final int rowIndex;
    private final int columnIndex;
    public SubstitutableBlockTest(final int columnIndex, final int rowIndex) {
        this.columnIndex = columnIndex;
        this.rowIndex = rowIndex;
    }

    @Parameters
    public static Collection conf() {
        int[] sizes = new int[] {3, 4, 5, 6, 7, 8, 9, 10, 11, 12};
        int resultSize = 0;
        for (int size : sizes) {
            resultSize += size * size;
        }
        int[][] result = new int[resultSize][2];
        for (int i = 0; i < sizes.length; i++) {
            final int size = sizes[i];
            for (int j = 0; j < size; j++) {
                for (int k = 0; k < size; k++) {
                    result[i][0] = j;
                    result[i][1] = k;
                }
            }
        }
        return Arrays.asList(result);
    }

    @Test
    public void testNonFilledSubstitutableBlockFilled() {
        SubstitutableBlock block = new SubstitutableBlock();
        Square nonFilledCenterSquare = new Square.Builder(CONFIGURATION, ELEMENTS, 0, 0).build();
        block.mergeInto(nonFilledCenterSquare, SquareLocation.CENTER);
        Assert.assertThat(block.isFilled(), is(false));
    }

    @Test
    public void testBuildSubstitutableBlock() {
        SubstitutableBlock block = new SubstitutableBlock();
        Square centerSquare = new Square.Builder(CONFIGURATION, ELEMENTS, 0, 0).build();
        block.mergeInto(centerSquare, SquareLocation.CENTER);
        Square upSquare = new Square.Builder(CONFIGURATION, ELEMENTS, 0, 2).build();
        block.mergeInto(upSquare, SquareLocation.UP);
        Square downSquare = new Square.Builder(CONFIGURATION, ELEMENTS, 0, 1).build();
        block.mergeInto(downSquare, SquareLocation.DOWN);
        Square leftSquare = new Square.Builder(CONFIGURATION, ELEMENTS, 2, 0).build();
        block.mergeInto(leftSquare, SquareLocation.LEFT);
        Square rightSquare = new Square.Builder(CONFIGURATION, ELEMENTS, 1, 0).build();
        block.mergeInto(rightSquare, SquareLocation.RIGHT);
        assertThat(block, is(notNullValue()));
    }
}
