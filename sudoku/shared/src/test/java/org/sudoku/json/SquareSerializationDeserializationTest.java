package org.sudoku.json;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;
import org.sudoku.conf.GameFieldConfiguration;
import org.sudoku.elements.GameField;
import org.sudoku.elements.Square;

import static org.junit.Assert.fail;
import static org.sudoku.TestsConstants.CONFIGURATION;
import static org.sudoku.TestsConstants.ELEMENTS;
import static org.sudoku.spi.json.ElementsJsonConstant.SQUARE;
import static org.sudoku.spi.json.ElementsJsonConstant.ELEMENT;
import static org.sudoku.spi.json.ElementsJsonConstant.COLUMN_POSITION;
import static org.sudoku.spi.json.ElementsJsonConstant.ROW_POSITION;
import static org.sudoku.spi.json.ElementsJsonConstant.ELEMENT_VALUE;

public class SquareSerializationDeserializationTest {

	private static final String jsonRepresentation = "{" +
			"	" + SQUARE + " : {" +
			"		" + ELEMENT + " : {" +
			"			" + ROW_POSITION + " : 0," +
			"			" + COLUMN_POSITION + " : 0," +
			"			" + ELEMENT_VALUE + " : 8" +
			"		}," +
			"		" + ELEMENT + " : {" +
			"			"+ROW_POSITION+" : 0," +
			"			"+COLUMN_POSITION+" : 1," +
			"			"+ ELEMENT_VALUE + " : 4" +
			"		}," +
			"		" + ELEMENT + " : {" +
			"			"+ROW_POSITION+" : 0," +
			"			"+COLUMN_POSITION+" : 2," +
			"			"+ ELEMENT_VALUE + " : 0" +
			"		}," +
			"		" + ELEMENT + " : {" +
			"			"+ROW_POSITION+" : 1," +
			"			"+COLUMN_POSITION+" : 0," +
			"			"+ ELEMENT_VALUE + " : 0" +
			"		}," +
			"		" + ELEMENT + " : {" +
			"			"+ROW_POSITION+" : 1," +
			"			"+COLUMN_POSITION+" : 1," +
			"			"+ ELEMENT_VALUE + " : 5" +
			"		}," +
			"		" + ELEMENT + " : {" +
			"			"+ROW_POSITION+" : 1," +
			"			"+COLUMN_POSITION+" : 2," +
			"			"+ ELEMENT_VALUE + " : 0" +
			"		}," +
			"		" + ELEMENT + " : {" +
			"			"+ROW_POSITION+" : 2," +
			"			"+COLUMN_POSITION+" : 0," +
			"			"+ ELEMENT_VALUE + " : 0" +
			"		}," +
			"		" + ELEMENT + " : {" +
			"			"+ROW_POSITION+" : 2," +
			"			"+COLUMN_POSITION+" : 1," +
			"			"+ ELEMENT_VALUE + " : 0" +
			"		}," +
			"		" + ELEMENT + " : {" +
			"			"+ROW_POSITION+" : 2," +
			"			"+COLUMN_POSITION+" : 2," +
			"			"+ ELEMENT_VALUE + " : 2" +
			"		}" +
			"	}" +
			"}";

	@Test
	@Ignore
	public void testSerialization() {
		fail();
	}

	@Test
	@Ignore
	public void testDeserialization() {
		fail();
	}

	@Test
	@Ignore
	public void testSerializationDeserialization() {
		fail();
	}
}
