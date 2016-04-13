package com.bj58.socialsystem.entity;

import java.io.Serializable;
import java.util.Date;

public class Friends implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	
	private long userid1;
	
	private long userid2;
	
	private Date addtime;
	
	private short state;

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public long getUserid1() {
		return userid1;
	}

	public void setUserid1(long userid1) {
		this.userid1 = userid1;
	}

	public long getUserid2() {
		return userid2;
	}

	public void setUserid2(long userid2) {
		this.userid2 = userid2;
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
