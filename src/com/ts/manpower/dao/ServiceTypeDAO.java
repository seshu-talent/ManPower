package com.ts.manpower.dao;

import java.sql.*;
import java.util.*;

import com.ts.manpower.dto.*;

public class ServiceTypeDAO {

	public static boolean add(ServiceType serviceType) {
		 
		Connection con = null;
		PreparedStatement ps = null;
		try {
			String stmt = "insert into service(description,approx_price,subcategory_id,image,deleted) values(?,?,?,?,0)"; 
			con = DBUtility.getConnection();

			ps = con.prepareStatement(stmt, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, serviceType.getDescription());
			ps.setDouble(2, serviceType.getApproxPrice());			
			ps.setInt(3, serviceType.getSubCategoryId());
			ps.setString(4, serviceType.getId() + ".jpg");
			
			if (ps.executeUpdate() != 0) {
				
				ResultSet rs = ps.getGeneratedKeys();
	            if (rs.next())
	            	serviceType.setId(rs.getInt(1));                
	          
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

	
	public static boolean delete(int serviceTypeId) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement("update service set deleted = 1 where id = ? ");
			ps.setInt(1, serviceTypeId);
			if (ps.executeUpdate() > 0)
				return true;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			DBUtility.close(con);
		}

		return false;
	}

	
	public static ServiceType get(int serviceTypeId) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		ServiceType serviceType = null;
		
		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement("select * from service where deleted = 0 and id = ?");
			ps.setInt(1, serviceTypeId);
			rs = ps.executeQuery();
			if (rs.next()) {
				serviceType = new ServiceType();
				serviceType.setId(rs.getInt("id"));
				serviceType.setDescription(rs.getString("description"));
				serviceType.setApproxPrice(rs.getFloat("approx_price"));
				serviceType.setServiceTypeImage(rs.getString("image"));
				if (rs.getInt("deleted") != 0)
					serviceType.setDeleted(true);
				else
					serviceType.setDeleted(false);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			DBUtility.close(con);
		}

		return serviceType;
	}	
	

	public static List<ServiceType> getServiceTypes(int subCategoryId) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<ServiceType> serviceTypesList = null;
		
		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement("select * from service where deleted = 0 and subcategory_id = ? ");
			ps.setInt(1, subCategoryId);
			rs = ps.executeQuery();
			
			serviceTypesList = new ArrayList<>();
			
			while (rs.next()) {				
				ServiceType serviceType = new ServiceType();
				serviceType.setId(rs.getInt("id"));
				serviceType.setDescription(rs.getString("description"));
				serviceType.setApproxPrice(rs.getFloat("approx_price"));
				serviceType.setServiceTypeImage(rs.getString("image"));
				serviceType.setSubCategoryId(subCategoryId);
				if (rs.getInt("deleted") != 0)
					serviceType.setDeleted(true);
				else
					serviceType.setDeleted(false);
				
				serviceTypesList.add(serviceType);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			DBUtility.close(con);
		}

		return serviceTypesList;
	}	
}
