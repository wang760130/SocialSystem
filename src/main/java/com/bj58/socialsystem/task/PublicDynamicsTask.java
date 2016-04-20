package com.bj58.socialsystem.task;

import java.util.Date;

import com.bj58.socialsystem.common.RedisKey;
import com.bj58.socialsystem.dao.IDao;
import com.bj58.socialsystem.entity.Dynamics;
import com.bj58.socialsystem.queue.TaskQueue;
import com.bj58.socialsystem.queue.TaskQueueManager;
import com.bj58.socialsystem.redis.JRedisClient;
import com.bj58.socialsystem.redis.JRedisProvider;
import com.bj58.socialsystem.utils.DaoFactory;
import com.bj58.socialsystem.utils.LBSInRedisGeo;

/**
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年4月11日
 */
public class PublicDynamicsTask implements Task {
	
	private static JRedisClient redisClient = JRedisProvider.getInstance().redis;
	
	@SuppressWarnings("rawtypes")
	private IDao dynamicsDao = DaoFactory.getDao(Dynamics.class);
	
	@SuppressWarnings("unchecked")
	@Override
	public void execute() {
		TaskQueue taskQueue = TaskQueueManager.get(TaskQueueManager.PUBLIC_DYNAMICS_QUEUE);
		
		String str = taskQueue.popTask();
		
		try {
			String[] arr = str.split("\t");
			
			String userid = arr[0];
			String lon = arr[1];
			String lat = arr[2];
			String text = arr[3];
			
			Dynamics dynamics = new Dynamics();
			dynamics.setUserid(Long.valueOf(userid));
			dynamics.setCoords(lon + "," + lat); 
			dynamics.setText(text);
			dynamics.setAddtime(new Date());
			
			long id = dynamicsDao.insert(dynamics);

			String dynamicsKey = RedisKey.DYNAMICS + id;
			redisClient.hset(dynamicsKey, "userid", userid+"");
			redisClient.hset(dynamicsKey, "lon", lon);
			redisClient.hset(dynamicsKey, "lat", lat);
			redisClient.hset(dynamicsKey, "addtime", "");
			redisClient.hset(dynamicsKey, "text", text);
			
			LBSInRedisGeo geo = new LBSInRedisGeo();		
			geo.add(id+"", lon, lat);
			
			//添加动态好友
			
			
		} catch (Exception e) {
			// TODO
			e.printStackTrace();
		}
		
	}

}
