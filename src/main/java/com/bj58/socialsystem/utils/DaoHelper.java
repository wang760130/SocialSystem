package com.bj58.socialsystem.utils;

import com.bj58.spat.scf.client.utility.logger.ILog;
import com.bj58.spat.scf.client.utility.logger.LogFactory;

/**
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2015年7月29日
 */
public class DaoHelper {
	
	private static final ILog log = LogFactory.getLogger(DaoHelper.class);

	public static DaoHelper daoHelper = null;
	static {
		try {
			/*String path = Path.getCurrentPath()+"/config/db.properties";
			log.info("db.properties, path = " + path);
			daoHelper = DAOBase.createIntrance(path);*/
		} catch (Exception e) {
			log.error("init daoHelper error",e);
		}
	}
	
}
