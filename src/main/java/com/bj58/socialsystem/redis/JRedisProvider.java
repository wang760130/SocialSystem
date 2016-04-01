package com.bj58.socialsystem.redis;

import java.io.File;

import org.apache.log4j.Logger;

/**
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年1月15日
 */
public class JRedisProvider {
	
	private static  JRedisProvider instance = null;
	private static final Logger logger = Logger.getLogger(JRedisProvider.class);
	public JRedisClient redis = null;
	
	public JRedisProvider() {
		try {
			
//			String configPath = Path.getCurrentPath() + File.separator + "config" + File.separator + "redis.xml";
			String configPath = "D:\\My Workspace\\SocialSystem\\conf\\redis.xml";
			redis = JRedisHelperCreator.creatInstance(configPath);
		} catch (Exception e) {
			logger.error("redis init exception");
		} 
	}
	
	
	public static JRedisProvider getInstance(){
	    if(instance == null ){
	        synchronized(JRedisProvider.class){
	            if(null == instance){
	                instance = new JRedisProvider();
	            }
	        }
	    }
		return instance;
	}
	
}
