package org.sudoku.json;

import java.io.IOException;
import java.util.Collection;
import java.util.EnumSet;

import org.codehaus.jackson.JsonGenerationException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.sudoku.conf.SquareLocation;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.Parameters;

@RunWith(Parameterized.class)
public class SquareLocationSerializationDeserializationTest {

	private static final String JSON_REPRESENTATION = "{ square_location : %s }";

	private final SquareLocation locationToTest;

	public SquareLocationSerializationDeserializationTest(SquareLocation locationToTest) {
		this.locationToTest = locationToTest;
	}

	@Parameters
	public Collection<SquareLocation> configuration() {
		return EnumSet.allOf(SquareLocation.class);
	}

	private ObjectMapper mapper;

	@Test
	public void testSerialization()
			throws IOException, JsonGenerationException, JsonMappingException {
		final String result = mapper.writeValueAsString(locationToTest);
		final String expectedResult = String.format(JSON_REPRESENTATION, locationToTest.toString());
		assertThat(result, is(JSON_REPRESENTATION));
	}

	@Test
	public void testDeserialization()
			throws IOException, JsonGenerationException, JsonMappingException {
		final SquareLocation result = mapper.readValue(
				String.format(JSON_REPRESENTATION, locationToTest.toString()),
				SquareLocation.class
		);
		assertThat(result, is(locationToTest));
	}

	@Test
	public void testSerializationDeserialization()
			throws IOException, JsonGenerationException, JsonMappingException {
		final String serializationResult = mapper.writeValueAsString(locationToTest);
		final SquareLocation temporary = mapper.readValue(serializationResult, SquareLocation.class);
		final String finalResult = mapper.writeValueAsString(temporary);
		assertThat(finalResult, is(serializationResult));
	}

	@Test
	public void testDeserializationSerialization()
			throws IOException, JsonGenerationException, JsonMappingException {
		final SquareLocation deserializationResult = mapper.readValue(
				String.format(
						JSON_REPRESENTATION,
						locationToTest.toString()),
				SquareLocation.class
		);
		final String temporary = mapper.writeValueAsString(deserializationResult);
		final SquareLocation finalResult = mapper.readValue(temporary, SquareLocation.class);
		assertThat(finalResult, is(deserializationResult));
	}
}
