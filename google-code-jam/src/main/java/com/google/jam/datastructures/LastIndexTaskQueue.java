package com.google.jam.datastructures;

import java.util.concurrent.BlockingQueue;

public interface LastIndexTaskQueue<I, E>
		extends BlockingQueue<E> {

	I getLastRetrievedTaskIndex();
}
