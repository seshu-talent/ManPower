package com.ts.manpower.service;

import java.util.*;

import com.ts.manpower.dao.*;
import com.ts.manpower.dto.*;

public class ManPowerService {

	public boolean addCategory(Category category) {
		return CategoryDAO.add(category);
	}

	public boolean deleteCategory(int categoryId) {
		return CategoryDAO.delete(categoryId);
	}

	public Category getCategory(int categoryId) {
		return CategoryDAO.get(categoryId);
	}

	public List<Category> getCategories() {
		return CategoryDAO.getCategories();
	}

	public boolean addSubCategory(SubCategory subCategory) {
		return SubCategoryDAO.add(subCategory);
	}

	public boolean deleteSubCategory(int subCategoryId) {
		return SubCategoryDAO.delete(subCategoryId);
	}

	public SubCategory getSubCategory(int subCategoryId) {
		return SubCategoryDAO.get(subCategoryId);
	}
	
	
	public List<SubCategory> getSubCategories() {
		return SubCategoryDAO.getSubCategories();
	}

	public List<SubCategory> getSubCategories(int categoryId) {
		return SubCategoryDAO.getSubCategories(categoryId);
	}

	public boolean addServiceType(ServiceType serviceType) {
		return ServiceTypeDAO.add(serviceType);
	}

	public boolean deleteServiceType(int serviceTypeId) {
		return ServiceTypeDAO.delete(serviceTypeId);
	}

	public ServiceType getServiceType(int serviceTypeId) {
		return ServiceTypeDAO.get(serviceTypeId);
	}

	public List<ServiceType> getServiceTypes(int subCategoryId) {
		return ServiceTypeDAO.getServiceTypes(subCategoryId);
	}

	public boolean registerUser(User user) {
		return UserDAO.register(user);
	}

	public User loginUser(String email, String password) {
		return UserDAO.login(email,password);
	}

	public User getUserDetails(int userId) {
		return new UserDAO().get(userId);
	}

	public User getUserDetails(String emailId) {
		return new UserDAO().get(emailId);
	}

	public Work addWork(Work work) {
		return new WorkDAO().add(work);
	}

	public List<Work> getWorksList(int userId) {
		return new WorkDAO().getWorks(userId);
	}

	public Address addAddress(Address address) {
		return new AddressDAO().add(address);
	}

	public boolean deleteAddress(int addressId) {
		return new AddressDAO().delete(addressId);
	}

	public Address getAddress(int addressId) {
		return new AddressDAO().get(addressId);
	}

	public List<Address> getAddressList(int userId) {
		return new AddressDAO().getAddressList(userId);
	}

	public boolean updatePassword(String password, int userId) {
		return new UserDAO().updatePassword(password, userId);
	}

	public boolean registerForce(Force force, Skill skill, WorkReference reference) {
		return ForceDAO.register(force, skill, reference);
	}

	public Force loginForce(String email, String password) {
		return new ForceDAO().login(email, password);
	}

	public boolean addSkill(Skill skill) {
		return new ForceDAO().add(skill);
	}

	public boolean addReference(WorkReference reference) {
		return new ForceDAO().add(reference);
	}

	public boolean addBid(Bid bid) {
		return new ForceDAO().add(bid);
	}

	public Force getForceDetails(int userId) {
		return new ForceDAO().get(userId);
	}

	public Force getForceDetails(String emailId) {
		return new ForceDAO().get(emailId);
	}

	public boolean approveForce(int userId) {
		return new ForceDAO().approveForce(userId);
	}

	public boolean changeStatus(Work work) {
		return new WorkDAO().changeStatus(work);
	}

	public List<Skill> getSkillsList(Force force) {
		return new ForceDAO().getSkillsList(force);
	}

	public List<WorkReference> getReferencesList(Force force) {
		return new ForceDAO().getWorkReferencesList(force);
	}

	public List<Bid> getBidsList(Force force) {
		return new ForceDAO().getBidsList(force);
	}
}
