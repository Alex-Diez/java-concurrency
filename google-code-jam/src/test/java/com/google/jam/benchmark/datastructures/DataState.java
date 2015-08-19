package com.google.jam.benchmark.datastructures;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Random;

@State(Scope.Thread)
public class DataState {

    private static final Logger LOGGER = LoggerFactory.getLogger(DataState.class);

    private final Random random = new Random(System.nanoTime());

    int datum;
    int counter;

    @Setup(Level.Invocation)
    public void setUp()
            throws Exception {
        datum = random.nextInt();
        counter = 0;
    }

    @TearDown(Level.Invocation)
    public void checkUp()
            throws Exception {
        LOGGER.info("Iteration number is {}", counter);
    }
}
