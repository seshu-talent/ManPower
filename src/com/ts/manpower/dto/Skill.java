package com.ts.manpower.dto;

public class Skill {
	
	private int userId;
	private int serviceId;
	private int experience;
	private String certificateImage;
	private boolean approved;
	
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public int getServiceId() {
		return serviceId;
	}
	public void setServiceId(int serviceId) {
		this.serviceId = serviceId;
	}
	public int getExperience() {
		return experience;
	}
	public void setExperience(int experience) {
		this.experience = experience;
	}
	
	public String getCertificateImage() {
		return certificateImage;
	}
	public void setCertificateImage(String certificateImage) {
		this.certificateImage = certificateImage;
	}
	
	public boolean isApproved() {
		return approved;
	}
	public void setApproved(boolean approved) {
		this.approved = approved;
	}
	
}
