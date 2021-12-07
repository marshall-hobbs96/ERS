package com.revature.service;

import java.io.InputStream;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.revature.DAL.DAL;
import com.revature.controller.Controller;
import com.revature.model.ERS_reimbursement;
import com.revature.model.ERS_user;

public class Service {

	private DAL dao; 
	private Logger logger; 
	
	
	public Service(DAL dao) {	
		
		this.dao = dao;
		logger = LoggerFactory.getLogger(Service.class);
		
	}
	
	public void validate_employee(ERS_user user) { //used for validating logged in employee can access feature
		
		//method stub
		
	}
	
	public void validate_manager(ERS_user user) {	//used for validating logged in manager can access feature
		
		//method stub
		
	}
	
	
	public ERS_user createUser(ERS_user newUser) throws SQLException, IllegalArgumentException, NoSuchAlgorithmException {
		
		
		//Ok we need to validate the input of newUser. Make sure all the fields are what we want them to be. 
		
		
		//Probably gunna move all this to a helper function. Im probably gunna have to use most of it again anyways for other user altering methods. 
		
		if ((newUser.getUser_role().compareTo("employee") != 0) && (newUser.getUser_role().compareTo("manager") != 0)) {
			
			throw new IllegalArgumentException("Unable to create new user. User role is invalid. Available roles include 'employee' and 'manager'");
			
		} else if(newUser.getErs_username() == null || newUser.getErs_username().compareTo("") == 0) {
			
			throw new IllegalArgumentException("Cannot create user. Username cannot be null or empty. Please specify a username");
			
//		} else if(whitespaceMatcher.find()) {	//This will return true if username has whitespace in it. We've previously trimmed it, so whitespace now means its inbetween characters. No good. 
//			
//			throw new IllegalArgumentException("Cannot create user. Username cannot have whitespace between characters.");
			
		} else if(newUser.getErs_password() == null || newUser.getErs_password().compareTo("") == 0) {
			
			throw new IllegalArgumentException("Cannot create user. Password cannot be null or empty. Please specify a password");
			
		} else if(newUser.getUser_first_name() == null || newUser.getUser_first_name().compareTo("") == 0) {
			
			throw new IllegalArgumentException("Cannot create user. First name cannot be null or empty. Please specify a first name");
			
		} else if(newUser.getUser_last_name() == null || newUser.getUser_last_name().compareTo("") == 0) {
			
			throw new IllegalArgumentException("Cannot create user. Last name cannot be null or empty. Please specify a last name");
			
		} else if(newUser.getUser_email() == null || newUser.getUser_email().compareTo("") == 0) {
			
			throw new IllegalArgumentException("Cannot create user. Email cannot be null or empty. Please specify a valid email.");
			
//		} else if(!emailMatcher.find()) {
//			
//			throw new IllegalArgumentException("Unable to create new user. Email is not valid. Please specify a valid email with a valid domain name");
			
		} else if(newUser.getErs_username().length() > 255) {
			
			throw new IllegalArgumentException("Cannot create user. Username exceeds acceptable number of characters[255]");
			
		} else if(newUser.getErs_password().length() > 255) {
			
			throw new IllegalArgumentException("Cannot create user. Password exceeds acceptable number of characters[255]");
			
		} else if (newUser.getUser_first_name().length() > 255) {
			
			throw new IllegalArgumentException("Cannot create user. First name exceeds acceptable number of characters[255]");
			
		} else if (newUser.getUser_last_name().length() > 255) {
			
			throw new IllegalArgumentException("Cannot create user. Last name exceeds acceptable number of characters[255]");
			
		} else if(newUser.getUser_email().length() > 255) {
			
			throw new IllegalArgumentException("Cannot create user. Email exceeds acceptable number of characters[255]");
			
		}
		
		//trim username, firstname, lastname, email
		//not trimming password, because a password should be able to have whitespace at the end/beginning if it wants
		//We are trimming all these other variables, because theres no reason they should have whitespace
		//Gotta trim down here, otherwise will throw exception if trying to trim a null value. We check for null values above
		newUser.setErs_username(newUser.getErs_username().trim());
		newUser.setUser_first_name(newUser.getUser_first_name().trim());
		newUser.setUser_last_name(newUser.getUser_last_name().trim());
		newUser.setUser_email(newUser.getUser_email().trim());
		
		Pattern whitespacePattern = Pattern.compile("\\s");
		Matcher whitespaceMatcher = whitespacePattern.matcher(newUser.getErs_username());
		
		Pattern emailPattern = Pattern.compile("/^\\S+@\\S+\\.\\S+$/"); //This is the same regex I used in the frontend. Hopefully it works here too
		Matcher emailMatcher = emailPattern.matcher(newUser.getUser_email());
		
		if(whitespaceMatcher.find()) {	//This will return true if username has whitespace in it. We've previously trimmed it, so whitespace now means its inbetween characters. No good. 
			
		throw new IllegalArgumentException("Cannot create user. Username cannot have whitespace between characters.");
		
		} 
		
		whitespaceMatcher = whitespacePattern.matcher(newUser.getUser_email());
		
		if(whitespaceMatcher.find()) {
			
			throw new IllegalArgumentException("Cannot create user. Email cannot have whitespace between characters.");
			
		} 
		
		//Need to add username/password hashing here
		//Need to salt with email too
		
		String usernameWithSalt = newUser.getErs_username(); //Im an idiot, you dont salt the username, duh
		String passwordWithSalt = newUser.getErs_password() + newUser.getUser_email();
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(usernameWithSalt.getBytes(StandardCharsets.UTF_8));
		byte[] digest = md.digest();
		
		newUser.setErs_username(String.format("%064x", new BigInteger(1, digest)));
		
		md.update(passwordWithSalt.getBytes(StandardCharsets.UTF_8));
		byte[] digest2 = md.digest();
		
		newUser.setErs_password(String.format("%064x", new BigInteger(1, digest2)));
		
		if(dao.checkEmailTaken(newUser)) {
			
			throw new IllegalArgumentException("Unable to create new user. Email is taken. Please specify a different email");
			
		} else if(dao.checkUsernameTaken(newUser)) {
			
			throw new IllegalArgumentException("Unable to create new user. Username is taken. Please specify a different username");
			
		}
		
		
		
		newUser = dao.addUser(newUser);
		ERS_user returnUser = dao.getUser(newUser.getUser_id());
		returnUser.setErs_password("");
		returnUser.setErs_username("");
		return returnUser;
		
		
	}
	
