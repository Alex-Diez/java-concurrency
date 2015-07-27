package com.google.jam;

import java.io.IOException;
import java.io.Writer;
import java.util.LinkedHashMap;
import java.util.Map;

public class ResultWriter {

	private final Map<Integer, Integer> resultToWrite;

	public ResultWriter(final Map<Integer, Integer> resultToWrite) {
		this.resultToWrite = new LinkedHashMap<>(resultToWrite);
	}

	public void writeTo(final Writer writer)
			throws IOException {
		for (int i = 0; i < resultToWrite.size(); i++) {
			int value = resultToWrite.get(i + 1);
			final String formattedOutput = String.format("Case #%d: %d", i + 1, value);
			writer.write(formattedOutput);
		}
		writer.flush();
	}
}
