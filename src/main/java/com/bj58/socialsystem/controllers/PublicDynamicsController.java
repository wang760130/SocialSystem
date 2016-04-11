package com.bj58.socialsystem.controllers;

import com.bj58.socialsystem.bloomfilter.BloomFilterOnReids;
import com.bj58.socialsystem.common.RedisKey;
import com.bj58.socialsystem.entity.Dynamics;
import com.bj58.socialsystem.queue.TaskQueue;
import com.bj58.socialsystem.queue.TaskQueueManager;
import com.bj58.socialsystem.utils.ObjectUtil;
import com.bj58.wf.mvc.ActionResult;
import com.bj58.wf.mvc.MvcController;
import com.bj58.wf.mvc.annotation.Path;

/**
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年4月1日
 */
public class PublicDynamicsController extends MvcController {
	
	BloomFilterOnReids bloomFilter = null;
	
	@Path("/dynamics/public")
	public ActionResult publicDynamics() {
		
		String userid = "";
		String text = "";
		String lon = "";
		String lat = "";
		
		bloomFilter = new BloomFilterOnReids();
		boolean isExit = bloomFilter.isExit(RedisKey.ALL_DYNAMICS, text);
		
		if(isExit == true) {
			// 已存在
		}
		
		// 添加bit
		bloomFilter.add(RedisKey.ALL_DYNAMICS, text);
		
		Dynamics dynamics = new Dynamics();
		dynamics.setUserid(Long.valueOf(userid));
		dynamics.setLon(lon);
		dynamics.setLat(lat);
		dynamics.setText(text);
		
		TaskQueue taskQueue = TaskQueueManager.get(TaskQueueManager.PUBLIC_DYNAMICS_QUEUE);
		try {
			taskQueue.pushTask(ObjectUtil.objectToString(dynamics));
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		return null;
	}
}
