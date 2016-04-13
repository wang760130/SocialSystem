package com.bj58.socialsystem.performance;

import org.junit.Test;

import com.bj58.socialsystem.entity.Friends;
import com.bj58.socialsystem.utils.ObjectUtil;

public class SerializablePerformance {
	
	@Test
	public void test() throws Exception {
		Friends friends = new Friends();
		friends.setId(12);
		friends.setState((short)1);
		
		long startTime = System.currentTimeMillis();
		for(int i = 0; i < 10000; i++) {
			String str = ObjectUtil.objectToString(friends);
			System.out.println(str);
			//friends = (Friends)ObjectUtil.stringToObject(str);
		}
		System.out.println("execute time :" + (System.currentTimeMillis() - startTime) + "ms");
	}
}
