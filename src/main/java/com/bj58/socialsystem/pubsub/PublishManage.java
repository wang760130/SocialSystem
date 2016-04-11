package com.bj58.socialsystem.pubsub;

import redis.clients.jedis.Jedis;

import com.bj58.socialsystem.redis.JRedisClient;
import com.bj58.socialsystem.redis.JRedisProvider;

/**
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年4月11日
 */
public class PublishManage {
	
	private static JRedisClient redisClient = JRedisProvider.getInstance().redis;

}
