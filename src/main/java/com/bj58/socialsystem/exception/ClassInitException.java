package com.bj58.socialsystem.exception;

/**
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2015年8月3日
 */
public class ClassInitException extends RuntimeException{
	
	private static final long serialVersionUID = -6070433409882587676L;

	public ClassInitException() {
		super(); 
	}

	public ClassInitException(String message, Throwable cause) {
		super(message, cause); 
	}

	public ClassInitException(String message) {
		super(message); 
	}

	public ClassInitException(Throwable cause) {
		super(cause); 
	}
}