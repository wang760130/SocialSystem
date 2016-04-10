package com.bj58.socialsystem.queue;

import com.bj58.socialsystem.redis.JRedisClient;
import com.bj58.socialsystem.redis.JRedisProvider;

/**
 * Reids构造消息队列
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年4月10日
 */
public class TaskQueue implements ITaskQueue{

	private static JRedisClient redisClient = JRedisProvider.getInstance().redis;

	private final static int REDIS_BD_INX = 9;
	
	private final String name;
	
	public TaskQueue(String name) {
		this.name = name;
	}

	@Override
	public void pushTask(String task) {
		redisClient.lpush(this.name, task);
	}
	
	@Override
	public String popTask() {
		return redisClient.rpop(this.name);
	}
	
	@Override
	public String getName() {
		return name;
	}

}
