package com.bj58.socialsystem.entity;

import java.io.Serializable;
import java.util.Date;

import com.bj58.sfft.utility.dao.annotation.NotDBColumn;

public class Dynamics implements Serializable {

	private static final long serialVersionUID = 1L;

	private long id;
	
	private long userid;
	
	private short type;
	
	private String text;
	
	private String coords;
	
	private Date addtime;
	
	private short state;
	
	@NotDBColumn
	private String addtimeStr;
	
	@NotDBColumn
	private String lon;
	
	@NotDBColumn
	private String lat;
	
	@NotDBColumn
	private double distance;

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
	
	public String getAddtimeStr() {
		return addtimeStr;
	}

	public void setAddtimeStr(String addtimeStr) {
		this.addtimeStr = addtimeStr;
	}

	public String getLon() {
		return lon;
	}

	public void setLon(String lon) {
		this.lon = lon;
	}

	public String getLat() {
		return lat;
	}

	public void setLat(String lat) {
		this.lat = lat;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}
	
}
