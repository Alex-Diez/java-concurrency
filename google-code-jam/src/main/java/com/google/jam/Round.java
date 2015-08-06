package com.google.jam;

import java.util.LinkedList;
import java.util.Map;
import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class Round {

    private final Queue<Map.Entry<Integer, String>> roundTasks;

    public Round(final boolean parallelism, final Map<Integer, String> tasks) {
        roundTasks = parallelism
                ? new LinkedBlockingQueue<>(tasks.entrySet())
                : new LinkedList<>(tasks.entrySet());
    }

    public Map.Entry<Integer, String> getNextTask() {
        return roundTasks.poll();
    }

    public int numberOfTasks() {
        return roundTasks.size();
    }

    public boolean hasNextTask() {
        return roundTasks.peek() != null;
    }
}
