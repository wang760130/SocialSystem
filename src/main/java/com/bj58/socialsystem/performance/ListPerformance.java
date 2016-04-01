package com.bj58.socialsystem.performance;

import java.util.ArrayList;

public class ListPerformance {
	
	private static final long ONE_HUNDRED_MILLION = 100000000;
	
	/**
	 * String size 1 byte
	 * add execute time :1575 ms
	 * get execute time :0 ms
	 */
	public static void ListTest1 () {
		ArrayList<String> list = new ArrayList<String>();
		
		long startTime = System.currentTimeMillis();
		long totalMemory = Runtime.getRuntime().totalMemory();
		
		for(int i = 0; i < ONE_HUNDRED_MILLION; i ++) {
			list.add("a");
		}
		
		System.out.println("add execute time :" + (System.currentTimeMillis() - startTime) + "ms");
		System.out.println("add exceute memory : " + (Runtime.getRuntime().totalMemory() - totalMemory) / 1024 / 1024 + "mb" );
		
		
		startTime = System.currentTimeMillis();
		totalMemory = Runtime.getRuntime().totalMemory();
		for(int i = 0; i < ONE_HUNDRED_MILLION; i ++) {
			list.get(i);
		}
		System.out.println("get execute time :" + (System.currentTimeMillis() - startTime) + "ms");
		System.out.println("get exceute memory : " + (Runtime.getRuntime().totalMemory() - totalMemory) / 1024 / 1024 + "mb" );

	}
	
	/**
	 * String size 26 byte
	 * add execute time :1663ms
	 * get execute time :10ms
	 */
	public static void ListTest2 () {
		ArrayList<String> list = new ArrayList<String>();
		
		long startTime = System.currentTimeMillis();
		long totalMemory = Runtime.getRuntime().totalMemory();
		
		for(int i = 0; i < ONE_HUNDRED_MILLION; i ++) {
			list.add("abcdefghijklmnopqrstuvwxyz");
		}
		System.out.println("add execute time :" + (System.currentTimeMillis() - startTime) + "ms");
		System.out.println("add exceute memory : " + (Runtime.getRuntime().totalMemory() - totalMemory) / 1024 / 1024 + "mb" );

		startTime = System.currentTimeMillis();
		totalMemory = Runtime.getRuntime().totalMemory();
		for(int i = 0; i < ONE_HUNDRED_MILLION; i ++) {
			list.get(i);
		}
		System.out.println("get execute time :" + (System.currentTimeMillis() - startTime) + "ms");
		System.out.println("get exceute memory : " + (Runtime.getRuntime().totalMemory() - totalMemory) / 1024 / 1024 + "mb" );

	}
	
	public static void main(String[] args) {
//		ListPerformance.ListTest1();
		ListPerformance.ListTest2();
		
	}
	
}
