package com.ts.manpower.dto;
import java.util.List;

public class SubCategory {
	private int id;
	private String type;
	boolean deleted;
	private List<ServiceType> servicesList;
	int categoryId;
    String subCategoryImage;	
    
   	public void setId(int id) {
		this.id = id;
	}
   	
   	public int getId() {
		return id;
	}

	public void setCategoryId(int categoryId) {
		this.categoryId = categoryId;
	}
	
	
	public int getCategoryId() {
		return categoryId;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}	
	
	public String getSubCategoryImage() {
		return subCategoryImage;
	}

	public void setSubCategoryImage(String subCategoryImage) {
		this.subCategoryImage = subCategoryImage;
	}

	public String getType() {
		return type;
	}
	public void setType(String type) {
		this.type = type;
	}

	public List<ServiceType> getServicesList() {
		return servicesList;
	}

	public void setServicesList(List<ServiceType> servicesList) {
		this.servicesList = servicesList;
	}	
	
}
