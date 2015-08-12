package com.google.jam;

import java.util.Map;
import java.util.Queue;
import java.util.function.Supplier;

public class Round {

    private final Queue<Map.Entry<Integer, String>> roundTasks;

    public Round(final Queue<Map.Entry<Integer, String>> roundTasks) {
        this.roundTasks = roundTasks;
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
