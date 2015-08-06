package org.sudoku.json;

import org.codehaus.jackson.map.ObjectMapper;

import org.junit.Before;

public abstract class AbstractJsonSerializationDeserializationTest {

    protected ObjectMapper mapper;

    @Before
    public void setUp() {
        mapper = new ObjectMapper();
    }
}
