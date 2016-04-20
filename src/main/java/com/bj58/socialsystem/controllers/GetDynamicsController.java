package com.bj58.socialsystem.controllers;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import redis.clients.jedis.GeoRadiusResponse;

import com.bj58.socialsystem.common.RedisKey;
import com.bj58.socialsystem.entity.Dynamics;
import com.bj58.socialsystem.redis.JRedisClient;
import com.bj58.socialsystem.redis.JRedisProvider;
import com.bj58.socialsystem.utils.LBSInRedisGeo;
import com.bj58.wf.mvc.ActionResult;
import com.bj58.wf.mvc.MvcController;
import com.bj58.wf.mvc.annotation.Path;

/**
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年4月1日
 */
public class GetDynamicsController extends MvcController {
	
	private static JRedisClient redisClient = JRedisProvider.getInstance().redis;

	
	@Path("/dynamics/get")
	public ActionResult getDynamics() {
		// 1:好友动态［按时间排序］2:大厅动态［按距离排序］3:大厅动态［综合排序（被点赞数，距离，发布时间
		String orderby = "1";
		String userid = "";
		String lon = "";
		String lat = "";
		int pageSize = 15;
		int pageNO = 1;
		
		List<Dynamics> dynamicsList = new ArrayList<Dynamics>();
		
		if(orderby.equals("1")) {
			
			double min = 0;
			double max = 0;
			int offset = 0;
			int count = 0;
			Set<String> firends = redisClient.zrangeByScore(RedisKey.FIRENDS + userid, min, max, offset, count);
			// TODO
		} else if(orderby.equals("2")) {
			Dynamics dynamics = null;
			LBSInRedisGeo geo = new LBSInRedisGeo();
			List<GeoRadiusResponse> list = geo.serach(Double.valueOf(lon), Double.valueOf(lat), 3000);
			System.out.println("size = " + list.size());
			
			for(GeoRadiusResponse geoRadiusResponse : list) {
				dynamics = new Dynamics();
				double latitude = geoRadiusResponse.getCoordinate().getLatitude();
				double longitude = geoRadiusResponse.getCoordinate().getLongitude();
				double distance = geoRadiusResponse.getDistance();
				String member = geoRadiusResponse.getMemberByString();
				System.out.println(member + " - " + distance + " - " + latitude + " - " + longitude);
				
				long id = Long.valueOf(member);
				
				String dynamicsKey = RedisKey.DYNAMICS + id;
				String duserid = redisClient.hget(dynamicsKey, "userid");
				String addtime = redisClient.hget(dynamicsKey, "addtime");
				String text = redisClient.hget(dynamicsKey, "text");
				
				dynamics.setId(id);
				dynamics.setDistance(distance);
				dynamics.setUserid(Long.valueOf(duserid));
				dynamics.setAddtimeStr(addtime);
				dynamics.setText(text);
				dynamicsList.add(dynamics);
			}
			
		} else if(orderby.equals("3")) {
			// ......
		}
		
		return null;
	}
	
}
