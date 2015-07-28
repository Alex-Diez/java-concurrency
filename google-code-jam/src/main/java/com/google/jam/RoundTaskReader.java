package com.google.jam;

import java.io.IOException;
import java.nio.file.Path;

public class RoundTaskReader {


	private final Path pathToFile;

	public RoundTaskReader(final Path pathToFile) {
		this.pathToFile = pathToFile;
	}

	public Round applyCreator(final RoundCreator roundCreator)
			throws IOException {
		return new Round(pathToFile);
	}
}
