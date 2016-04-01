package com.bj58.socialsystem.bloomfilter;

public class BloomFilterTest {
	
	public void test1 () {
		BloomFilter filter = new BloomFilter();
		filter.init("D:\\My Workspace\\SocialSystem\\text\\BloomFilter.txt");
		
		filter.add("live");
		System.out.println(filter.isExit("live"));
		
		
		filter.delete("live");
		System.out.println(filter.isExit("live"));
	}
	
}
