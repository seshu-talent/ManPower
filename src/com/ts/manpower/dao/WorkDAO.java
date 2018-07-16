package com.ts.manpower.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.ts.manpower.dto.*;

public class WorkDAO {	
	
	public Work add(Work work) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBUtility.getConnection();
			String stmt = "insert into Work(posted_date,posted_by,service_id,description,status) values(?,?,?,?,'O')";

			ps = con.prepareStatement(stmt, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setDate(1, new java.sql.Date(System.currentTimeMillis()));
			ps.setInt(2, work.getPostedBy().getId());
			ps.setInt(3, work.getService().getId());
			ps.setString(4, work.getDescription());			

			if (ps.executeUpdate() > 0) {

				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					work.setId(rs.getInt(1));
					work.setPostedDate(new java.sql.Date(System.currentTimeMillis()));					
				}

				return work;
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			DBUtility.close(con);
		}
		
		return null;
	}
	
	
	public Work get(int workId) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Work work = null;
		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement("select * from work where id = ?");
			ps.setInt(1, workId);
			rs = ps.executeQuery();
			if (rs.next()) {
				work = new Work();
				work.setId(rs.getInt("id"));
				work.setPostedDate(rs.getDate("posted_date"));
				
				User user = new User();
				user.setId(rs.getInt("user_id"));
				work.setPostedBy(user);
				
				ServiceType serviceType = new ServiceType();
				serviceType.setId(rs.getInt("service_id"));
				work.setService(serviceType);
				
				work.setDescription(rs.getString("description"));
				
				if(rs.getString("status").equals("O"))
					work.setStatus(true);
				else
					work.setStatus(false);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			DBUtility.close(con);
		}
		
		return work;
	}
	
	
	
	public List<Work> getWorks(int userId) {
		
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Work> worksList = null;
		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement("select * from work where user_id = ? ");
			ps.setInt(1, userId);
			rs = ps.executeQuery();

			worksList = new ArrayList<>();

			while (rs.next()) {

				Work work = new Work();
				work.setId(rs.getInt("id"));
				work.setPostedDate(rs.getDate("posted_date"));
				
				User user = new User();
				user.setId(rs.getInt("user_id"));
				work.setPostedBy(user);
				
				ServiceType serviceType = new ServiceType();
				serviceType.setId(rs.getInt("service_id"));
				work.setService(serviceType);
				
				work.setDescription(rs.getString("description"));
				
				if(rs.getString("status").equals("O"))
					work.setStatus(true);
				else
					work.setStatus(false);
				
				worksList.add(work);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			DBUtility.close(con);
		}
		
		return worksList;
	}
	
	
	public boolean changeStatus(Work work){
		Connection con = null;
		PreparedStatement ps = null;
		
		try{			
				con = DBUtility.getConnection();
				ps = con.prepareStatement("update Work set status = ? where id = ?");
				
				if(work.isStatus())
				   ps.setString(1,"C");
				else
					ps.setString(1,"O");
				
				ps.setInt(2,work.getId());
				

				if(ps.executeUpdate()>0)
					return true;					
		}
		catch(SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			DBUtility.close(con);
		}
		
		return false;
		
	}

}
