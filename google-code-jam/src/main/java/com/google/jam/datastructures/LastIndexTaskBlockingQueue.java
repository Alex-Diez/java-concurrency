package com.google.jam.datastructures;

import java.util.concurrent.BlockingQueue;

public interface LastIndexTaskBlockingQueue<E>
        extends BlockingQueue<E>,
                LastIndexTaskQueue<E> {
}
