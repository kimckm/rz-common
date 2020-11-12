package rz.cloud.pure;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class JucDemo {

	private static final ExecutorService executorService = new ThreadPoolExecutor(
		10,
		20,
		60L,
		TimeUnit.SECONDS,
		new ArrayBlockingQueue<>(15));

	private static long exe() {
		try {
			Thread.sleep(2000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		return System.currentTimeMillis();
	}

	public static void main(String[] args) throws ExecutionException, InterruptedException {
		long now = System.currentTimeMillis();

		List<Future<Long>> list = new ArrayList<>();

		for (int i = 0; i < 10; i++) {
			list.add(executorService.submit(JucDemo::exe));
		}

		for (Future<Long> f : list) {
			System.out.println("总耗时: " + (f.get() - now) + " ms");
		}
	}

}
