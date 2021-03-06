package com.bj58.socialsystem.common;

/**
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年3月30日
 */
public class RedisKey {
	
	// 所有动态 bit
	public static final String ALL_DYNAMICS = "jerry_all_dynamics_";
	
	// 用户信息 hash
//	public static final String USER = "jerry_user_";
	
	// 用户好友 set
	public static final String FIRENDS = "jerry_firends_";
	
	// 动态信息 hash key:id key:la key:lo key:like key:unlike
	public static final String DYNAMICS = "jerry_dynamics_";
	
	// 用户好友的动态  zset 
	public static final String FIRENDS_DYNAMICS = "jerry_firends_dynamics_";
	
	// 动态位置信息 geo
	public static final String DYNAMICS_GEO = "jerry_dunamics_geo";
	
	// 用户喜欢不喜欢  hash
//	public static final String THUMBS = "jerry_thumbs_";
	
	// 动态发布队列  list
	public static final String PUBLIC_DYNAMICS_QUEUE = "jerry_public_dynamics_queue";
	
	// 动态删除队列  list
	public static final String DELETE_DYANMICS_QUEUE = "jerry_delete_dynamics_queue";
	
	// 点赞队列 list
	public static final String THUMBS_QUEUE = "jerry_thumbs_queue";
}
