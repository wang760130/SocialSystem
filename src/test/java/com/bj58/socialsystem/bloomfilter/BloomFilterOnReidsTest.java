package com.bj58.socialsystem.bloomfilter;

import org.junit.Before;
import org.junit.Test;

import com.bj58.socialsystem.utils.StopWatch;

public class BloomFilterOnReidsTest {
	
	private BloomFilterOnReids bloomFilter = null;
	private String DEFAULT_KEY = "jerry_bloomfilter";
	
	private static long TEN_THOUSAND = 10000L;           // 一万
	private static long HUNDRED_THOUSAND = 100000L;      // 十万
	private static long MILLION = 1000000L;			     // 百万
	private static long TEN_MILLION = 10000000L;		 // 千万
	private static long HUNDRED_MILLION = 100000000L;    // 亿
	private static long BILLION = 1000000000L;  		 // 十亿
	private static long TEN_BILLION = 10000000000L;      // 百亿
	private static long HUNDRED_BILLION = 100000000000L; // 千亿
	
	@Before
	public void init() {
		bloomFilter = new BloomFilterOnReids(0.00000001f, (int)Math.pow(2, 31));
	}
	
	@Test
	public void delKey() {
		StopWatch  watch = new StopWatch();
		watch.start();
		long result = bloomFilter.delKey(DEFAULT_KEY);
		System.out.println(result);
		watch.stop();
		System.out.println(watch.prettyPrint());   // 43ms
	}
	
	@Test
	public void addTenThousand() {
		StopWatch  watch = new StopWatch();
		watch.start();
		for(int i = 0; i < TEN_THOUSAND; i++) {
			bloomFilter.add(DEFAULT_KEY, i+"");
		}
		watch.stop();
		System.out.println(watch.prettyPrint()); // 40822ms
	}
	
	@Test
	public void batchAddTenThousand() {
		StopWatch  watch = new StopWatch();
		watch.start();
		String[] value = new String[(int)(TEN_THOUSAND)];
		for(int i = 0; i < TEN_THOUSAND; i++) {
			value[i] = i + "";
		}
		bloomFilter.batchAdd(DEFAULT_KEY,value);
		watch.stop();
		System.out.println(watch.prettyPrint());   // 1935ms
	}
	
	@Test 
	public void batchAddMillion() {
		StopWatch  watch = new StopWatch();
		watch.start();
		String[] value = new String[(int)(MILLION)];
		for(int i = 0; i < MILLION; i++) {
			value[i] = i + "";
		}
		bloomFilter.batchAdd(DEFAULT_KEY,value);
		watch.stop();
		System.out.println(watch.prettyPrint());  // 12484ms
	}
	
	
	@Test
	public void isExit() {
		StopWatch  watch = new StopWatch();
		watch.start();
		boolean result = bloomFilter.isExit(DEFAULT_KEY, "9999");
		System.out.println(result);
		watch.stop();
		System.out.println(watch.prettyPrint());
	}
	
	
}
