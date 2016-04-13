package com.bj58.socialsystem.performance;

import org.junit.Test;
import com.alibaba.fastjson.JSONObject;
import com.bj58.socialsystem.entity.Friends;

public class JsonPerformance {
	
	@Test
	public void test() {
		Friends friends = new Friends();
		friends.setId(12);
		friends.setState((short)1);
		
		long startTime = System.currentTimeMillis();
		for(int i = 0; i < 10000; i++) {
			String json = JSONObject.toJSONString(friends);
			JSONObject jsonObject = JSONObject.parseObject(json);
		}
		System.out.println("execute time :" + (System.currentTimeMillis() - startTime) + "ms");
	}
	
}
