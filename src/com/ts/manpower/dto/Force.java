package com.ts.manpower.dto;

import java.util.List;

public class Force extends User{
	
	private String aadharImage;
	private List<Skill> skillsList;	
	private List<WorkReference> workReferencesList;	
	private List<Bid> bidsList;
				
	public String getAadharImage() {
		return aadharImage;
	}

	public void setAadharImage(String aadharImage) {
		this.aadharImage = aadharImage;
	}

	public List<Skill> getSkillsList() {
		return skillsList;
	}
	
	public void setSkillsList(List<Skill> skillsList) {
		this.skillsList = skillsList;
	}
	
	public List<WorkReference> getWorkReferencesList() {
		return workReferencesList;
	}
	
	public void setWorkReferencesList(List<WorkReference> workReferencesList) {
		this.workReferencesList = workReferencesList;
	}
	
	public List<Bid> getBidsList() {
		return bidsList;
	}
	
	public void setBidsList(List<Bid> bidsList) {
		this.bidsList = bidsList;
	}	
	
}