	public ERS_user elevateUser(int userID) {
		
		return new ERS_user();
		
	}
	
	
	public boolean deleteUser(int user_id) throws SQLException {
		
		return dao.deleteUser(user_id);
	
		
	}
	
	public ERS_user updateUser(ERS_user updatedUser) {
		
		return new ERS_user(); //method stub
		
	}
	
	public ArrayList<ERS_user> getAllUsers() throws SQLException {
		
		return dao.getAllUsers(); 
		
	}
	
	public ERS_user getUser(int user_id) throws SQLException {
		
		ERS_user returnUser = dao.getUser(user_id);
		returnUser.setErs_username("");
		returnUser.setErs_password("");
		
		return returnUser; //Method stub
		
	}
	
	public ERS_user getUserByUsernamePassword(ERS_user user) throws NoSuchAlgorithmException, SQLException {
		
		
		String usernameWithSalt = user.getErs_username();
		
		MessageDigest md = MessageDigest.getInstance("SHA-256");
		md.update(usernameWithSalt.getBytes(StandardCharsets.UTF_8));
		byte[] digest = md.digest();
		
		user.setErs_username(String.format("%064x", new BigInteger(1, digest)));
		
		
		ERS_user actualUser = dao.getUserByUsername(user.getErs_username());
		
		String passwordWithSalt = user.getErs_password() + actualUser.getUser_email();
		
		md.update(passwordWithSalt.getBytes(StandardCharsets.UTF_8));
		byte[] digest2 = md.digest();
		
		user.setErs_password(String.format("%064x", new BigInteger(1, digest2)));
	
		
		
		if(actualUser.getErs_password().compareTo(user.getErs_password()) != 0) {
			
			throw new SQLException("Invalid login credentials");
			
		}
		
		actualUser.setErs_username("");
		actualUser.setErs_password("");
		
		return actualUser; //method stub
		
	}
	
	
	public ERS_reimbursement createRequest(ERS_reimbursement newReimbursement, InputStream content, ERS_user currentUser) throws SQLException, IllegalArgumentException  {
		
		
		if(newReimbursement.getReimb_amount() <= 0) {
			
			throw new IllegalArgumentException("Unable to create request. Reimbursement amount cannot equal to or less than 0. Please enter a valid reimbursement amount");
			
		} else if((newReimbursement.getReimb_type().compareTo("LODGING") != 0) && (newReimbursement.getReimb_type().compareTo("TRAVEL") != 0) && (newReimbursement.getReimb_type().compareTo("FOOD") != 0) && (newReimbursement.getReimb_type().compareTo("OTHER") != 0)) {
			
			logger.info(newReimbursement.getReimb_type());
			throw new IllegalArgumentException("Unable to create request. Reimbursement type is not valid. Must be 'LODGING', 'TRAVEL', 'FOOD' or 'OTHER'");
			
			
		} else if(newReimbursement.getReimb_description().length() > 255) {
			
			throw new IllegalArgumentException("Unable to create request. Reimbursement description is too long[255]");
			
		} else if(currentUser.getUser_id() == 0) {
			
			throw new IllegalArgumentException("Unable to create request. Must be logged in to create request");
			
		}
		
		newReimbursement.setReimb_submitted(LocalDateTime.now().toString());
		newReimbursement.setReimb_status("PENDING");
		newReimbursement.setReimb_author(currentUser.getUser_id());;
		
		return dao.createRequest(newReimbursement, content);	

		
	}
	
