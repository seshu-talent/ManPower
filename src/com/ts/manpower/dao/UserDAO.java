package com.ts.manpower.dao;

import java.sql.*;

import com.ts.manpower.dto.*;

public class UserDAO {

	public static boolean register(User user) {
		Connection con = null;
		PreparedStatement ps = null;
		
		if(search(user.getEmailId()))
			return false;
		
		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement("insert into user(name,password,email,register_date,mobile_number) values(?,?,?,?,?)");
			
			ps.setString(1, user.getName());
			ps.setString(2, user.getPassword());
			ps.setString(3, user.getEmailId());
			ps.setDate(4, new java.sql.Date(System.currentTimeMillis()));
			ps.setLong(5, user.getMobileNumber());
			
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
	
	
	private static boolean search(String email)
	{
		Connection con = null;
		PreparedStatement ps = null;
		
		
		try {

			con = DBUtility.getConnection();			
			ps = con.prepareStatement("select * from user where email = ?");
			ps.setString(1, email);
			
			ResultSet rs = ps.executeQuery();
			
			if (rs.next())
				return true;			
		} 
		catch (SQLException e) {
			e.printStackTrace();			
		}		
		
		finally {
			DBUtility.close(con);
		}
		
		return false;
	}
	

	public static User login(String email,String password) {
		
		Connection con = null;
		PreparedStatement ps = null;
		User user = null;
		
		try {

			con = DBUtility.getConnection();			
			ps = con.prepareStatement("select * from user where email = ? and password = ?");
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();
			
			if (rs.next()) 
			{
				user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setEmailId(rs.getString("email_id"));
				user.setMobileNumber(rs.getLong("mobile_number"));
				user.setRegistrationDate(rs.getDate("register_date"));
				
				user.setWorksList(new WorkDAO().getWorks(rs.getInt("id")));				
				user.setAddressList(new AddressDAO().getAddressList(rs.getInt("id")));
				
				return user;
			}
							
		} 
		catch (SQLException e) {
			e.printStackTrace();			
		}		
		
		finally {
			DBUtility.close(con);
		}
		
		return user;
	}
	
	
	public boolean rateUser(Rating rating){
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement("insert into rating values(?,?,?,?,?)");
			
			ps.setInt(1, rating.getFromUserId());
			ps.setInt(2, rating.getToUserId());
			ps.setInt(3, rating.getRating());
			ps.setString(4,rating.getComments());
			ps.setLong(5,rating.getService_id());
			
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
	

	public User get(int userId) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement("select * from user where id = ?");
			ps.setInt(1, userId);
			rs = ps.executeQuery();

			if (rs.next()) {
				user = new User();
				user.setId(rs.getInt("id"));
				user.setName(rs.getString("name"));
				user.setPassword(rs.getString("password"));
				user.setEmailId(rs.getString("email_id"));
				user.setMobileNumber(rs.getLong("mobile_number"));
				user.setRegistrationDate(rs.getDate("register_date"));
				
				user.setWorksList(new WorkDAO().getWorks(userId));				
				user.setAddressList(new AddressDAO().getAddressList(userId));
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			DBUtility.close(con);
		}

		return user;
	}

	
	public User get(String email) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		User user = null;
		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement("select * from user where email_id = ?");
			ps.setString(1, email);
			rs = ps.executeQuery();

			if (rs.next()) 
				user = get(rs.getInt("id"));			
			
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			DBUtility.close(con);
		}

		return user;
	}

	

	public boolean updatePassword(String password, int userId) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement("update user set password = ? where id = ?");
			ps.setString(1, password);
			ps.setInt(2, userId);
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
}
