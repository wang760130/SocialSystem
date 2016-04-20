package com.bj58.socialsystem.bloomfilter;

import java.util.List;

import org.junit.Test;

import redis.clients.jedis.GeoRadiusResponse;

import com.bj58.socialsystem.utils.LBSInRedisGeo;
import com.bj58.socialsystem.utils.StopWatch;
import com.bj58.socialsystem.utils.ThreadLocalRandom;

public class LBSInRedisGeoTest {
	
	@Test
	public void addTest() {
		StopWatch  watch = new StopWatch();
		watch.start();
		
		ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
		LBSInRedisGeo lbs = new LBSInRedisGeo();
		
		for(int i = 0; i < 1000; i++) {
			double longitude = (double) threadLocalRandom.nextLong(103983192, 104160690) / (double)1000000;
			double latitude = (double) threadLocalRandom.nextLong(30597730, 30726786) / (double)1000000;
			
			
			lbs.add(String.valueOf(i), longitude, latitude);
		}
		
		watch.stop();
		System.out.println(watch.prettyPrint());  // 2669 ms
	}
	
	@Test
	public void serachTest() {
		
//		ThreadLocalRandom threadLocalRandom = ThreadLocalRandom.current();
		LBSInRedisGeo lbs = new LBSInRedisGeo();
		
		//double currentLongitude = (double)threadLocalRandom.nextLong(103983192, 104160690) / (double) 1000000;
		//double currentLatitude = (double)threadLocalRandom.nextLong(30597730, 30726786) / (double) 1000000;
		
//		System.out.println(currentLongitude + "-" + currentLatitude);
		List<GeoRadiusResponse> geoRadiusResponseList = lbs.serach(104.060028,30.710133,3000);
		
		System.out.println("size = " + geoRadiusResponseList.size());
		
		for(GeoRadiusResponse geoRadiusResponse : geoRadiusResponseList) {
			double latitude = geoRadiusResponse.getCoordinate().getLatitude();
			double longitude = geoRadiusResponse.getCoordinate().getLongitude();
			double distance = geoRadiusResponse.getDistance();
			String member = geoRadiusResponse.getMemberByString();
			System.out.println(member + " - " + distance + " - " + latitude + " - " + longitude);
		}
		
	}
	
}
