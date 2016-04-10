package com.bj58.socialsystem.task;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

import com.bj58.socialsystem.queue.TaskQueue;
import com.bj58.socialsystem.queue.TaskQueueManager;

/**
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年4月10日
 */
public class DeleteDynamicsTask implements Task {
	
	TaskQueue taskQueue = null;
	String task = null;
	
	@Override
	public void execute() {
		taskQueue = TaskQueueManager.get(TaskQueueManager.DELETE_DYNAMICS_QUEUE);
		
		Set<Serializable> executedTaskSet = new HashSet<Serializable>();
		
		task = taskQueue.popTask();
		
		while(task != null) {
			if(executedTaskSet.contains(task)) {
				taskQueue.pushTask(task);
				break;
			}
			
			executeSingleTask(taskQueue, task);
			
			task = taskQueue.popTask();
		}
		
	}

	public void executeSingleTask(TaskQueue tashQueue, String task) {
		try {
			// TODO
		} catch (Exception e) {
			if(task != null) {
				tashQueue.pushTask(task);
			}
		}
		
	}

}
