package org.concurrent.datastructures;

import java.util.concurrent.BlockingQueue;

public interface LastIndexTaskBlockingQueue<E>
        extends BlockingQueue<E>,
                LastIndexTaskQueue<E> {
}
