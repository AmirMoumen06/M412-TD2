/*
 * M412 2020-2021: distributed programming
 */

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.CompletionService;
import java.util.concurrent.ExecutorCompletionService;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class TestTask {

	public static void main(String[] args) {

		List<Callable<Integer>> taches = new ArrayList<>();
		// modifier les durées d'éxécution pour vérifier que l'on récupére bien
		// les résultats dans l'ordre des terminaisons
		Callable<Integer> tache1 = new Task(12);
		Callable<Integer> tache2 = new Task(5);
		Callable<Integer> tache3 = new Task(10);
		Callable<Integer> tache4 = new Task(1);

		taches.add(tache1);
		taches.add(tache2);
		taches.add(tache3);
		taches.add(tache4);

		ExecutorService executor = Executors.newFixedThreadPool(3);

		resoudre(executor, taches);
	}

	private static void resoudre(final ExecutorService executor, List<Callable<Integer>> taches) {

		// Le service de terminaison
		CompletionService<Integer> ecs = new ExecutorCompletionService<>(executor);

		// une liste de Future pour récupérer les résultats
		List<Future<Integer>> futures = new ArrayList<>();

		Integer res;
		try {
			// On soumet toutes les tâches à l'executor
			for (Callable<Integer> t : taches) {
				futures.add(ecs.submit(t));
			}
			for (int i = 0; i < taches.size(); ++i) {
				// On récupére le premier résultat disponible
				// sous la forme d'un Future avec take(). Puis l'appel
				// à get() nous donne le résultat du Callable.
				res = ecs.take().get();
				if (res != null) {
					// On affiche le résultat de la tâche
					System.out.println(res);
					//executor.shutdownNow(); // for test
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			executor.shutdown();
		}
	}
}
