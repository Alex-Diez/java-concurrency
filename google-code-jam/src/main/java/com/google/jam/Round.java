package com.google.jam;

import com.google.jam.datastructures.LastIndexTaskQueue;

import java.util.Map;
import java.util.Map.Entry;
import java.util.Queue;
import java.util.function.Supplier;

public class Round {

    private final LastIndexTaskQueue<String> roundTasks;

    public Round(final LastIndexTaskQueue<String> roundTasks) {
        this.roundTasks = roundTasks;
    }

    public String getNextTask() {
        return roundTasks.poll();
    }

    public int numberOfTasks() {
        return roundTasks.size();
    }

    public boolean hasNextTask() {
        return roundTasks.peek() != null;
    }

    public int getLastTaskId() {
        return roundTasks.getLastRetrievedTaskIndex();
    }
}
