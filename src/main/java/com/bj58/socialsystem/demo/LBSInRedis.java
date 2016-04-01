package com.bj58.socialsystem.demo;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.bj58.socialsystem.redis.JRedisClient;
import com.bj58.socialsystem.redis.JRedisProvider;
import com.bj58.socialsystem.utils.Geohash;
import com.bj58.socialsystem.utils.PosDistanceUtil;

/**
 * Reids 模拟 LBS
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年3月25日
 */
public class LBSInRedis {
	
	private static final int INDEX = 5;
	
	private static JRedisClient redisClient = JRedisProvider.getInstance().redis;
	private static Geohash geohash = new Geohash();
	private static final String KEY = "jerry_";
	
	/**
	 * 添加位置信息
	 * @param id
	 * @param latitude
	 * @param longitude
	 */
	public void add(long id,double longitude, double latitude) {
		//原数据处理
        //获取原Geohash
		String oldHashData = redisClient.hget(KEY + id, "geo");
		if(oldHashData != null && !"".equals(oldHashData)) {
			//原索引
			String oldIndexKey = oldHashData.substring(0, INDEX);
			//删除
			redisClient.srem(oldIndexKey, KEY + id);
		}
		
		//新数据处理
		redisClient.hset(KEY + id, "lo", longitude + "");
		redisClient.hset(KEY + id, "la", latitude + "");
		
		String hashData = geohash.encode(longitude,latitude);
		redisClient.hset(KEY + id, "geo", hashData);
		
		String indexKey = hashData.substring(0, INDEX);
		redisClient.sadd(KEY + indexKey, KEY + id);
	}
	
	
	/**
	 * 获取附近信息
	 * @param latitude
	 * @param longitude
	 */
	public Set<String> serach(double longitude, double latitude) {
		String hashData = geohash.encode(longitude, latitude);
		String indexKey = hashData.substring(0, INDEX);
		return redisClient.smembers(KEY + indexKey);
	}
	
	/**
	 * 获取附近用户并排序
	 * @param latitude
	 * @param longitude
	 */
	public List<Map<String, String>> serachAndSort(final double longitude, final double latitude) {
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();

		Set<String> set = this.serach(longitude, latitude);
		for(String s : set) {
			s = s.replace(KEY, "");
			Map<String, String> map = this.get(Long.valueOf(s));
			map.put("id", s);
			list.add(map);
		}
		
		Collections.sort(list, new Comparator<Map<String, String>>(){
			@Override
			public int compare(Map<String, String> o1, Map<String, String> o2) {
				 int d1 = PosDistanceUtil.getDistance(Double.parseDouble(o1.get("longitude")), Double.parseDouble(o1.get("latitude")),  longitude, latitude);
                 int d2 = PosDistanceUtil.getDistance(Double.parseDouble(o2.get("longitude")), Double.parseDouble(o2.get("latitude")), longitude, latitude);
                 return d1 - d2;
			}
		});
		
		return list;
	}
	
	/**
	 * 获取附近用户并排序再分页
	 * @param latitude
	 * @param longitude
	 */
	public List<Map<String, String>> serachAndSortAndPage( double longitude, double latitude, int pageNo, int pageSize) {
		List<Map<String, String>> tempList = this.serachAndSort(longitude, latitude);
		
		List<Map<String, String>> list = new ArrayList<Map<String, String>>();
		int start = (pageNo - 1) * pageSize;
		int maxEnd = start + pageSize;
		for(int i = start;i<tempList.size();i++){
			if(i < maxEnd){
				list.add(tempList.get(i));
			}
		}
		return list;
	}
	
	/**
	 * 获取信息
	 * @param userid
	 * @return
	 */
	public Map<String, String> get(long userid) {
		Map<String, String> map = new HashMap<String, String>();
		
		String longitude = redisClient.hget(KEY + userid, "lo");
		String latitude = redisClient.hget(KEY + userid, "la");
		
		map.put("longitude", longitude);
		map.put("latitude", latitude);
		
		return map;
	}
	
}
