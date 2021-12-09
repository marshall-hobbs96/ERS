package com.revature.DAL;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;

import javax.sql.rowset.serial.SerialBlob;

import org.postgresql.Driver;

import com.revature.model.ERS_reimbursement;
import com.revature.model.ERS_user;


public class DAL {

	public static Driver postgresDriver = new Driver();
	public static Connection connection; 
	
	
	public DAL() {}
	
	
	public static String establishConnection(String url, String superUsername, String superPassword) {
		
		try {
			
			DriverManager.registerDriver(postgresDriver);
			connection= DriverManager.getConnection(url, superUsername, superPassword);
			return "Connection successfully established with " + url; 
			
		}
		
		catch(SQLException e) {
			
			 return "connection error: " + e.getMessage();
			
		}
		
		
	}	
	
	public ERS_user addUser(ERS_user newUser) throws SQLException{
		String sql = "INSERT INTO ers_users (ers_username, ers_password, user_first_name, user_last_name, user_email, user_role) VALUES (?, ?, ?, ?, ?, ?);";
		
		PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		statement.setString(1, newUser.getErs_username());
		statement.setString(2, newUser.getErs_password());
		statement.setString(3, newUser.getUser_first_name());
		statement.setString(4, newUser.getUser_last_name());
		statement.setString(5, newUser.getUser_email());
		statement.setString(6, newUser.getUser_role());
		
		int numRecordUpdated = statement.executeUpdate();
		
		if(numRecordUpdated != 1) {
			
			throw new SQLException("Adding new user unsuccessful");
			
		}
		
		ResultSet resultSet = statement.getGeneratedKeys();
		resultSet.next();
		int automaticallyGeneratedId = resultSet.getInt(1);
		
		newUser.setUser_id(automaticallyGeneratedId);
		
		return newUser; //method stub
		
	}
	
	public boolean deleteUser(int userId) throws SQLException {
		
		String sql = "DELETE * FROM ers_users WHERE user_id = ?;";
		
		PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setInt(1, userId);
		
		int recordsUpdated = statement.executeUpdate();
		
		if(recordsUpdated != 1) {
			
			return false;
			
		}
		
		
		return true; 
		
	}
	
	public ERS_user updateUser(ERS_user updatedUser) {
		
		return new ERS_user(); //method stub
		
	}
	
	public ArrayList<ERS_user> getAllUsers() throws SQLException {
		
		String sql = "SELECT * FROM ers_users;";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet resultSet = statement.executeQuery();
		
		ArrayList<ERS_user> userList = new ArrayList<>();
		
		while(resultSet.next()) {
			
			ERS_user user = new ERS_user();
			
			user.setUser_id(resultSet.getInt("user_id"));
			user.setUser_first_name(resultSet.getString("user_first_name"));
			user.setUser_last_name(resultSet.getString("user_last_name"));
			user.setUser_role(resultSet.getString("user_role"));
			user.setUser_email(resultSet.getString("user_email"));
			
			userList.add(user);
			
		}
		
		return userList;//method stub
		
	}
	
	public ERS_user getUser(int userID) throws SQLException {
		
		String sql = "SELECT * FROM ers_users WHERE user_id = ?;";
		
		PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		statement.setInt(1, userID);
		
		ResultSet resultSet = statement.executeQuery();
		
		if(resultSet.next() == false) {
			
			throw new SQLException("No user with that ID");
			
		}
		
		ERS_user user = new ERS_user();	//Thought it would be a bit more readable if I did it this way instead of throwing it all in a constructor
		user.setUser_id(userID);
		user.setErs_username(resultSet.getString("ers_username"));
		user.setErs_password(resultSet.getString("ers_password"));
		user.setUser_first_name(resultSet.getString("user_first_name"));
		user.setUser_last_name(resultSet.getString("user_last_name"));
		user.setUser_email(resultSet.getString("user_email"));
		user.setUser_role(resultSet.getString("user_role"));
		
		
		return user; 
		
	}
	
	public boolean checkUsernameTaken(ERS_user newUser) throws SQLException {
		
		String sql = "SELECT * FROM ers_users WHERE ers_username = ?;";
		PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, newUser.getErs_username());
		
		ResultSet resultSet = statement.executeQuery(); 
		
		if(resultSet.next()) {
			
			return true; 
			
		}
		
