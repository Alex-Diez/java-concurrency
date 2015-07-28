package com.google.jam;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Round {

	private final Queue<String> roundTasks;

	private final boolean parallelism;

	public Round(final boolean parallelism, final int queueLength, final Collection<String> tasks) {
		this.parallelism = parallelism;
		roundTasks = parallelism ? new LinkedBlockingQueue<>(tasks) : new ArrayDeque<>(tasks);
	}

	public boolean inParallel() {
		return parallelism;
	}

	public String getNextTask() {
		return roundTasks.poll();
	}

	public int numberOfTasks() {
		return roundTasks.size();
	}
}
