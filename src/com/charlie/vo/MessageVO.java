package com.charlie.vo;

public class MessageVO {
	int sq;
	String id;
	String date;
	String time;
	String message;
	
	public MessageVO() {}
	
	public MessageVO(int sq,String id , String date, String time, String message) {
		this.sq = sq;
		this.id = id;
		this.date = date;
		this.time = time;
		this.message =message;
	}

	public int getSq() {
		return sq;
	}

	public String getId() {
		return id;
	}

	public String getDate() {
		return date;
	}

	public String getTime() {
		return time;
	}

	public String getMessage() {
		return message;
	}

	
}
