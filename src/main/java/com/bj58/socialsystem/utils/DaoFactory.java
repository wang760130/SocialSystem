package com.bj58.socialsystem.utils;

import java.util.concurrent.ConcurrentHashMap;

import com.bj58.socialsystem.dao.IDao;
import com.bj58.socialsystem.exception.ClassInitException;

/**
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2015年8月3日
 */
public class DaoFactory {
	
	private static ConcurrentHashMap<Class<?>, Object> classCache = new ConcurrentHashMap<Class<?>, Object>();

	private final static String DAO_POSTFIX = "DaoImpl";
	
	private final static String PATH_IMPL = "com.bj58.socialsystem.dao.impl.";
	
	public static IDao<?> getDao(Class<?> clazz) {
		return getDao(clazz, true);
	}

	public static IDao<?> getDao(Class<?> interfaceName, boolean single) {
		try {
			String clazzName = interfaceName.getName();
			String name = clazzName.substring(clazzName.lastIndexOf(".") + 1);
			String implClazz = PATH_IMPL + name + DAO_POSTFIX;
			Class<?> implClass = Class.forName(implClazz);
			return getDaAddCache(implClass, single);
		} catch (Exception e) {
			throw new ClassInitException(e);
		}
	}

	private static IDao<?> getDaAddCache(Class<?> implClass, boolean single) {
		Object object = null;
		try {
			if(single) {
				if(classCache.containsKey(implClass)) {
					object = classCache.get(implClass);
				} else {
					object = implClass.newInstance();
					if(null != object) {
						classCache.put(implClass, object);
					}
				}
			}  else {
				object = implClass.newInstance();
			}
			
			return (IDao<?>) object;
		} catch (Exception e) {
			throw new ClassInitException(e);
		}
	}
}