		return false; 
		
	}
	
	public boolean checkEmailTaken(ERS_user newUser) throws SQLException {
		
		String sql = "SELECT * FROM ers_users WHERE user_email = ?;";
		PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		statement.setString(1, newUser.getUser_email());
		
		ResultSet resultSet = statement.executeQuery();
		
		if(resultSet.next()) {
			
			return true; 
			
		}
		
		
		return false; 
		
	}
	
	public ERS_reimbursement createRequest(ERS_reimbursement newReimbursement, InputStream content) throws SQLException {
		
		String sql = "INSERT INTO ers_reimbursement (reimb_amount, reimb_submitted, reimb_status, reimb_type, reimb_description, reimb_receipt, reimb_author) "
				+ "VALUES (?, ?, ?, ?, ?, ?, ?);";
		
		PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		statement.setDouble(1, newReimbursement.getReimb_amount());
		statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
		statement.setString(3, newReimbursement.getReimb_status());
		statement.setString(4, newReimbursement.getReimb_type());
		statement.setString(5, newReimbursement.getReimb_description());
		statement.setBinaryStream(6, content);
		statement.setInt(7, newReimbursement.getReimb_author());
		
		int recordsUpdated = statement.executeUpdate();
		
		if(recordsUpdated != 1) {
			
			throw new SQLException("Creating new request unsuccessful");
			
		}
		
		ResultSet resultSet = statement.getGeneratedKeys();
		resultSet.next();
		
		newReimbursement.setReimb_id(resultSet.getInt("reimb_id"));
		
		return newReimbursement;
		
		
		
		

		
	}
	
	public boolean deleteRequest(int requestId) {
		
		//method stub 
		return false; 
		
	}
	
	public boolean deleteAllUserRequests(int userId) {
		
		//method stub
		return false; 
		
	}
	
	public ERS_reimbursement getRequest(int requestId) {
		
		return new ERS_reimbursement(); 
		
	}
	
	public ERS_reimbursement updateRequest(ERS_reimbursement updatedReimbursement) throws SQLException {
		
		String sql = "UPDATE ers_reimbursement SET reimb_status = ?, reimb_resolved = ?, reimb_resolver = ? WHERE reimb_id = ?;";
		
		PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		statement.setString(1, updatedReimbursement.getReimb_status());
		statement.setTimestamp(2, Timestamp.valueOf(LocalDateTime.now()));
		statement.setInt(3, updatedReimbursement.getReimb_resolver());
		statement.setInt(4, updatedReimbursement.getReimb_id());
		
		int recordsUpdated = statement.executeUpdate();
		
		if(recordsUpdated != 1) {
			
			throw new SQLException("Unable to update request");
			
		}
		
		
		
		
		return updatedReimbursement; //method stub 
		
	}
	
	public ArrayList<ERS_reimbursement> getAllRequests() throws SQLException {
		
		ArrayList<ERS_reimbursement> reimbursementsList = new ArrayList<>();
		
		String sql = "SELECT * FROM ers_reimbursement";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		ResultSet rs = statement.executeQuery();
		
		while(rs.next()) {
			
			ERS_reimbursement reimb = new ERS_reimbursement();
			
			reimb.setReimb_id(rs.getInt("reimb_id"));
			reimb.setReimb_amount(rs.getDouble("reimb_amount"));
			reimb.setReimb_submitted(rs.getTimestamp("reimb_submitted").toString());
			reimb.setReimb_status(rs.getString("reimb_status"));
			reimb.setReimb_type(rs.getString("reimb_type"));
			reimb.setReimb_description(rs.getString("reimb_description"));
			reimb.setReimb_author(rs.getInt("reimb_author"));
			reimb.setReimb_resolver(rs.getInt("reimb_resolver"));
			InputStream image = rs.getBinaryStream("reimb_receipt");
			
			
			if(rs.getTimestamp("reimb_resolved") != null) {
				
				reimb.setReimb_resolved(rs.getTimestamp("reimb_resolved").toString());
				
			}
			
			reimbursementsList.add(reimb);
			
		}
		
		return reimbursementsList;//method stub 
		
	}
	
	public ArrayList<ERS_reimbursement> getAllRequests(String status) throws SQLException {
		
		ArrayList<ERS_reimbursement> reimbList = new ArrayList<>();
		
		String sql = "SELECT * FROM ers_reimbursement WHERE reimb_status = ?;";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setString(1, status);
		
		ResultSet rs = statement.executeQuery();
		
		while(rs.next()) {
			
			
			ERS_reimbursement reimb = new ERS_reimbursement();
			
			reimb.setReimb_id(rs.getInt("reimb_id"));
			reimb.setReimb_amount(rs.getDouble("reimb_amount"));
			reimb.setReimb_submitted(rs.getTimestamp("reimb_submitted").toString());
			reimb.setReimb_status(rs.getString("reimb_status"));
			reimb.setReimb_type(rs.getString("reimb_type"));
			reimb.setReimb_description(rs.getString("reimb_description"));
			reimb.setReimb_author(rs.getInt("reimb_author"));
			//reimb.setReimb_receipt(new SerialBlob(rs.getBlob("reimb_receipt")));
			
			reimbList.add(reimb);
			
		}
		
		return reimbList; 
		
	}
	
	public ArrayList<ERS_reimbursement> getAllRequests(String status, int userId){
		
		return new ArrayList<ERS_reimbursement>(); //method stub 
		
	}
	
	public ArrayList<ERS_reimbursement> getAllRequests(int userId) throws SQLException {

		String sql = "SELECT * FROM ers_reimbursement WHERE reimb_author = ?;";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setInt(1, userId);
		
		ResultSet rs = statement.executeQuery();
		
		ArrayList<ERS_reimbursement> reimbList = new ArrayList<>();
		
		while(rs.next()) {
			
			ERS_reimbursement reimb = new ERS_reimbursement();
			reimb.setReimb_amount(rs.getDouble("reimb_amount"));
			reimb.setReimb_author(rs.getInt("reimb_author"));
			reimb.setReimb_description(rs.getString("reimb_description"));
			reimb.setReimb_id(rs.getInt("reimb_id"));
			reimb.setReimb_resolver(rs.getInt("reimb_resolver"));
			reimb.setReimb_status(rs.getString("reimb_status"));
			reimb.setReimb_submitted(rs.getTimestamp("reimb_submitted").toString());
			reimb.setReimb_type(rs.getString("reimb_type"));
			
			if(rs.getTimestamp("reimb_resolved") != null) {
				
				reimb.setReimb_resolved(rs.getTimestamp("reimb_resolved").toString());
				
			}
			
			reimbList.add(reimb);
			
			
		}
		
		return reimbList; //method stub 
		
	}
	
	public ERS_user getUserByUsernamePassword(String username, String password) throws SQLException {
		
		String sql = "SELECT * FROM ers_users WHERE ers_username = ? AND ers_password = ?;";
		
		PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		statement.setString(1, username);
		statement.setString(2, password);
		
		ResultSet resultSet = statement.executeQuery();
		
		if(!resultSet.next()) {
			
			throw new SQLException("Invalid login credentials");
			
		}
		
		ERS_user user = new ERS_user();
		user.setUser_id(resultSet.getInt("user_id"));
		user.setErs_username("");
		user.setErs_password("");
		user.setUser_first_name(resultSet.getString("user_first_name"));
		user.setUser_last_name(resultSet.getString("user_last_name"));
		user.setUser_email(resultSet.getString("user_email"));
		user.setUser_role(resultSet.getString("user_role"));
		
		
		return user;
		
	}
	
	public ERS_user getUserByUsername(String username) throws SQLException {
		
		String sql = "SELECT * FROM ers_users WHERE ers_username = ?;";
		
		PreparedStatement statement = connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
		
		statement.setString(1, username);
		
		ResultSet resultSet = statement.executeQuery();
		
		if(!resultSet.next()) {
			
			throw new SQLException("Invalid login credentials");
			
		}
		
		ERS_user user = new ERS_user();
		user.setErs_username(resultSet.getString("ers_username"));
		user.setErs_password(resultSet.getString("ers_password"));
		user.setUser_email(resultSet.getString("user_email"));
		user.setUser_first_name(resultSet.getString("user_first_name"));
		user.setUser_last_name(resultSet.getString("user_last_name"));
		user.setUser_id(resultSet.getInt("user_id"));
		user.setUser_role(resultSet.getString("user_role"));
		
		return user;
		
	}
	
	
	


	public InputStream getRequestImage(int request_id) throws SQLException {
		
		String sql = "SELECT reimb_receipt FROM ers_reimbursement WHERE reimb_id = ?;";
		
		PreparedStatement statement = connection.prepareStatement(sql);
		
		statement.setInt(1, request_id);
		
		ResultSet rs = statement.executeQuery();
		
		if(rs.next()) {
			
			InputStream image = rs.getBinaryStream("reimb_receipt");
			return image; 
			
		}
		
		return null; 
		
	}
}
