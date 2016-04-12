package com.bj58.socialsystem.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class FutureTest {
	
	public static void main(String[] args) {
		/*** 使用线程池 ***/
		ExecutorService executor = Executors.newCachedThreadPool();
		Task task = new Task();
		Future<Integer> result = executor.submit(task);
		executor.shutdown();
		
		
		/*** 使用线程 ***/
		/*Task task = new Task();
		FutrueTask<Integer> futureTask - new FutureTask<Integer>(task);
		Thread thread = new Thread(futureTask);
		thread.start();*/
		
		try {
			Thread.sleep(1000);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		
		System.out.println("主线程在执行任务");
		
		try {
			System.out.println(result.get());
		} catch (InterruptedException | ExecutionException e) {
			e.printStackTrace();
		}
	}
}

class Task implements Callable<Integer> {

	@Override
	public Integer call() throws Exception {
		System.out.println("子线程在进行计算");
		Thread.sleep(3000);
		int sum = 0;
		for(int i = 0; i < 100; i++) {
			sum += i;
		}
		return sum;
	}
	
}
