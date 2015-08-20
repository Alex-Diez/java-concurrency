package com.google.jam.benchmark.datastructures.states;

import java.util.Random;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class DataState {

    private final Random random = new Random(System.nanoTime());

    private int datum;

    public int getDatum() {
        return datum;
    }

    @Setup(Level.Invocation)
    public void setUp()
            throws Exception {
        datum = random.nextInt();
    }
}
