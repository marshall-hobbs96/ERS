package com.revature.DAL;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
	
	public ERS_reimbursement createRequest(ERS_reimbursement newReimbursement) {
		
		return new ERS_reimbursement(); //method stub 
		
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
	
	public ERS_reimbursement updateRequest(ERS_reimbursement updatedReimbursement) {
		
		return new ERS_reimbursement(); //method stub 
		
	}
	
	public ArrayList<ERS_reimbursement> getAllRequests() {
		
		return new ArrayList<ERS_reimbursement>();//method stub 
		
	}
	
	public ArrayList<ERS_reimbursement> getAllRequests(String status) {
		
		return new ArrayList<ERS_reimbursement>(); ///method stub
		
	}
	
	public ArrayList<ERS_reimbursement> getAllRequests(String status, int userId){
		
		return new ArrayList<ERS_reimbursement>(); //method stub 
		
	}
	
	public ArrayList<ERS_reimbursement> getAllRequests(int userId) {

		
		return new ArrayList<ERS_reimbursement>(); //method stub 
		
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
		
		return user;
		
	}
}
