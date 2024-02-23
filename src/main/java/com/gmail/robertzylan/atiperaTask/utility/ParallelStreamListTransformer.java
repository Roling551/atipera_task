package com.gmail.robertzylan.atiperaTask.utility;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.function.Function;
import java.util.stream.Collectors;

import java.util.concurrent.TimeUnit;

@Component
public class ParallelStreamListTransformer implements ListTransformer{

	@Override
	public <T, R> List<R> transform(List<T> list, Function<T, R> transformFunction) {
		List<R> resultList = new ArrayList<>();

		ExecutorService executor = Executors.newVirtualThreadPerTaskExecutor();

		for (T item : list) {
			executor.submit(() -> {
				R result = transformFunction.apply(item);
				synchronized (resultList) {
					resultList.add(result);
				}
			});
		}

		executor.shutdown();
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.SECONDS);
		} catch (InterruptedException e) {
			Thread.currentThread().interrupt();
		}

		return resultList;
	}
}
