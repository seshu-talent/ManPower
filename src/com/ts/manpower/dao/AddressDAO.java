package com.ts.manpower.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.ts.manpower.dto.*;

public class AddressDAO {
	
	public Address add(Address address) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBUtility.getConnection();
			String stmt = "insert into address(door_no,street,city,state,country,postal_code,deleted) values(?,?,?,?,?,0)";

			ps = con.prepareStatement(stmt, PreparedStatement.RETURN_GENERATED_KEYS);
			ps.setString(1, address.getDoorno());
			ps.setString(2, address.getStreet());
			ps.setString(3, address.getCity());
			ps.setString(4, address.getState());
			ps.setLong(5, address.getPostalCode());

			if (ps.executeUpdate() > 0) {

				ResultSet rs = ps.getGeneratedKeys();
				if (rs.next()) {
					address.setId(rs.getInt(1));
				}

				return address;
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

	public boolean delete(int addressId) {
		Connection con = null;
		PreparedStatement ps = null;
		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement("update address set deleted=1 where id = ?");
			ps.setInt(1, addressId);
			if (ps.executeUpdate() > 0) {
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

	public Address get(int addressId) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		Address address = null;
		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement("select * from address where id = ?");
			ps.setInt(1, addressId);
			rs = ps.executeQuery();
			if (rs.next()) {
				address = new Address();
				address.setId(rs.getInt("id"));
				address.setDoorno(rs.getString("door_no"));
				address.setStreet(rs.getString("street"));
				address.setCity(rs.getString("city"));
				address.setState(rs.getString("state"));
				address.setCountry(rs.getString("country"));
				address.setCountry(rs.getString("postal_code"));

				if (rs.getInt("deleted") != 0)
					address.setDeleted(true);
				else
					address.setDeleted(false);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			DBUtility.close(con);
		}
		
		return address;
	}

	public List<Address> getAddressList(int userId) {
		Connection con = null;
		PreparedStatement ps = null;
		ResultSet rs = null;
		List<Address> addressList = null;
		try {
			con = DBUtility.getConnection();
			ps = con.prepareStatement("select * from address where user_id = ? ");
			ps.setInt(1, userId);			
			rs = ps.executeQuery();

			addressList = new ArrayList<>();

			while (rs.next()) {

				Address address = new Address();
				address.setId(rs.getInt("id"));
				address.setDoorno(rs.getString("door_no"));
				address.setStreet(rs.getString("street"));
				address.setCity(rs.getString("city"));
				address.setState(rs.getString("state"));
				address.setCountry(rs.getString("country"));
				address.setCountry(rs.getString("postal_code"));

				if (rs.getInt("deleted") != 0)
					address.setDeleted(true);
				else
					address.setDeleted(false);
				
				addressList.add(address);
			}
		} 
		catch (SQLException e) {
			e.printStackTrace();
		}
		
		finally {
			DBUtility.close(con);
		}
		
		return addressList;
	}

}
