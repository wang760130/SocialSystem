package com.bj58.socialsystem.bloomfilter;

import java.util.List;
import java.util.Map;

import org.junit.Test;

import com.bj58.socialsystem.demo.LBSInRedis;
import com.bj58.socialsystem.utils.PosDistanceUtil;
import com.bj58.socialsystem.utils.StopWatch;
import com.bj58.socialsystem.utils.ThreadLocalRandom;

public class LBSInRedisTest {
	
	@Test
	public void addTest() {
		StopWatch  watch = new StopWatch();
		watch.start();
		
		ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
		LBSInRedis lbs = new LBSInRedis();
		
		for(int i = 0; i < 1000; i++) {
			double longitude = (double) threadLocalRandom.nextLong(103983192, 104160690) / (double)1000000;
			double latitude = (double) threadLocalRandom.nextLong(30597730, 30726786) / (double)1000000;
			
			lbs.add(i, longitude, latitude);
		}
		
		watch.stop();
		System.out.println(watch.prettyPrint()); // 12484ms
	}
	
	@Test
	public void serachTest() {
		ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
		LBSInRedis lbs = new LBSInRedis();
		
		double currentLongitude = (double)threadLocalRandom.nextLong(103983192, 104160690) / (double) 1000000;
		double currentLatitude = (double)threadLocalRandom.nextLong(30597730, 30726786) / (double) 1000000;
		
		List<Map<String, String>> list = lbs.serachAndSort(currentLongitude,currentLatitude);
		System.out.println(currentLongitude + " - " + currentLatitude + " - " + list.size());
		for(Map<String, String> l  : list) {
			String id = l.get("id");
			System.out.println(id + " - " + l.get("longitude") + " - " + l.get("latitude") + " - " + PosDistanceUtil.getDistance(Double.parseDouble(l.get("longitude")), Double.parseDouble(l.get("latitude")),currentLongitude, currentLatitude));
		}
		
		System.out.println("paging....");
		List<Map<String, String>> pageList = lbs.serachAndSortAndPage(currentLongitude, currentLatitude, 2, 10);
		for(Map<String, String> l  : pageList) {
			String id = l.get("id");
			System.out.println(id + " - " + l.get("longitude") + " - " + l.get("latitude") + " - " + PosDistanceUtil.getDistance(Double.parseDouble(l.get("longitude")), Double.parseDouble(l.get("latitude")), currentLongitude, currentLatitude));
		}
	}
	
}
