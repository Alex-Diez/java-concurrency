package org.sudoku.json;

import java.io.IOException;
import java.util.Collection;
import java.util.EnumSet;

import org.sudoku.conf.SlaveStatus;
import org.sudoku.conf.SquareLocation;

import org.junit.Test;

import static org.hamcrest.CoreMatchers.is;
import static org.junit.Assert.assertThat;
import static org.junit.runners.Parameterized.Parameters;

public class SlaveStatusSerializationDeserializationTest
        extends AbstractJsonSerializationDeserializationTest {

    private static final String JSON_REPRESENTATION = "{ slave_status : %s }";

    private final SlaveStatus slaveStatusToTest;

    public SlaveStatusSerializationDeserializationTest(SlaveStatus slaveStatusToTest) {
        this.slaveStatusToTest = slaveStatusToTest;
    }

    @Parameters
    public Collection<SlaveStatus> configuration() {
        return EnumSet.allOf(SlaveStatus.class);
    }

    @Test
    public void testSlaveStatusSerialization()
            throws IOException {
        final String result = mapper.writeValueAsString(slaveStatusToTest);
        final String expectedResult = String.format(JSON_REPRESENTATION, slaveStatusToTest.toString());
        assertThat(result, is(expectedResult));
    }

    @Test
    public void testSlaveStatusDeserialization()
            throws IOException {
        final SquareLocation result = mapper.readValue(
                String.format(JSON_REPRESENTATION, slaveStatusToTest.toString()),
                SquareLocation.class
        );
        assertThat(result, is(slaveStatusToTest));
    }

    @Test
    public void testSlaveStatusSerializationDeserialization()
            throws IOException {
        final String serializationResult = mapper.writeValueAsString(slaveStatusToTest);
        final SquareLocation temporary = mapper.readValue(serializationResult, SquareLocation.class);
        final String finalResult = mapper.writeValueAsString(temporary);
        assertThat(finalResult, is(serializationResult));
    }

    @Test
    public void testSlaveStatusDeserializationSerialization()
            throws IOException {
        final SquareLocation deserializationResult = mapper.readValue(
                String.format(
                        JSON_REPRESENTATION,
                        slaveStatusToTest.toString()),
                SquareLocation.class
        );
        final String temporary = mapper.writeValueAsString(deserializationResult);
        final SquareLocation finalResult = mapper.readValue(temporary, SquareLocation.class);
        assertThat(finalResult, is(deserializationResult));
    }
}
