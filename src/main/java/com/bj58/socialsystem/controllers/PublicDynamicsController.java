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
	
	@Path("/dynamics/public")
	public ActionResult publicDynamics() {
		
		String text = "";
		String userid = "";
		
		BloomFilterOnReids bloomFilter = new BloomFilterOnReids();
		bloomFilter.add(RedisKey.ALL_DYNAMICS, text);
		
		return null;
	}
}
