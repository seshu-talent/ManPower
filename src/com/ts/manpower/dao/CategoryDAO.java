package com.ts.manpower.dao;

import java.sql.*;
import java.util.*;

import com.ts.manpower.dto.*;

public class CategoryDAO {

	public static boolean add(Category category) {
		
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement("insert into category(type,image,deleted) values(?,?,0)", PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, category.getType());
			ps.setString(2, category.getType() + ".jpg");			

			if (ps.executeUpdate() > 0) {

				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					category.setId(rs.getInt(1));
				}

				return true;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			DBUtility.close(con);
		}
		
		return false;
	}
	

	public static boolean delete(int categoryId) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement("update category set deleted=1 where id = ?");
			ps.setInt(1, categoryId);
			if (ps.executeUpdate() > 0) {
				return true;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			DBUtility.close(con);
		}
		
		return false;
	}

	public static Category get(int categoryId) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Category category = null;
		
		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement("select * from category where id = ?");
			ps.setInt(1, categoryId);
			rs = ps.executeQuery();
			if (rs.next()) {
				category = new Category();
				category.setId(rs.getInt("id"));
				category.setType(rs.getString("type"));
				category.setCategoryImage(rs.getString("image"));

				if (rs.getInt("deleted") != 0)
					category.setDeleted(true);
				else
					category.setDeleted(false);
				
				
				category.setSubCategoriesList(SubCategoryDAO.getSubCategories(categoryId));
				
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			DBUtility.close(con);
		}
		
		return category;
	}

	public static List<Category> getCategories() {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Category> categoriesList = null;
		
		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement("select * from category where deleted = 0");
			rs = ps.executeQuery();

			categoriesList = new ArrayList<>();

			while (rs.next()) {

				Category category = new Category();
				category.setId(rs.getInt("id"));
				category.setType(rs.getString("type"));
				category.setCategoryImage(rs.getString("image"));

				if (rs.getInt("deleted") != 0)
					category.setDeleted(true);
				else
					category.setDeleted(false);
				
				
				category.setSubCategoriesList(SubCategoryDAO.getSubCategories(category.getId()));
				
				categoriesList.add(category);
				

			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			DBUtility.close(con);
		}
		
		return categoriesList;
	}
}