	public boolean deleteRequest(int requestId) {
		
		//method stub
		return false; 
		
	}
	
	public ERS_reimbursement updateRequest(ERS_reimbursement updatedReimbursement, ERS_user currentUser) throws IllegalArgumentException, SQLException {
		
		if(currentUser.getUser_role().compareTo("manager") != 0) {
			
			throw new IllegalArgumentException("Unable to update request. Must be logged in as a manager to update requests");
			
		} else if((updatedReimbursement.getReimb_status().compareTo("APPROVED") != 0) && (updatedReimbursement.getReimb_status().compareTo("DENIED") != 0)) {
			
			throw new IllegalArgumentException("Unable to update request. Status not valid. Valid status is 'approved' and 'denied'");
			
		}
		
		updatedReimbursement.setReimb_resolver(currentUser.getUser_id());
		
		dao.getRequest(updatedReimbursement.getReimb_id());
		
		dao.updateRequest(updatedReimbursement);
		
		return dao.getRequest(updatedReimbursement.getReimb_id());
		
		
	}
	
	public ERS_reimbursement getRequest(int requestID) {
		
		return new ERS_reimbursement();
		
	}
	
	public ArrayList<ERS_reimbursement> getAllRequests(ERS_user currentUser) throws SQLException {
		
		ArrayList<ERS_reimbursement> reimbursementsList = new ArrayList<>();
		
		if(currentUser.getUser_role().compareTo("employee") == 0) {	//get all reimbursements for just this employee
			
			reimbursementsList = dao.getAllRequests(currentUser.getUser_id());
			logger.info("Get all requests for employee invoked");
			
		} else if(currentUser.getUser_role().compareTo("manager") == 0) {	//Get all reimbursements
			
			reimbursementsList = dao.getAllRequests();
			logger.info("Get all requests for manager invoked");
			
		} else {	//role not recognized, throw exception
			
			throw new IllegalArgumentException("Cannot retrieve requests. User role is not valid");
			
		}
		
		
		return reimbursementsList; 
		
	}
	
	public ArrayList<ERS_reimbursement> getAllRequests(String status, ERS_user currentUser) throws SQLException {
		
		if(currentUser.getUser_role().compareTo("manager") != 0) {
			
			throw new IllegalArgumentException("Unable to get reimbursements. User role must be manager");
			
		} else if((status.compareTo("PENDING") != 0) && (status.compareTo("APPROVED") != 0) && (status.compareTo("DENIED") != 0)) {
			
			throw new IllegalArgumentException("Unable to get requests. Invalid status. Please search by a valid status");
			
		} 
			
		
		ArrayList<ERS_reimbursement> reimbList = dao.getAllRequests(status);
		
		return reimbList; //method stub
		
	}	
	

	

	
}
