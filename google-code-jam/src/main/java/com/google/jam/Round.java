package com.google.jam;

import java.util.Collection;
import java.util.LinkedList;
import java.util.Queue;

public class Round {

    private final Queue<String> roundTasks;
    private final int numberOfTasks;

    public Round(final Collection<String> strings) {
        this.roundTasks = new LinkedList<>(strings);
        this.numberOfTasks = roundTasks.size();
    }

    public String getNextTask() {
        return roundTasks.poll();
    }

    public int numberOfTasks() {
        return numberOfTasks;
    }

    public boolean hasNextTask() {
        return roundTasks.peek() != null;
    }
}
