package com.google.jam;

public interface MultiThreadRoundResolver
		extends RoundResolver {

	void shutdownThreadPool();
}
