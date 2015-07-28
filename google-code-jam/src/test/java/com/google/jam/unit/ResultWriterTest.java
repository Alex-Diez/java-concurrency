package com.google.jam.unit;

import java.io.BufferedReader;
import java.io.PipedReader;
import java.io.PipedWriter;
import java.util.HashMap;
import java.util.Map;

import com.google.jam.ResultWriter;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;

public class ResultWriterTest {

	@Test
	public void testCreateResultWriter()
			throws Exception {
		new ResultWriter(new HashMap<>());
	}

	@Test
	public void testCheckWrittenResult()
			throws Exception {
		Map<Integer, Integer> resultToWrite = new HashMap<>();
		resultToWrite.put(1, 2);
		ResultWriter resultWriter = new ResultWriter(resultToWrite);
		PipedWriter writer = new PipedWriter();
		BufferedReader reader = new BufferedReader(new PipedReader(writer));
		resultWriter.writeTo(writer);
		String resultString = reader.readLine();
		assertThat("Case #1: 2", is(equalTo(resultString)));
	}
}
