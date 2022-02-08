/*
 * M412 2020-2021: distributed programming
 */

import java.util.concurrent.Callable;

public class Task implements Callable<Integer> {

	private final int sleepTime;

	Task(int n) {
		sleepTime = n;
	}

	@Override
	public Integer call() throws Exception {
		Thread.sleep(1000 * sleepTime);
		return sleepTime;
	}
}
