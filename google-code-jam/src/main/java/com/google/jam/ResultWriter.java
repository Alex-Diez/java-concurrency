package com.google.jam;

import java.io.IOException;
import java.io.Writer;
import java.util.Map;
import java.util.TreeMap;

public class ResultWriter {

    private final Map<Integer, String> resultToWrite;

    public ResultWriter(final Map<Integer, String> resultToWrite) {
        this.resultToWrite = new TreeMap<>(resultToWrite);
    }

    public void writeTo(final Writer writer)
            throws IOException {
        for (Map.Entry<Integer, String> entry : resultToWrite.entrySet()) {
            final String formattedOutput = String.format("Case #%d: %s%n", entry.getKey(), entry.getValue());
            writer.write(formattedOutput);
        }
        writer.flush();
    }
}
