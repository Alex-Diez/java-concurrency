package com.google.jam.benchmark.datastructures;

import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

import com.google.jam.datastructures.LastIndexTaskLinkedBlockingQueue;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Level;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.TearDown;
import org.openjdk.jmh.runner.Runner;
import org.openjdk.jmh.runner.RunnerException;
import org.openjdk.jmh.runner.options.Options;
import org.openjdk.jmh.runner.options.OptionsBuilder;

@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.NANOSECONDS)
public class BlockingQueuesBenchmarks {

    public static final Collection<Integer> DATA = Arrays.asList(
            1, 2, 3, 4, 5, 6, 7, 8, 9, 10,
            11, 12, 13, 14, 15, 16, 17, 18, 19, 20,
            21, 22, 23, 24, 25, 26, 27, 28, 29, 30,
            31, 32, 33, 34, 35, 36, 37, 38, 39, 40,
            41, 42, 43, 44, 45, 46, 47, 48, 49, 50,
            51, 52, 53, 54, 55, 56, 57, 85, 95, 60,
            61, 62, 63, 64, 65, 66, 67, 68, 69, 70,
            71, 72, 73, 74, 75, 76, 77, 78, 79, 80,
            81, 82, 83, 84, 85, 86, 87, 88, 89, 90,
            91, 92, 93, 94, 95, 96, 97, 98, 99, 100
    );

    @Param({"impl", "link", "array-fair", "array-unfair"})
    private String queueType;

    private BlockingQueue<Integer> queue;
    private Object result;
    private Object insertResult;
    private Object removeResult;

    @Setup(Level.Iteration)
    public void setUp()
            throws Exception {
        switch (queueType) {
            case "impl":
                queue = new LinkedBlockingQueue<>(DATA);
                break;
            case "link":
                queue = new LastIndexTaskLinkedBlockingQueue<>(DATA);
                break;
            case "array-fair":
                queue = new ArrayBlockingQueue<>(DATA.size(), true, DATA);
                break;
            default:
                queue = new ArrayBlockingQueue<>(DATA.size(), false, DATA);
                break;
        }
    }

    @TearDown(Level.Invocation)
    public void check()
            throws Exception {
        assert queue != null;
        assert !queue.isEmpty();
        assert result != null;
        assert insertResult != null;
        assert removeResult != null;
    }

    @Benchmark
    public void addRemoveElement()
            throws Exception {
        insertResult = queue.offer(1);
        removeResult = queue.poll();
    }

    @Benchmark
    public void addElement()
            throws Exception {
        result = queue.offer(1);
    }

    @Benchmark
    public void removeElement()
            throws Exception {
        result = queue.poll();
    }

    public static void main(String[] args)
            throws RunnerException {
        final Options options = new OptionsBuilder()
                .include(BlockingQueuesBenchmarks.class.getSimpleName())
                .warmupIterations(30)
                .measurementIterations(30)
                .forks(1)
                .jvmArgs("-ea")
                .build();
        new Runner(options).run();
    }
}
