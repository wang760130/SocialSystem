package com.bj58.socialsystem.queue;

/**
 * 消息队列
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年4月10日
 */
public interface ITaskQueue {

	/**
	 * 获取队列名
	 * @return
	 */
	public String getName();
	
	/**
	 * 往队列中添加任务
	 * @param task
	 */
	public void pushTask(String task);
	
	/**
	 * 从队列中取出一个任务
	 */
	public String popTask();

}
