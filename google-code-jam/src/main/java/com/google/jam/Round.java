package com.google.jam;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Round {

	private final BlockingQueue<String> roundTasks;

	public Round(final char roundSign, final String roundComplexity)
			throws IOException {
		final String pathToFile = buildPathToFile(roundSign, roundComplexity);
		final Path pathToRoundTasks = Paths.get(pathToFile);
		List<String> allLines;
		allLines = Files.readAllLines(pathToRoundTasks);
		allLines.remove(0);
		this.roundTasks = new ArrayBlockingQueue<>(allLines.size(), true, allLines);
	}

	public String buildPathToFile(final char roundSign, final String roundComplexity) {
		final String currentDirectory = System.getProperty("user.dir");
		final String fileSeparator = System.getProperty("file.separator");
		return currentDirectory + fileSeparator + "files" + fileSeparator + roundSign + '-' + roundComplexity + '-' + "practice.in";
	}

	public String getNextTask() {
		return "";
	}
}
