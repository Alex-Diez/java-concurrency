package com.google.jam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Round {

	private final BlockingQueue<String> roundTasks;

	public Round(final Path pathToRoundFile)
			throws IOException {
		List<String> allLines = Files.readAllLines(pathToRoundFile);
		allLines.remove(0);
		this.roundTasks = new ArrayBlockingQueue<>(allLines.size(), true, allLines);
	}

	public String getNextTask() {
		return roundTasks.poll();
	}
}
