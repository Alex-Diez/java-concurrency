package org.sudoku.spi.json;

import org.codehaus.jackson.JsonGenerator;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.JsonSerializer;
import org.codehaus.jackson.map.SerializerProvider;
import org.sudoku.elements.Square;

import java.io.IOException;

public class SquareSerializer
		extends JsonSerializer<Square> {

	@Override
	public void serialize(
			Square value,
			JsonGenerator jsonGenerator,
			SerializerProvider provider) throws IOException, JsonProcessingException {

	}
}
