package org.sudoku.game.strategies;

import org.junit.Ignore;
import org.junit.Test;
import org.sudoku.game.elements.SubstitutableBlock;

public class ResolverByBlockTest {

	@Test
	public void testCreateResolverBlock()
			throws Exception {
		new ResolverByBlock(new SubstitutableBlock[3]);
	}
}
