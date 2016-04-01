package com.bj58.socialsystem.demo;

import com.bj58.socialsystem.utils.StopWatch;

public class StopWatchDemo {
	
	public static void main(String[] args) throws InterruptedException {
		  StopWatch first = new StopWatch("First");  
	      first.start("A");  
	      Thread.sleep(200);  
	      first.stop();  
	      first.start("B");  
	      Thread.sleep(200);  
	      first.stop();  
	      first.start("C");  
	      Thread.sleep(120);  
	      first.stop();  
	      System.out.println(first.prettyPrint());  
	      
	}
}
