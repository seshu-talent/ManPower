package com.ts.manpower.dto;

import java.util.*;

public class Work {
	private int id;
	private Date postedDate;
	private User postedBy;
	private ServiceType service;
	private String description;	
	private boolean status;
	
	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}
	
	public void setPostedDate(Date postedDate) {
		this.postedDate = postedDate;
	}

	public User getPostedBy() {
		return postedBy;
	}

	public void setPostedBy(User postedBy) {
		this.postedBy = postedBy;
	}

	public ServiceType getService() {
		return service;
	}

	public void setService(ServiceType service) {
		this.service = service;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public boolean isStatus() {
		return status;
	}

	public void setStatus(boolean status) {
		this.status = status;
	}

	public Date getPostedDate() {
		return postedDate;
	}
}
