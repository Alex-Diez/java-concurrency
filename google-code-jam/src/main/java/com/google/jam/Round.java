package com.google.jam;

import java.util.Map;
import java.util.Queue;
import java.util.function.Supplier;

public class Round {

    private final Queue<Map.Entry<Integer, String>> roundTasks;

    public Round(final Supplier<Queue<Map.Entry<Integer, String>>> roundTasksSupplier) {
        this.roundTasks = roundTasksSupplier.get();
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
