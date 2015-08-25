package org.concurrent.datastructures.benchmark.states;

import java.math.BigInteger;
import java.security.SecureRandom;

import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;

@State(Scope.Thread)
public class DataState {

    private final SecureRandom random = new SecureRandom();

    private String datum;

    public String getDatum() {
        return datum;
    }

    @Setup(Level.Invocation)
    public void setUp()
            throws Exception {
        datum = new BigInteger(120, random).toString();
    }
}
