package com.google.jam;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;

public class Round {

	private final BlockingQueue<String> roundTasks;

	public Round(final int queueLength, final Collection<String> tasks) {
		roundTasks = new ArrayBlockingQueue<>(queueLength, true, tasks);
	}

	public String getNextTask() {
		return roundTasks.poll();
	}
}
