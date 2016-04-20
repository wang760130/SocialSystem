package com.bj58.socialsystem.task;

import com.bj58.socialsystem.common.RedisKey;
import com.bj58.socialsystem.queue.TaskQueue;
import com.bj58.socialsystem.queue.TaskQueueManager;
import com.bj58.socialsystem.redis.JRedisClient;
import com.bj58.socialsystem.redis.JRedisProvider;

/**
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年4月12日
 */
public class ThumbsTask implements Task{

	private static JRedisClient redisClient = JRedisProvider.getInstance().redis;

	@Override
	public void execute() {
		TaskQueue taskQueue = TaskQueueManager.get(TaskQueueManager.PUBLIC_DYNAMICS_QUEUE);
		
		String str = taskQueue.popTask();
		
		try {
			String[] arr = str.split("\t");
			
//			String userid = arr[0];
			String dynid = arr[1];
			String type = arr[2]; // 1:喜欢 2:不喜欢
		
			if(type.equals("1")) {
				redisClient.hincrBy(RedisKey.DYNAMICS + dynid, "like", 1);
			} else if(type.equals("2")) {
				redisClient.hincrBy(RedisKey.DYNAMICS + dynid, "unlike", 1);
			}
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	
}
