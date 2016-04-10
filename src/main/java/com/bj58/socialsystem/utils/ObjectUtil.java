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
	
//	 JedisUtil.lpush(redisKey, ObjectUtil.objectToBytes(msg1));
	
//	 byte[] bytes = JedisUtil.rpop(redisKey);
//   Message msg = (Message) ObjectUtil.bytesToObject(bytes);
	
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
}
