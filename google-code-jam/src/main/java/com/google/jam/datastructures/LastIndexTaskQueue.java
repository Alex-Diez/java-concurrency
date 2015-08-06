package com.google.jam.datastructures;

import java.util.concurrent.BlockingQueue;

public interface LastIndexTaskQueue<E>
        extends BlockingQueue<E> {

    int getLastRetrievedTaskIndex();
}
