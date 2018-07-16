package com.ts.manpower.dto;

import java.util.*;

public class Bid {
	private Work work;
	private float bidPrice;
	private Date bidDate;
	private char status;
	private String comments;
	private int userId;

	public Work getWork() {
		return work;
	}

	public void setWork(Work work) {
		this.work = work;
	}

	public float getBidPrice() {
		return bidPrice;
	}

	public void setBidPrice(float bidPrice) {
		this.bidPrice = bidPrice;
	}

	public Date getBidDate() {
		return bidDate;
	}

	public void setBidDate(Date bidDate) {
		this.bidDate = bidDate;
	}

	public char getStatus() {
		return status;
	}

	public void setStatus(char status) {
		this.status = status;
	}

	public String getComments() {
		return comments;
	}

	public void setComments(String comments) {
		this.comments = comments;
	}

	public int getUserId() {
		return userId;
	}

	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
