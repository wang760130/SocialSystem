package com.bj58.socialsystem.controllers;

import com.bj58.socialsystem.queue.TaskQueue;
import com.bj58.socialsystem.queue.TaskQueueManager;
import com.bj58.wf.mvc.ActionResult;
import com.bj58.wf.mvc.MvcController;
import com.bj58.wf.mvc.annotation.Path;

/**
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年4月1日
 */
public class ThumbsController extends MvcController {
	
	@Path("/thumbs")
	public ActionResult thumbs() {
		String userid = "";
		String dynid = "";
		String type = ""; //1:喜欢 2:不喜欢
		
		TaskQueue taskQueue = TaskQueueManager.get(TaskQueueManager.THUMBS_DYNAMICS_QUEUE);
		try {
			taskQueue.pushTask(userid + "/t" + dynid + "/t" + type);
		} catch (Exception e) { 
			e.printStackTrace();
		}
		
		return null;
	}
}
