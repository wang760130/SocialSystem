package com.bj58.socialsystem.demo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import redis.clients.jedis.GeoCoordinate;
import redis.clients.jedis.GeoRadiusResponse;
import redis.clients.jedis.GeoUnit;
import redis.clients.jedis.params.geo.GeoRadiusParam;

import com.bj58.socialsystem.redis.JRedisClient;
import com.bj58.socialsystem.redis.JRedisProvider;

/**
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年3月31日
 */
public class LBSInRedisGeo {
	
	private static JRedisClient redisClient = JRedisProvider.getInstance().redis;
	
	private static final String KEY = "jerry_";	
	
	public void add(String id, double longitude, double latitude) {
		Map<String, GeoCoordinate> memberCoordinateMap = new HashMap<String, GeoCoordinate>();
		GeoCoordinate geoCoordinate = new GeoCoordinate(longitude,latitude);
		memberCoordinateMap.put(id, geoCoordinate);
		redisClient.geoadd(KEY + "geo", memberCoordinateMap);
	}
	
	public List<GeoRadiusResponse> serach(double longitude, double latitude, double radius) {
		return redisClient.georadius(KEY + "geo", longitude, latitude, radius, GeoUnit.M, GeoRadiusParam.geoRadiusParam().withDist().withCoord().sortAscending());
	}

}
