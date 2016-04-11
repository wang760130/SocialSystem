package com.bj58.socialsystem.task;

import com.bj58.socialsystem.queue.TaskQueue;
import com.bj58.socialsystem.queue.TaskQueueManager;

/**
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年4月11日
 */
public class PublicDynamicsTask implements Task  {

	TaskQueue taskQueue = null;
	String task = null;
	
	@Override
	public void execute() {
		taskQueue = TaskQueueManager.get(TaskQueueManager.PUBLIC_DYNAMICS_QUEUE);
		
	}

}
