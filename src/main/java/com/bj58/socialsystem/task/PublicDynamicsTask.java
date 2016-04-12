package com.bj58.socialsystem.task;

import com.bj58.socialsystem.common.RedisKey;
import com.bj58.socialsystem.entity.Dynamics;
import com.bj58.socialsystem.queue.TaskQueue;
import com.bj58.socialsystem.queue.TaskQueueManager;
import com.bj58.socialsystem.redis.JRedisClient;
import com.bj58.socialsystem.redis.JRedisProvider;
import com.bj58.socialsystem.utils.ObjectUtil;

/**
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年4月11日
 */
public class PublicDynamicsTask implements Task {
	private static JRedisClient redisClient = JRedisProvider.getInstance().redis;
	
	@Override
	public void execute() {
		TaskQueue taskQueue = TaskQueueManager.get(TaskQueueManager.PUBLIC_DYNAMICS_QUEUE);
		
		String task = taskQueue.popTask();
		
		try {
			Dynamics dynamics = (Dynamics)ObjectUtil.stringToObject(task);
			
			long userid = dynamics.getUserid();
			String lon = dynamics.getLon();
			String lat = dynamics.getLat();
			String text = dynamics.getText();
			
			// ...
			long id = 0L;

			String dynamicsKey = RedisKey.DYNAMICS + id;
			redisClient.hset(dynamicsKey, "userid", userid+"");
			redisClient.hset(dynamicsKey, "lon", lon);
			redisClient.hset(dynamicsKey, "lat", lat);
			
		} catch (Exception e) {
			// TODO
		}
		
	}

}
