package com.google.jam;

import java.util.ArrayDeque;
import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Round {

	private final Queue<String> roundTasks;

	public Round(final boolean parallelism, final Collection<String> tasks) {
		roundTasks = parallelism ? new LinkedBlockingQueue<>(tasks) : new LinkedList<>(tasks);
	}

	public String getNextTask() {
		return roundTasks.poll();
	}

	public int numberOfTasks() {
		return roundTasks.size();
	}
}
