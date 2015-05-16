package org.fairytale;

public class Action {

	private final long duration;

	public Action(long duration) {
		this.duration = duration;
	}

	public void doAction()
			throws InterruptedException {
		Thread.sleep(duration);
	}
}
