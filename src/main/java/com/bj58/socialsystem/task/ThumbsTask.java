package com.bj58.socialsystem.task;

import com.bj58.socialsystem.entity.Thumbs;
import com.bj58.socialsystem.queue.TaskQueue;
import com.bj58.socialsystem.queue.TaskQueueManager;
import com.bj58.socialsystem.redis.JRedisClient;
import com.bj58.socialsystem.redis.JRedisProvider;
import com.bj58.socialsystem.utils.ObjectUtil;

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
		
		String task = taskQueue.popTask();
		
		try {
			Thumbs thumbs = (Thumbs)ObjectUtil.stringToObject(task);
			long dynid = thumbs.getDynid();
			thumbs.getType();
			
//			redisClient.hs
			
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		
	}

	
}
