package com.bj58.socialsystem.entity;

import java.util.Date;

public class Dynamics {

	private long id;
	
	private long userid;
	
	private short type;
	
	private String text;
	
	private String coords;
	
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

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public String getCoords() {
		return coords;
	}

	public void setCoords(String coords) {
		this.coords = coords;
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