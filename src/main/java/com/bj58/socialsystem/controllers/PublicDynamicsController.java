package com.bj58.socialsystem.controllers;

import com.bj58.socialsystem.bloomfilter.BloomFilterOnReids;
import com.bj58.socialsystem.common.RedisKey;
import com.bj58.wf.mvc.ActionResult;
import com.bj58.wf.mvc.annotation.Path;

/**
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年4月1日
 */
public class PublicDynamicsController {
	
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
			
		}
		
		// 添加bit
		bloomFilter.add(RedisKey.ALL_DYNAMICS, text);
		
		return null;
	}
}
