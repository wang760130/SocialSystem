package com.bj58.socialsystem.entity;

import java.util.Date;

public class Thumbs {

	private long id;
	
	private long userid;
	
	private short type;
	
	private long dynid;
	
	private Date addtime;
	
	private short state;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserid() {
		return userid;
	}

	public void setUserid(long userid) {
		this.userid = userid;
	}

	public short getType() {
		return type;
	}

	public void setType(short type) {
		this.type = type;
	}

	public long getDynid() {
		return dynid;
	}

	public void setDynid(long dynid) {
		this.dynid = dynid;
	}

	public Date getAddtime() {
		return addtime;
	}

	public void setAddtime(Date addtime) {
		this.addtime = addtime;
	}

	public short getState() {
		return state;
	}

	public void setState(short state) {
		this.state = state;
	}
	
}
