package org.sudoku.json;

import java.io.IOException;

import org.sudoku.elements.Square;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.sudoku.spi.json.ElementsJsonConstant.COLUMN_POSITION;
import static org.sudoku.spi.json.ElementsJsonConstant.ELEMENT;
import static org.sudoku.spi.json.ElementsJsonConstant.ELEMENT_VALUE;
import static org.sudoku.spi.json.ElementsJsonConstant.ROW_POSITION;
import static org.sudoku.spi.json.ElementsJsonConstant.SQUARE;

public class SquareSerializationDeserializationTest
        extends AbstractJsonSerializationDeserializationTest {

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

    private Square squareToTest;

    @Test
    public void testSquareSerialization()
            throws IOException {
        final String result = mapper.writeValueAsString(squareToTest);
        assertThat(result, is(JSON_REPRESENTATION));
    }

    @Test
    public void testSquareDeserialization()
            throws IOException {
        final Square result = mapper.readValue(JSON_REPRESENTATION, Square.class);
        assertThat(result, is(squareToTest));
    }

    @Test
    public void testSquareSerializationDeserialization()
            throws IOException {
        final String serializationResult = mapper.writeValueAsString(squareToTest);
        final Square temporary = mapper.readValue(serializationResult, Square.class);
        final String finalResult = mapper.writeValueAsString(temporary);
        assertThat(finalResult, is(serializationResult));
    }

    @Test
    public void testSquareDeserializationSerialization()
            throws IOException {
        final Square deserializationResult = mapper.readValue(JSON_REPRESENTATION, Square.class);
        final String temporary = mapper.writeValueAsString(deserializationResult);
        final Square finalResult = mapper.readValue(temporary, Square.class);
        assertThat(finalResult, is(deserializationResult));
    }
}
