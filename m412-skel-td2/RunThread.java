/*
 * M412 2020-2021: distributed programming
 */

// used by MultiProcessingCallable.java

import java.util.List;
import java.util.concurrent.Callable;

public class RunThread implements Callable<Integer> {
	private int rank;
	private List<Callable<Integer>> worker; // tous les autres Callable

	RunThread(int i, List<Callable<Integer>> w) {
		rank = i;
		worker = w;
	}

	@Override
	public Integer call() {
		System.out.println("rank: " + rank);
		for (Callable<Integer> job : worker){
			System.out.println(job.toString());
		}
		return rank;
	}

}
