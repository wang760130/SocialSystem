package com.bj58.socialsystem.utils;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.bj58.spat.scf.serializer.component.annotation.SCFMember;
import com.bj58.spat.scf.serializer.component.annotation.SCFSerializable;
import com.google.gson.Gson;

/**
 * @author Wangjiajun 
 * @Email  wangjiajun@58.com
 * @date   2015年8月10日
 */
@SCFSerializable
public class Page implements Serializable {

	private static final long serialVersionUID = 5192357666324600054L;

	public static final String ASC = "asc";
	public static final String DESC = "desc";
	
	/**
	 * 当前页数
	 */
	@SCFMember(sortId=1)
	private int pageNo = 1;
	/**
	 * 每页大小
	 */
	@SCFMember(sortId=2)
	private int pageSize = 15;
	/**
	 * 排序字段，如：id
	 */
	@SCFMember(sortId=3)
	private String orderFields = null;
	/**
	 * 排序类型，如：desc，asc
	 */
	@SCFMember(sortId=4)
	private String order = null;
	/**
	 * 排序名称，如：id dsc，id asc
	 */
	@SCFMember(sortId=5)
	private String orderName = null;
	/**
	 * 查询条件
	 */
	@SCFMember(sortId=6)
	private String condition = null;
	@SCFMember(sortId=7)
	private boolean autoCount = true;
	@SCFMember(sortId=8)
	private int start = 0;
	/**
	 * 返回结果集合
	 */
	@SCFMember(sortId=9)
	private List resultList = new ArrayList();
	/**
	 * 总共条数
	 */
	@SCFMember(sortId=10)
	private long totalCount = -1L;
	
	public Page() {
		super();
	}
	
	public Page(String condition, int pageNo, int pageSize, String orderName) {
		super();
		this.condition = condition;
		this.pageNo = pageNo;
		this.pageSize = pageSize;
		this.orderName = orderName;
	}

	public int getPageNo() {
		return pageNo;
	}
	public void setPageNo(int pageNo) {
		this.pageNo = pageNo;
		if (pageNo < 1)
			this.pageNo = 1;
	}
	public Page pageNo(int thePageNo) {
		setPageNo(thePageNo);
		return this;
	}
	
	public int getPageSize() {
		return pageSize;
	}
	public void setPageSize(int pageSize) {
		this.pageSize = pageSize;
		if (pageSize < 1)
			this.pageSize = 1;
	}
	public Page pageSize(int thePageSize) {
		setPageSize(thePageSize);
		return this;
	}
	
	
	public String getOrderFields() {
		return orderFields;
	}
	public void setOrderFields(String orderFields) {
		this.orderFields = orderFields;
	}
	public Page orderFields(String theOrderFields) {
		setOrderFields(theOrderFields);
		return this;
	}
	
	
	public String getOrder() {
		return order;
	}
	public void setOrder(String order) {
		if(order!=null){
			String lower = order.toLowerCase();
			String[] arrayOfString = lower.split("\\,");
	
			for (int i = 0; i < arrayOfString.length; i++) {
				String str = arrayOfString[i];
				if ((!"desc".equals(str))&& (!"asc".equals(str))) {
					throw new IllegalArgumentException("排序方向" + str + "不是合法值");
				}
			}
			this.order = lower;
		}
	}
	public Page order(String theOrder) {
		setOrder(theOrder);
		return this;
	}
	
	
	public String getOrderName() {
		String orderName = "";
		if(this.orderName != null && !"".equals(this.orderName)) {
			orderName = this.orderName;
		} else {
			String orderFields = this.getOrderFields();
			if(orderFields != null && !orderFields.equals("")) {
				orderName = orderFields + " ";
				String order = this.getOrder();
				if(order != null && (order.toLowerCase().equals(ASC) || order.toLowerCase().equals(DESC))) {
					orderName += order;
				}
			}
		}
		
		return orderName;
	}
	public void setOrderName(String orderName) {
		this.orderName = orderName;
	}
	
	
	public String getCondition() {
		return condition;
	}
	public void setCondition(String condition) {
		this.condition = condition;
	}
	
	
	public boolean isAutoCount() {
		return autoCount;
	}
	public void setAutoCount(boolean autoCount) {
		this.autoCount = autoCount;
	}
	public Page autoCount(boolean theAutoCount) {
		setAutoCount(theAutoCount);
		return this;
	}
	
	
	public int getStart() {
		return start;
	}
	public void setStart(int start) {
		this.start = start;
	}
	public List getResultList() {
		return resultList;
	}
	public void setResultList(List resultList) {
		this.resultList = resultList;
	}
	public long getTotalCount() {
		return totalCount;
	}
	public void setTotalCount(long totalCount) {
		this.totalCount = totalCount;
	}

	
	public int getFirst() {
		if (this.start != 0)
			return this.start + 1;
		return (this.pageNo - 1) * this.pageSize + 1;
	}
	
	public long getTotalPages() {
		if (this.totalCount < 0L) {
			return -1L;
		}

		long l = this.totalCount / this.pageSize;
		if (this.totalCount % this.pageSize > 0L) {
			l += 1L;
		}
		return l;
	}
	
	public boolean isHasNext() {
		return this.pageNo + 1 <= getTotalPages();
	}
	
	public int getNextPage() {
		if (isHasNext()) {
			return this.pageNo + 1;
		}
		return this.pageNo;
	}
	
	public boolean isHasPre() {
		return this.pageNo - 1 >= 1;
	}

	public int getPrePage() {
		if (isHasPre()) {
			return this.pageNo - 1;
		}
		return this.pageNo;
	}
	
	public static String getPageCacheKey(String key) {
		String str = "";
		if (key.startsWith("/")) {
			str = key.substring(1, key.length());
		}

		if (str.indexOf("/") != -1) {
			str = str.substring(0, str.indexOf("/"));
		}

		return str;
	}
	
//	public String toJson() {
//		Map<String,Object> jsonMap = new HashMap<String,Object>();
//		jsonMap.put("page", String.valueOf(this.getPageNo()));
//		jsonMap.put("total", this.getTotalPages());
//		jsonMap.put("records", String.valueOf(this.getTotalCount()));	
//		jsonMap.put("rows", this.getResultList());
//		JSONObject jsonObject = JSONObject.fromObject(jsonMap);  
//		return jsonObject.toString();
//	}	
	
	public String toJson() {
		Map<String,Object> jsonMap = new HashMap<String,Object>();
		jsonMap.put("page", String.valueOf(this.getPageNo()));
		jsonMap.put("total", this.getTotalPages());
		jsonMap.put("records", String.valueOf(this.getTotalCount()));
		jsonMap.put("rows", this.getResultList());
		Gson gson = new Gson();
		return gson.toJson(jsonMap);
	}
}
