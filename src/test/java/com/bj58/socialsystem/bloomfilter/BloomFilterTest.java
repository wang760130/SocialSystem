package com.bj58.socialsystem.bloomfilter;

import org.junit.Test;

public class BloomFilterTest {
	
	@Test
	public void test1 () {
		BloomFilter filter = new BloomFilter();
		filter.init("D:\\My Workspace\\SocialSystem\\text\\BloomFilter.txt");
		
		filter.add("live");
		System.out.println(filter.isExit("live"));
		
		
		filter.delete("live");
		System.out.println(filter.isExit("live"));
	}
	
}
