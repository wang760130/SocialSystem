package com.bj58.socialsystem.dao;

import java.util.List;

import com.bj58.socialsystem.utils.Page;


/**
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2015年7月30日
 */
public interface IDao<T> {
	
	public T load(long id) throws Exception;
	
    public long insert(T entity) throws Exception;
    
    public void update(T entity) throws Exception;
	
	public void delete(long id) throws Exception;
    
    public List<T> page(Page page) throws Exception;
	
	public int count(String where) throws Exception;
}
