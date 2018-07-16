package com.ts.manpower.dao;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import com.ts.manpower.dto.*;

public class ForceDAO {

	public Force login(String email, String password) {

		Connection con = null;
		PreparedStatement ps = null;
		Force force = null;

		try {

			con = DBUtility.getConnection();
			ps = con.prepareStatement("select * from users where email = ? and password = ? and id in(select user_id from skill)");
			ps.setString(1, email);
			ps.setString(2, password);
			ResultSet rs = ps.executeQuery();

			if (rs.next()) {
				
				force = new Force();
				force.setId(rs.getInt("id"));
				force.setName(rs.getString("name"));
				force.setPassword(rs.getString("password"));
				force.setEmailId(rs.getString("email_id"));
				force.setMobileNumber(rs.getLong("mobile_number"));
				force.setRegistrationDate(rs.getDate("register_date"));
				force.setAadharImage(rs.getString("aadhar_image"));		
				
			List<Skill> skillsList = getSkillsList(force);
			force.setSkillsList(skillsList);

			List<WorkReference> workReferencesList = getWorkReferencesList(force);
			force.setWorkReferencesList(workReferencesList);

			List<Bid> bidsList = getBidsList(force);
			force.setBidsList(bidsList);
				
			
			
				return force;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			DBUtility.close(con);
		}

		return force;
	}
	
	
	public static boolean register(Force force,Skill skill,WorkReference reference) {
		Connection con = null;
		PreparedStatement ps = null;	
		
		if(search(force.getEmailId()))
			return false;
		
		try {
			con = DBUtility.getConnection();
			
			ps = con.prepareStatement("insert into user(name,password,email,register_date,mobile_number,aadhar_image) values(?,?,?,?,?,?)");
			
			ps.setString(1, force.getName());
			ps.setString(2, force.getPassword());
			ps.setString(3, force.getEmailId());
			ps.setDate(4, new java.sql.Date(System.currentTimeMillis()));
			ps.setLong(5, force.getMobileNumber());
			ps.setString(6, force.getAadharImage());
			//ps.setString(6, force.getName() + "_" + force.getMobileNumber() + ".jpg");
			
			if (ps.executeUpdate() > 0) {				
				
			//	if(new ForceDAO().add(skill))
				//	if(new ForceDAO().add(reference))
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
	

	public boolean add(Skill skill) {

		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = DBUtility.getConnection();
			ps = con.prepareStatement("insert into skill(user_id,service_id, experience, certificate_path,approved) values(?,?,?,'N')");
			ps.setInt(1, skill.getUserId());
			ps.setInt(1, skill.getServiceId());
			ps.setInt(2, skill.getExperience());
			ps.setString(3, skill.getUserId() + "_certificate_" + skill.getServiceId() + ".jpg");

			if (ps.executeUpdate() > 0)
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
	

	public boolean add(WorkReference reference) {

		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = DBUtility.getConnection();
			ps = con.prepareStatement("insert into reference values(?,?,?,?,?,?,?,?,?)");
			ps.setString(1, reference.getFirstName());
			ps.setString(2, reference.getLastName());
			ps.setLong(3, reference.getMobileNumber());
			ps.setString(4, reference.getEmailId());

			ServiceType serviceType = new ServiceType();
			serviceType.setId(reference.getServiceType().getId());

			ps.setInt(5, serviceType.getId());
			ps.setDate(6, (Date) reference.getServiceDate());
			ps.setInt(7, reference.getUser_id());
			ps.setString(8, String.valueOf(reference.getVerifiedThrough()));
			ps.setInt(9, reference.getRating());

			if (ps.executeUpdate() > 0)
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
	

	public boolean add(Bid bid) {

		Connection con = null;
		PreparedStatement ps = null;

		try {

			con = DBUtility.getConnection();
			ps = con.prepareStatement("insert into bid values(?,?,?,?,'N',?)");
			ps.setInt(1, bid.getWork().getId());
			ps.setFloat(2, bid.getBidPrice());
			ps.setDate(3,new java.sql.Date(System.currentTimeMillis()));
			ps.setInt(4, bid.getUserId());
			ps.setString(5, String.valueOf(bid.getStatus()));

			if (ps.executeUpdate() > 0)
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

	public Force get(int userId) {

		Connection con = null;
		PreparedStatement ps = null;

		ResultSet rs = null;
		Force force = null;

		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement("select * from user where id = ?");
			ps.setInt(1, userId);
			rs = ps.executeQuery();

			if (rs.next()) {
				force = new Force();
				force.setId(rs.getInt("id"));
				force.setName(rs.getString("name"));
				force.setPassword(rs.getString("password"));
				force.setEmailId(rs.getString("email_id"));
				force.setMobileNumber(rs.getLong("mobile_number"));
				force.setRegistrationDate(rs.getDate("register_date"));
				force.setAadharImage(rs.getString("aadhar_image"));				
			

			List<Skill> skillsList = getSkillsList(force);
			force.setSkillsList(skillsList);

			List<WorkReference> workReferencesList = getWorkReferencesList(force);
			force.setWorkReferencesList(workReferencesList);

			List<Bid> bidsList = getBidsList(force);
			force.setBidsList(bidsList);

			return force;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			DBUtility.close(con);
		}

		return force;
	}

	public Force get(String email) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Force force = null;
		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement("select id from user where email_id = ?");
			ps.setString(1, email);

			rs = ps.executeQuery();

			if (rs.next()) {
				force = get(rs.getInt("id"));
			}

			return force;
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			DBUtility.close(con);
		}

		return force;
	}
	
	
	public boolean approveForce(int userId){
		Connection con = null;
		PreparedStatement ps = null;
		
		try{			
				con = DBUtility.getConnection();
				ps = con.prepareStatement("update skill set approved = 'Y' where user_id = ?");
				ps.setInt(1, userId);				

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
	

	public List<Skill> getSkillsList(Force force) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Skill> skillsList = null;

		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement("select * from skill where user_id=? and approved='Y'");
			ps.setInt(1, force.getId());
			rs = ps.executeQuery();

			skillsList = new ArrayList<>();

			while (rs.next()) {

				Skill skill = new Skill();
				skill.setServiceId(rs.getInt("service_id"));
				skill.setExperience(rs.getInt("experience"));
				skill.setCertificateImage(rs.getString("certificate_image"));
				skill.setApproved(true);
				skillsList.add(skill);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			DBUtility.close(con);
		}

		return skillsList;

	}

	public List<WorkReference> getWorkReferencesList(Force force) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<WorkReference> workReferencesList = null;
		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement("select * from reference where deleted = 0 and user_id = ?) ");
			ps.setInt(1, force.getId());
			rs = ps.executeQuery();

			workReferencesList = new ArrayList<>();

			while (rs.next()) {

				WorkReference workReference = new WorkReference();
				workReference.setFirstName(rs.getString("first_name"));
				workReference.setLastName(rs.getString("last_name"));
				workReference.setMobileNumber(rs.getLong("mobile_number"));

				ServiceType service = new ServiceType();
				service.setId(rs.getInt("service_id"));

				workReference.setServiceType(service);
				workReference.setServiceDate(rs.getDate("service_date"));

				workReference.setVerifiedThrough(rs.getString("verified_through").charAt(0));

				if (rs.getInt("deleted") != 0)
					workReference.setDeleted(true);
				else
					workReference.setDeleted(false);

				workReferencesList.add(workReference);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			DBUtility.close(con);
		}

		return workReferencesList;

	}

	public List<Bid> getBidsList(Force force) {

		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Bid> bidsList = null;
		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement("select * from bid where user_id = ?) ");
			ps.setInt(1, force.getId());
			rs = ps.executeQuery();

			bidsList = new ArrayList<>();

			while (rs.next()) {

				Bid bid = new Bid();
				bid.setBidPrice(rs.getFloat("bid_price"));
				bid.setBidDate(rs.getDate("bid_date"));
				bid.setComments(rs.getString("comments"));

				Work work = new Work();
				work.setId(rs.getInt("work_id"));
				bid.setWork(work);

				bid.setStatus(rs.getString("status").charAt(0));

				bidsList.add(bid);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			DBUtility.close(con);
		}

		return bidsList;
	}

}
