package org.sudoku.json;

import java.io.IOException;

import org.junit.Before;
import org.junit.Ignore;
import org.junit.Test;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.sudoku.elements.GameField;
import org.sudoku.elements.Square;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;
import static org.sudoku.TestsConstants.CONFIGURATION;
import static org.sudoku.TestsConstants.ELEMENTS;
import static org.sudoku.spi.json.ElementsJsonConstant.SQUARE;
import static org.sudoku.spi.json.ElementsJsonConstant.ELEMENT;
import static org.sudoku.spi.json.ElementsJsonConstant.COLUMN_POSITION;
import static org.sudoku.spi.json.ElementsJsonConstant.ROW_POSITION;
import static org.sudoku.spi.json.ElementsJsonConstant.ELEMENT_VALUE;

public class SquareSerializationDeserializationTest {

	private static final String JSON_REPRESENTATION = "{" +
			"	" + SQUARE + " : {" +
			"		" + ELEMENT + " : {" +
			"			" + ROW_POSITION + " : 0," +
			"			" + COLUMN_POSITION + " : 0," +
			"			" + ELEMENT_VALUE + " : 8" +
			"		}," +
			"		" + ELEMENT + " : {" +
			"			" + ROW_POSITION + " : 0," +
			"			" + COLUMN_POSITION + " : 1," +
			"			" + ELEMENT_VALUE + " : 4" +
			"		}," +
			"		" + ELEMENT + " : {" +
			"			" + ROW_POSITION + " : 0," +
			"			" + COLUMN_POSITION + " : 2," +
			"			" + ELEMENT_VALUE + " : 0" +
			"		}," +
			"		" + ELEMENT + " : {" +
			"			" + ROW_POSITION + " : 1," +
			"			" + COLUMN_POSITION + " : 0," +
			"			" + ELEMENT_VALUE + " : 0" +
			"		}," +
			"		" + ELEMENT + " : {" +
			"			" + ROW_POSITION + " : 1," +
			"			" + COLUMN_POSITION + " : 1," +
			"			" + ELEMENT_VALUE + " : 5" +
			"		}," +
			"		" + ELEMENT + " : {" +
			"			" + ROW_POSITION + " : 1," +
			"			" + COLUMN_POSITION + " : 2," +
			"			" + ELEMENT_VALUE + " : 0" +
			"		}," +
			"		" + ELEMENT + " : {" +
			"			" + ROW_POSITION + " : 2," +
			"			" + COLUMN_POSITION + " : 0," +
			"			" + ELEMENT_VALUE + " : 0" +
			"		}," +
			"		" + ELEMENT + " : {" +
			"			" + ROW_POSITION + " : 2," +
			"			" + COLUMN_POSITION + " : 1," +
			"			" + ELEMENT_VALUE + " : 0" +
			"		}," +
			"		" + ELEMENT + " : {" +
			"			" + ROW_POSITION + " : 2," +
			"			" + COLUMN_POSITION + " : 2," +
			"			" + ELEMENT_VALUE + " : 2" +
			"		}" +
			"	}" +
			"}";

	private ObjectMapper mapper;
	private Square squareToTest;

	@Before
	public void setUp() {
		final GameField gameField = new GameField.Builder(CONFIGURATION, ELEMENTS).build();
		squareToTest = gameField.buildSquareAt(0, 0);
		mapper = new ObjectMapper();
	}

	@Test
	@Ignore
	public void testSerialization()
			throws IOException, JsonGenerationException, JsonMappingException {
		final String result = mapper.writeValueAsString(squareToTest);
		assertThat(result, is(JSON_REPRESENTATION));
	}

	@Test
	@Ignore
	public void testDeserialization()
			throws IOException, JsonGenerationException, JsonMappingException {
		final Square result = mapper.readValue(JSON_REPRESENTATION, Square.class);
		assertThat(result, is(squareToTest));
	}

	@Test
	@Ignore
	public void testSerializationDeserialization()
			throws IOException, JsonGenerationException, JsonMappingException {
		final String serializationResult = mapper.writeValueAsString(squareToTest);
		final Square temporary = mapper.readValue(serializationResult, Square.class);
		final String finalResult = mapper.writeValueAsString(temporary);
		assertThat(finalResult, is(serializationResult));
	}

	@Test
	@Ignore
	public void testDeserializationSerialization()
			throws IOException, JsonGenerationException, JsonMappingException {
		final Square deserializationResult = mapper.readValue(JSON_REPRESENTATION, Square.class);
		final String temporary = mapper.writeValueAsString(deserializationResult);
		final Square finalResult = mapper.readValue(temporary, Square.class);
		assertThat(finalResult, is(deserializationResult));
	}
}
