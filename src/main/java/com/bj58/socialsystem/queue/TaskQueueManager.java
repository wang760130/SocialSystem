package com.bj58.socialsystem.queue;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 获取队列实例的工具类
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年4月10日
 */
public class TaskQueueManager {

	private static Map<String, TaskQueue> queneMap = new ConcurrentHashMap<String, TaskQueue>();
	
	public static final String PUBLIC_DYNAMICS_QUEUE = "PUBLIC_DYNAMICS_QUEUE";
	
	public static final String DELETE_DYNAMICS_QUEUE = "DELETE_DYNAMICS_QUEUE";
	
	public static void init() {
		queneMap.put(PUBLIC_DYNAMICS_QUEUE, new TaskQueue(PUBLIC_DYNAMICS_QUEUE));
		queneMap.put(DELETE_DYNAMICS_QUEUE, new TaskQueue(DELETE_DYNAMICS_QUEUE));
	}
	
	static {
		init();
	}
	
	public static TaskQueue get(String name) {
		return getTaskQueue(name);
	}
	
	public static TaskQueue getTaskQueue(String name) {
		return queneMap.get(name);
	}
}
