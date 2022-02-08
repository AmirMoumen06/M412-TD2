/*
 * M412 2020-2021: distributed programming
 */

// use RunThread.java
// récupére les résultats sans Future

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * 
 * http://docs.oracle.com/javase/7/docs/api/java/util/concurrent/
 * ExecutorCompletionService.html
 * 
 */
public class MultiProcessingCallable {

	private void solve(Executor e, Collection<Callable<Integer>> worker)
			throws InterruptedException, ExecutionException {
		CompletionService<Integer> ecs = new ExecutorCompletionService<>(
				e);
		for (Callable<Integer> job : worker)
			ecs.submit(job);
		int n = worker.size();
		for (int i = 0; i < n; ++i) {
			Integer r = ecs.take().get();
			if (r != null)
				System.out.println("result: " + r);
		}
	}

	public static void main(String[] args) throws
			InterruptedException, ExecutionException {

		List<Callable<Integer>> worker = new ArrayList<>();
		for (int i = 0; i < 20; i++) {
			Callable<Integer> job = new RunThread(i, worker);
			worker.add(job);
		}

		int p = Runtime.getRuntime().availableProcessors();
		ExecutorService executor = Executors.newFixedThreadPool(p);

		MultiProcessingCallable solver = new MultiProcessingCallable();
		solver.solve(executor, worker);
		executor.shutdown();
		System.out.println("Finished!");
	}
}
