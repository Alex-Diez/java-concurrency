package com.google.jam;

import java.nio.file.Path;
import java.nio.file.Paths;

public class RoundPathBuilder {

	private final String location;
	private final char roundLetter;
	private final String complexity;
	private final String roundType;

	public RoundPathBuilder(
			final String location,
			final char roundLetter,
			final String complexity,
			final String roundType) {
		this.location = location;
		this.roundLetter = roundLetter;
		this.complexity = complexity;
		this.roundType = roundType;
	}

	public Path build() {
		final String currentDirectory = System.getProperty("user.dir");
		final String fileSeparator = System.getProperty("file.separator");
		return Paths.get(
				currentDirectory + fileSeparator +
						"src" + fileSeparator +
						location + fileSeparator +
						"files" + fileSeparator +
						roundLetter + '-' + complexity + '-' + roundType + ".in"
		);
	}
}
