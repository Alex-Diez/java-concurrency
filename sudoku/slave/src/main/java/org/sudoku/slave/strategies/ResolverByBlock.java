package org.sudoku.slave.strategies;

import org.sudoku.conf.SlaveStatus;
import org.sudoku.elements.Element;
import org.sudoku.slave.blocks.SubstitutableBlock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ResolverByBlock {

	private static final Logger LOG = LoggerFactory.getLogger(ResolverByBlock.class);

	private final SubstitutableBlock block;

	public ResolverByBlock(final SubstitutableBlock block) {
		this.block = block;
	}

	public SlaveStatus execute() {
		LOG.info("Block before resolution\n{}", block);
		Integer position = -1;
		Element elementToSubstitute = Element.EMPTY_ELEMENT;
		for(int i = 0; i < Element.POSSIBLE_ELEMENTS.length && position == -1; i++) {
			position = block.positionToSubstitution(Element.POSSIBLE_ELEMENTS[i]);
			if(position != -1) {
				elementToSubstitute = Element.POSSIBLE_ELEMENTS[i];
			}
		}
		SlaveStatus status;
		if(position != -1) {
			block.putIn(elementToSubstitute, position);
			if(block.isFilled()) {
				status = SlaveStatus.DONE;
			}
			else {
				status = SlaveStatus.SERVE;
			}
		}
		else {
			status = SlaveStatus.IDLE;
		}
		LOG.info("Block after resolution\n{}", block);
		return status;
	}

}
