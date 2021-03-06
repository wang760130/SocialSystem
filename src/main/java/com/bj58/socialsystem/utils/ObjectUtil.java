package com.bj58.socialsystem.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2016年4月4日
 */
public class ObjectUtil {
	private static final String DEFAULT_CHARSET = "UTF-8";
	
	 /**对象转byte[]
     * @param obj
     * @return
     * @throws IOException
     */
    public static byte[] objectToBytes(Object obj) throws Exception{
    	ByteArrayOutputStream bo = null;
    	ObjectOutputStream oo = null;
    	byte[] bytes = null;
    	try {
    		bo = new ByteArrayOutputStream();
            oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            bytes = bo.toByteArray();
    	} finally {
    		if(oo != null) {
    			oo.close();
    		}
    		if(bo != null) {
    			bo.close();
    		}
    	}
        return bytes;
    }
    
    /**
     * 对象转string
     * @param obj
     * @return
     * @throws Exception
     */
    public static String objectToString(Object obj) throws Exception{
    	ByteArrayOutputStream bo = null;
    	ObjectOutputStream oo = null;
    	String str = null;
    	try {
    		bo = new ByteArrayOutputStream();
            oo = new ObjectOutputStream(bo);
            oo.writeObject(obj);
            str = bo.toString(DEFAULT_CHARSET);
    	} finally {
    		if(oo != null) {
    			oo.close();
    		}
    		if(bo != null) {
    			bo.close();
    		}
    	}
        return str;
    }
    
    /**byte[]转对象
     * @param bytes
     * @return
     * @throws Exception
     */
    public static Object bytesToObject(byte[] bytes) throws Exception {
        ByteArrayInputStream bis  = null;
        ObjectInputStream ois = null;
        try {
        	bis= new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
        } finally {
        	if(ois != null) {
        		ois.close();
        	}
        	if(bis != null) {
        		bis.close();	
        	}
        }
        
        return ois.readObject();
    }
    
    /**
     * string转对象 
     * @param str
     * @return
     * @throws Exception
     */
    public static Object stringToObject(String str) throws Exception {
        ByteArrayInputStream bis  = null;
        ObjectInputStream ois = null;
        try {
        	bis= new ByteArrayInputStream(str.getBytes(DEFAULT_CHARSET));
            ois = new ObjectInputStream(bis);
        } finally {
        	if(ois != null) {
        		ois.close();
        	}
        	if(bis != null) {
        		bis.close();	
        	}
        }
        
        return ois.readObject();
    }
}
