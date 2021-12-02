package com.revature.service;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.ArrayList;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;

import com.revature.DAL.DAL;
import com.revature.model.ERS_reimbursement;
import com.revature.model.ERS_user;

import org.junit.jupiter.api.MethodOrderer.MethodName;

@TestMethodOrder(MethodName.class)
public class ServiceTest {

	private Service sut;
	private DAL mockDao;
	private String tooLongString = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
			+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa"
			+ "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaa";
	//Is there a more elegant way to do this? yes, but this is more fun 
	
	@BeforeEach
	public void DaoAndServiceSetup() {
		
		mockDao = mock(DAL.class);
		sut = new Service(mockDao);
		
	}
	
	/**********************************************BEGIN CREATE USER TESTS
	 * @throws SQLException 
	 * @throws IllegalArgumentException 
	 * @throws NoSuchAlgorithmException ************************************/
	
	@Test
	public void createUserPositiveNewEmployee() throws IllegalArgumentException, SQLException, NoSuchAlgorithmException {	
		
		ERS_user newEmployee = new ERS_user("username", "password", "John", "Doe", "email@revature.net", "employee");
		ERS_user expectedResult = new ERS_user("1809aec60e165c214dd7b02d1f1b42ada7674d6a14ad70ba44ffd7b0641436bf", 
				"61d1cb660b73116e65aeb8c90a1350bed4ded1e145ba72215e941e778c8f022c", "John", "Doe", "email@revature.net", "employee");
		
		newEmployee.setUser_id(1);
		expectedResult.setUser_id(1);
		
		when(mockDao.addUser(eq(newEmployee))).thenReturn(expectedResult);
		when(mockDao.checkUsernameTaken(eq(newEmployee))).thenReturn(false);
		when(mockDao.checkEmailTaken(eq(newEmployee))).thenReturn(false);
		when(mockDao.getUser(eq(1))).thenReturn(expectedResult);
		
		assertEquals(expectedResult, sut.createUser(newEmployee));
		

	}
	
	@Test
	public void createUserPositiveNewManager() throws IllegalArgumentException, SQLException, NoSuchAlgorithmException {
		
		ERS_user newManager = new ERS_user("username2", "passsword2", "John", "Doe", "email2@revature.net", "manager");
		ERS_user expectedResult = new ERS_user("091e05e30c63fc1d1ad188ce26e2b829dabab647bb388128745abfae88f06cee", 
				"78f1053bf4fbdf59e29dee7dee93907fb7954ce2702acbb74ba833b1febc65df", "John", "Doe", "email2@revature.net", "manager");
		
		newManager.setUser_id(1);
		expectedResult.setUser_id(1);
		
		when(mockDao.addUser(eq(newManager))).thenReturn(expectedResult);
		when(mockDao.checkUsernameTaken(eq(newManager))).thenReturn(false);
		when(mockDao.checkEmailTaken(eq(newManager))).thenReturn(false);
		when(mockDao.getUser(eq(1))).thenReturn(expectedResult);
		
		assertEquals(expectedResult, sut.createUser(newManager));
		
	}
	
	@Test
	public void createUserNegativeInvalidUserRole() throws SQLException {
		
		ERS_user newUser = new ERS_user("username", "password", "John", "Doe", "email@revature.net", "admin");
		newUser.setUser_id(1);
		when(mockDao.checkUsernameTaken(eq(newUser))).thenReturn(false);
		when(mockDao.checkEmailTaken(eq(newUser))).thenReturn(false);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.createUser(newUser);
			
		});
		
		assertEquals("Unable to create new user. User role is invalid. Available roles include 'employee' and 'manager'", e.getMessage());
		
	}
	
	@Test
	public void createUserNegativeEmptyUsername() throws SQLException {
		ERS_user newUser = new ERS_user("", "password", "John", "Doe", "email@revature.net", "employee");
		newUser.setUser_id(1);
		when(mockDao.checkUsernameTaken(eq(newUser))).thenReturn(false);
		when(mockDao.checkEmailTaken(eq(newUser))).thenReturn(false);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.createUser(newUser);
			
		});
		
		assertEquals("Cannot create user. Username cannot be null or empty. Please specify a username", e.getMessage());
		
	}
	
	@Test
	public void createUserNegativeNullUsername() throws SQLException {
		
		ERS_user newUser = new ERS_user(null, "password", "John", "Doe", "email@revature.net", "employee");
		newUser.setUser_id(1);
		when(mockDao.checkUsernameTaken(eq(newUser))).thenReturn(false);
		when(mockDao.checkEmailTaken(eq(newUser))).thenReturn(false);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.createUser(newUser);
			
		});
		
		assertEquals("Cannot create user. Username cannot be null or empty. Please specify a username", e.getMessage());
		
		
	}
	
	@Test
	public void createUserNegativeEmptyPassword() throws SQLException {

		ERS_user newUser = new ERS_user("username", "", "John", "Doe", "email@revature.net", "employee");
		newUser.setUser_id(1);
		when(mockDao.checkUsernameTaken(eq(newUser))).thenReturn(false);
		when(mockDao.checkEmailTaken(eq(newUser))).thenReturn(false);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.createUser(newUser);
			
		});
		
		assertEquals("Cannot create user. Password cannot be null or empty. Please specify a password", e.getMessage());
		
		
	}
	
	@Test
	public void createUserNegativeNullPassword() throws SQLException {
		
		ERS_user newUser = new ERS_user("username", null, "John", "Doe", "email@revature.net", "employee");
		newUser.setUser_id(1);
		when(mockDao.checkUsernameTaken(eq(newUser))).thenReturn(false);
		when(mockDao.checkEmailTaken(eq(newUser))).thenReturn(false);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.createUser(newUser);
			
		});
		
		assertEquals("Cannot create user. Password cannot be null or empty. Please specify a password", e.getMessage());
		
	}
	
	@Test
	public void createUserNegativeEmptyFirstname() throws SQLException {
		ERS_user newUser = new ERS_user("username", "password", "", "Doe", "email@revature.net", "employee");
		newUser.setUser_id(1);
		when(mockDao.checkUsernameTaken(eq(newUser))).thenReturn(false);
		when(mockDao.checkEmailTaken(eq(newUser))).thenReturn(false);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.createUser(newUser);
			
		});
		
		assertEquals("Cannot create user. First name cannot be null or empty. Please specify a first name", e.getMessage());
	}
	
	@Test
	public void createUserNegativeNullFirstname() throws SQLException {
		ERS_user newUser = new ERS_user("username", "password", null, "Doe", "email@revature.net", "employee");
		newUser.setUser_id(1);
		when(mockDao.checkUsernameTaken(eq(newUser))).thenReturn(false);
		when(mockDao.checkEmailTaken(eq(newUser))).thenReturn(false);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.createUser(newUser);
			
		});
		
		assertEquals("Cannot create user. First name cannot be null or empty. Please specify a first name", e.getMessage());
	}
	
	@Test
	public void createUserNegativeEmptyLastname() throws SQLException {
		ERS_user newUser = new ERS_user("username", "password", "John", "", "email@revature.net", "employee");
		newUser.setUser_id(1);
		when(mockDao.checkUsernameTaken(eq(newUser))).thenReturn(false);
		when(mockDao.checkEmailTaken(eq(newUser))).thenReturn(false);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.createUser(newUser);
			
		});
		
		assertEquals("Cannot create user. Last name cannot be null or empty. Please specify a last name", e.getMessage());
	}
	
	@Test
	public void createUserNegativeNullLastname() throws SQLException {
		ERS_user newUser = new ERS_user("username", "password", "John", null, "email@revature.net", "employee");
		newUser.setUser_id(1);
		when(mockDao.checkUsernameTaken(eq(newUser))).thenReturn(false);
		when(mockDao.checkEmailTaken(eq(newUser))).thenReturn(false);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.createUser(newUser);
			
		});
		
		assertEquals("Cannot create user. Last name cannot be null or empty. Please specify a last name", e.getMessage());
	}
	
	@Test 
	public void createUserNegativeEmptyEmail() throws SQLException {
		ERS_user newUser = new ERS_user("username", "password", "John", "Doe", "", "employee");
		newUser.setUser_id(1);
		when(mockDao.checkUsernameTaken(eq(newUser))).thenReturn(false);
		when(mockDao.checkEmailTaken(eq(newUser))).thenReturn(false);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.createUser(newUser);
			
		});
		
		assertEquals("Cannot create user. Email cannot be null or empty. Please specify a valid email.", e.getMessage());
	}
	
	@Test
	public void createUserNegativeNullEmail() throws SQLException {
		ERS_user newUser = new ERS_user("username", "password", "John", "Doe", null, "employee");
		newUser.setUser_id(1);
		when(mockDao.checkUsernameTaken(eq(newUser))).thenReturn(false);
		when(mockDao.checkEmailTaken(eq(newUser))).thenReturn(false);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.createUser(newUser);
			
		});
		
		assertEquals("Cannot create user. Email cannot be null or empty. Please specify a valid email.", e.getMessage());
	}
	
	@Test
	public void createUserNegativeNoEmailDomain() throws SQLException {		//I.e., email give doesn't have an @....com, .net etc. 
		ERS_user newUser = new ERS_user("username", "password", "John", "Doe", "email", "employee");
		newUser.setUser_id(1);
		when(mockDao.checkUsernameTaken(eq(newUser))).thenReturn(false);
		when(mockDao.checkEmailTaken(eq(newUser))).thenReturn(false);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.createUser(newUser);
			
		});
		
		assertEquals("Unable to create new user. Email is not valid. Please specify a valid email with a valid domain name", e.getMessage());
	}
	
	@Test
	public void createUserNegativeUsernameTaken() throws SQLException {
		ERS_user newUser = new ERS_user("username", "password", "John", "Doe", "email@revature.net", "employee");
		newUser.setUser_id(1);
		
		
		when(mockDao.checkUsernameTaken(eq(newUser))).thenReturn(true);
		when(mockDao.checkEmailTaken(eq(newUser))).thenReturn(false);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.createUser(newUser);
			
		});
		
		assertEquals("Unable to create new user. Username is taken. Please specify a different username", e.getMessage());
	}
	
	@Test
	public void createUserNegativeEmailTaken() throws SQLException {
		ERS_user newUser = new ERS_user("username", "password", "John", "Doe", "email@revature.net", "employee");
		newUser.setUser_id(1);
		
		when(mockDao.checkUsernameTaken(eq(newUser))).thenReturn(false);
		when(mockDao.checkEmailTaken(eq(newUser))).thenReturn(true);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.createUser(newUser);
			
		});
		
		assertEquals("Unable to create new user. Email is taken. Please specify a different email", e.getMessage());
	}
	
	@Test
	public void createUserNegativeUsernameTooLong() throws SQLException {
		
		ERS_user newUser = new ERS_user(tooLongString, "password", "firstname", "lastname", "email@gmail.com", "employee");
		newUser.setUser_id(1);
		when(mockDao.checkUsernameTaken(eq(newUser))).thenReturn(false);
		when(mockDao.checkEmailTaken(eq(newUser))).thenReturn(false);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.createUser(newUser);
			
		});
		
		assertEquals("Cannot create user. Username exceeds acceptable number of characters[255]", e.getMessage());
		
		
	}
	
	@Test 
	public void createUserNegativePasswordTooLong() throws SQLException {
		
		ERS_user newUser = new ERS_user("username", tooLongString, "firstname", "lastname", "email@gmail.com", "employee");
		newUser.setUser_id(1);
		when(mockDao.checkUsernameTaken(eq(newUser))).thenReturn(false);
		when(mockDao.checkEmailTaken(eq(newUser))).thenReturn(false);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.createUser(newUser);
			
		});
		
		assertEquals("Cannot create user. Password exceeds acceptable number of characters[255]", e.getMessage());
	}
	
	@Test
	public void createUserNegativeFirstnameTooLong() throws SQLException {
		
		ERS_user newUser = new ERS_user("username", "password", tooLongString, "lastname", "email@gmail.com", "employee");
		newUser.setUser_id(1);
		when(mockDao.checkUsernameTaken(eq(newUser))).thenReturn(false);
		when(mockDao.checkEmailTaken(eq(newUser))).thenReturn(false);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.createUser(newUser);
			
		});
		
		assertEquals("Cannot create user. First name exceeds acceptable number of characters[255]", e.getMessage());
			
	}
	
	@Test
	public void createUserNegativeLastnameTooLong() throws SQLException {
		
		ERS_user newUser = new ERS_user("username", "password", "firstname", tooLongString, "email@gmail.com", "employee");
		newUser.setUser_id(1);
		when(mockDao.checkUsernameTaken(eq(newUser))).thenReturn(false);
		when(mockDao.checkEmailTaken(eq(newUser))).thenReturn(false);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.createUser(newUser);
			
		});
		
		assertEquals("Cannot create user. Last name exceeds acceptable number of characters[255]", e.getMessage());
		
	}
	
	@Test
	public void createUserNegativeEmailTooLong() throws SQLException {
		
		ERS_user newUser = new ERS_user("username", "password", "firstname", "lastname", tooLongString + "@gmail.com", "employee");
		newUser.setUser_id(1);
		when(mockDao.checkUsernameTaken(eq(newUser))).thenReturn(false);
		when(mockDao.checkEmailTaken(eq(newUser))).thenReturn(false);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.createUser(newUser);
			
		});
		
		assertEquals("Cannot create user. Email exceeds acceptable number of characters[255]", e.getMessage());
		
	}
	
	@Test
	public void createUserNegativeWhitespaceInUsername() throws SQLException {
		
		ERS_user newUser = new ERS_user("user name", "password", "firstname", "lastname", "email@gmail.com", "employee" );
		newUser.setUser_id(1);
		when(mockDao.checkUsernameTaken(eq(newUser))).thenReturn(false);
		when(mockDao.checkEmailTaken(eq(newUser))).thenReturn(false);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.createUser(newUser);
			
		});
		
		assertEquals("Cannot create user. Username cannot have whitespace between characters.", e.getMessage());
		
	}
	
	@Test
	public void createUserNegativeWhitespaceInEmail() throws SQLException {
		
		ERS_user newUser = new ERS_user("username", "password", "firstname", "lastname", "ema il@gmail.com", "employee" );
		newUser.setUser_id(1);
		when(mockDao.checkUsernameTaken(eq(newUser))).thenReturn(false);
		when(mockDao.checkEmailTaken(eq(newUser))).thenReturn(false);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.createUser(newUser);
			
		});
		
		assertEquals("Cannot create user. Email cannot have whitespace between characters.", e.getMessage());
		
	}
	
	
	
	/*********************************************END CREATE USER TESTS************************************/
	
	/*********************************************BEGIN DELETE USER TESTS
	 * @throws SQLException ***********************************/
	
	@Test
	public void deleteUserPositive() throws SQLException {	//only managers can delete users
		//We do all our authenticating on the controller layer so we should assume for this test that the user is already verified as a manager
		
		ERS_user userToDelete = new ERS_user();
		userToDelete.setUser_id(1);
		
		when(mockDao.getUser(eq(1))).thenReturn(userToDelete);
		when(mockDao.deleteAllUserRequests(eq(1))).thenReturn(true);
		when(mockDao.deleteUser(eq(1))).thenReturn(true);
		
		assertEquals(true, sut.deleteUser(1));
		
		
	}
	
	@Test
	public void deleteUserNegativeUserDoesntExist() throws SQLException {
		
		when(mockDao.deleteUser(eq(1))).thenReturn(false);
		
		assertEquals(false, sut.deleteUser(1));
		
	}
	
	
	/*********************************************END DELETE USER TESTS*****************************************/
	
	/*********************************************BEGIN LOGIN TESTS
	 * @throws SQLException 
	 * @throws NoSuchAlgorithmException *********************************************/


	
	
	  @Test public void loginPositive() throws SQLException, NoSuchAlgorithmException {
	  
		  ERS_user user = new ERS_user("username", "password", "firstname", "lastname", "email@gmail.com", "employee");
		  ERS_user expectedUser = new ERS_user("", "", "firstname", "lastname", "email@gmail.com", "employee");
		  
		  when(mockDao.getUserByUsername("16f78a7d6317f102bbd95fc9a4f3ff2e3249287690b8bdad6b7810f82b34ace3")).thenReturn(user);

	 
		  assertEquals(expectedUser, sut.getUserByUsernamePassword(user));
		  
	 }
	  
	  @Test public void loginNegativeUsernameDoesntMatch() throws SQLException { //test method stub }
		
		  ERS_user user = new ERS_user("username", "password", "firstname", "lastname", "email@gmail.com", "employee");
		  
		  when(mockDao.getUserByUsername("16f78a7d6317f102bbd95fc9a4f3ff2e3249287690b8bdad6b7810f82b34ace3")).thenThrow(new SQLException("Invalid login credentials")); 
		  
		  SQLException e = assertThrows(SQLException.class, () -> {
			  
			 sut.getUserByUsernamePassword(user); 
			  
		  });
		  
		  assertEquals("Invalid login credentials", e.getMessage());
		  
	  }
	  
	  @Test public void loginNegativePasswordDoesntMatch() throws SQLException { //test method stub }
		  
		  
		  ERS_user user = new ERS_user("username", "password", "firstname", "lastname", "email@gmail.com", "employee");
		  
		  when(mockDao.getUserByUsername("16f78a7d6317f102bbd95fc9a4f3ff2e3249287690b8bdad6b7810f82b34ace3")).thenThrow(new SQLException("Invalid login credentials")); 
		  
		  SQLException e = assertThrows(SQLException.class, () -> {
			  
			 sut.getUserByUsernamePassword(user); 
			  
		  });
		  
		  assertEquals("Invalid login credentials", e.getMessage());
		  
	  }
	 
	
	/*************************************************END LOGIN TESTS*********************************************/
	
	/**************************************************BEGIN UPDATE USER TESTS
	 * @throws SQLException *************************************/
	
	@Test
	public void updateUserPositiveUser() throws SQLException {	//User profile successfully updated by user
		ERS_user originalUser = new ERS_user("username", "password", "John", "Doe", "email@revature.net", "employee");
		ERS_user updatingUser = new ERS_user("username", "password2", "John2", "Doe2", "email@revature.net", "employee"); //Cant update username or email sorry
		ERS_user updatedUser = new ERS_user("1809aec60e165c214dd7b02d1f1b42ada7674d6a14ad70ba44ffd7b0641436bf", 
				"1cd8d3ee6bcaacbf5cf7d79064ccd460d5c12ae0080af140a6ee525f09cc2ab7", "john2", "Doe2", "email@revature.net", "employee");
		
		updatingUser.setUser_id(1);
		
		when(mockDao.updateUser(eq(updatingUser))).thenReturn(updatedUser);
		when(mockDao.getUser(eq(1))).thenReturn(originalUser);
		
		assertEquals(updatedUser, sut.updateUser(updatingUser));
		
	}
	
	@Test
	public void updateUserPositiveNullAndEmptyFieldsDontUpdate() throws SQLException {
		
		ERS_user originalUser = new ERS_user("username", "password", "John", "Doe", "email@revature.net", "employee");
		ERS_user updatingUser = new ERS_user("username", "password2", "", null, "email@revature.net", null); //Cant update username or email sorry
		ERS_user updatedUser = new ERS_user("1809aec60e165c214dd7b02d1f1b42ada7674d6a14ad70ba44ffd7b0641436bf", 
				"1cd8d3ee6bcaacbf5cf7d79064ccd460d5c12ae0080af140a6ee525f09cc2ab7", "john", "Doe", "email@revature.net", "employee");
		
		updatingUser.setUser_id(1);
		
		when(mockDao.updateUser(eq(updatingUser))).thenReturn(updatedUser);
		when(mockDao.getUser(eq(1))).thenReturn(originalUser);
		
		assertEquals(updatedUser, sut.updateUser(updatingUser));
		
	}
	

	@Test
	public void updateUserNegativeUserDoesntExist() throws SQLException {

		ERS_user updatingUser = new ERS_user("username", "password2", "John2", "Doe2", "email@revature.net", "employee");
		
		updatingUser.setUser_id(1);
		
		when(mockDao.getUser(eq(1))).thenThrow(new SQLException("Error, user does not exist"));
		
		SQLException e = assertThrows(SQLException.class, () -> {
			
			sut.updateUser(updatingUser);
			
		});
		
		assertEquals("Error, user does not exist", e.getMessage());
		
	}
	
	@Test 
	public void updateUserNegativeUpdatingUsername() throws SQLException {
		
		ERS_user originalUser = new ERS_user("username", "password", "John", "Doe", "email@revature.net", "employee");
		ERS_user updatingUser = new ERS_user("newUsername", "password", "John", "Doe", "email@revature.net", "employee");
		
		updatingUser.setUser_id(1);
		
		when(mockDao.getUser(eq(1))).thenReturn(originalUser);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.updateUser(updatingUser);
			
		});
		
		assertEquals("Unable to update user. Cannot update username", e.getMessage());
		
	}
	
	@Test
	public void updateUserNegativeUpdatingEmail() throws SQLException {
		
		ERS_user originalUser = new ERS_user("username", "password", "John", "Doe", "email@revature.net", "employee");
		ERS_user updatingUser = new ERS_user("username", "password", "John", "Doe", "newemail@revature.net", "employee");
		
		updatingUser.setUser_id(1);
		
		when(mockDao.getUser(eq(1))).thenReturn(originalUser);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.updateUser(updatingUser);
			
		});
		
		assertEquals("Unable to update user. Cannot update email", e.getMessage());
		
	}
		
	
	
	/******************************************************END UPDATE USER TESTS***********************************/
	
	/*****************************************************BEGIN GET ALL USERS TESTS
	 * @throws SQLException *********************************/
	
	@Test
	public void getAllUsersPositive() throws SQLException {
		
		ERS_user user1 = new ERS_user();
		ERS_user user2 = new ERS_user();
		
		user1.setUser_id(1);
		user2.setUser_id(2);
		
		ArrayList<ERS_user> expectedList = new ArrayList<>();
		expectedList.add(user2);
		expectedList.add(user1);
		
		
		when(mockDao.getAllUsers()).thenReturn(expectedList);
		
		assertEquals(expectedList, sut.getAllUsers());
		
	}
	

	
	/******************************************************END GET ALL USER TESTS************************************/
	
	/*****************************************************BEGIN GET USER TESTS
	 * @throws SQLException ****************************************/
	
	
	@Test
	public void getUserPositive() throws SQLException {
		
		ERS_user user = new ERS_user();
		
		user.setUser_id(1);
		
		when(mockDao.getUser(eq(1))).thenReturn(user);
		
		assertEquals(user, sut.getUser(1));
		
	}
	
	@Test
	public void getUserNegativeUserDoesntExist() throws SQLException {
		
		when(mockDao.getUser(eq(1))).thenThrow(new SQLException("User does not exist"));
		
		SQLException e = assertThrows(SQLException.class, () -> {
			
			sut.getUser(1);
			   
		});
		
		assertEquals("User does not exist", e.getMessage());
		
	}
	

	
	
	/*************************************************************END GET USER TESTS************************************/
	
	/***********************************************************BEGIN CREATE REQUEST TESTS
	 * @throws SQLException ******************************/
	
	@Test
	public void createRequestPositive() throws SQLException {
		
		ERS_reimbursement request = new ERS_reimbursement(50.2, "TRAVEL", "Some description", null, 1);
		ERS_user user = new ERS_user();
		user.setUser_id(1);
		
		when(mockDao.createRequest(eq(request))).thenReturn(request);
		when(mockDao.getUser(eq(1))).thenReturn(user);
		
		assertEquals(request, sut.createRequest(request));
		
		
	}
	
	@Test
	public void createRequestNegativeReimbursementAmountLessThanOrEqualToZero() throws SQLException {	//why would you create a reimbursement request for negative dollars?!?! Baka
		
		ERS_reimbursement request = new ERS_reimbursement(-20.4, "travel", "Some description", null, 1);
		ERS_user user = new ERS_user(); 
		user.setUser_id(1);
		
		when(mockDao.getUser(eq(1))).thenReturn(user);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.createRequest(request);
			
		});
		
		assertEquals("Unable to create request. Reimbursement amount cannot equal to or less than 0. Please enter a valid reimbursement amount", e.getMessage());
		
		
	}
	
	@Test
	public void createRequestNegativeUserDoesntExist() throws SQLException {	//Trying to create a request for a user that doesn't exist
		
		ERS_reimbursement request = new ERS_reimbursement(30.3, "travel", "Some description", null, 1);
		when(mockDao.getUser(eq(1))).thenThrow(new SQLException("Unable to find user"));
		
		SQLException e = assertThrows(SQLException.class, () -> {
			
			sut.createRequest(request);
			
		});
		
		assertEquals("Unable to find user", e.getMessage());
		
	}
	
	@Test
	public void createRequestNegativeTypeNotValid() throws SQLException {	//When creating tests, will probably just assign as pending in service layer
		
		ERS_reimbursement request = new ERS_reimbursement(30.3, "invalid status", "Some description", null, 1);
		ERS_user user = new ERS_user(); 
		user.setUser_id(1);
		
		when(mockDao.getUser(eq(1))).thenReturn(user);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.createRequest(request);
			
		});
		
		assertEquals("Unable to create request. Reimbursement type is not valid. Must be 'LODGING', 'TRAVEL', 'FOOD' or 'OTHER'", e.getMessage());
		
	}
	
	@Test public void createRequestNegativeDescriptionLongerThan255Chars() throws SQLException {
		
		ERS_reimbursement request = new ERS_reimbursement(30.3, "FOOD", tooLongString, null, 1);
		ERS_user user = new ERS_user();
		user.setUser_id(1);
		
		when(mockDao.getUser(eq(1))).thenReturn(user);
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.createRequest(request);
			
		});
		
		assertEquals("Unable to create request. Reimbursement description is too long[255]", e.getMessage());
		
	}
	
	
	/**********************************************************END CREATE REQUEST TESTS**********************************/
	
	/**********************************************************BEGIN DELETE REQUEST TESTS*********************************/
	

	@Test
	public void deleteRequestPositive() {
		
		ERS_reimbursement request = new ERS_reimbursement(); 
		
		when(mockDao.getRequest(eq(1))).thenReturn(request);
		when(mockDao.deleteRequest(eq(1))).thenReturn(true);
		
		assertEquals(true, sut.deleteRequest(1));
		
	}
	
	@Test
	public void deleteRequestNegativeRequestDoesntExist() {
		
		when(mockDao.getRequest(eq(1))).thenThrow(new SQLException("Request not found"));
		
		SQLException e = assertThrows(SQLException.class, () -> {
			
			sut.deleteRequest(1);
			
		});
		
		assertEquals("Cannot delete request. Request not found", e.getMessage());
		
	}
	
	/*********************************************************END DELETE REQUEST TESTS****************************************/
	
	/*********************************************************BEGIN UPDATE REQUEST TESTS**************************************/
	
	@Test
	public void updateRequestPositive() {}
	
	@Test
	public void updateRequestPositiveEmptyAndNullDontChange() {}
	
	@Test
	public void updateRequestNegativeStatusIsNotValid() {}
	
	@Test
	public void updateRequestNegativeTypeIsNotValid() {}
	
	@Test
	public void updateRequestNegativeAmountIsNotValid() {}
	
	/**************************************************************END UPDATE REQUEST TESTS******************************************/
	
	/**************************************************************BEGIN GET ALL REQUESTS TESTS**************************************/
	

	@Test
	public void getAllRequestsPositive() {
		
		ArrayList<ERS_reimbursement> expectedList = new ArrayList<>();
		
		ERS_reimbursement request1 = new ERS_reimbursement();
		ERS_reimbursement request2 = new ERS_reimbursement();
		
		request1.setReimb_id(1);
		request2.setReimb_id(2);
		
		expectedList.add(request2);
		expectedList.add(request1);
		when(mockDao.getAllRequests()).thenReturn(expectedList);
		
		assertEquals(expectedList, sut.getAllRequests());
		
	}
	
	@Test
	public void getAllRequestsByUserIdPositive() throws SQLException {
		
		ArrayList<ERS_reimbursement> expectedList = new ArrayList<>();
		ERS_user user = new ERS_user();
		user.setUser_id(1);
		
		ERS_reimbursement request1 = new ERS_reimbursement();
		ERS_reimbursement request2 = new ERS_reimbursement();
		
		request1.setReimb_id(1);
		request2.setReimb_id(2);
		
		expectedList.add(request2);
		expectedList.add(request1);
		
		when(mockDao.getAllRequests(eq(1))).thenReturn(expectedList);
		when(mockDao.getUser(eq(1))).thenReturn(user);
		
		assertEquals(expectedList, sut.getAllRequests(1));
		
	}
	
	@Test
	public void getAllRequestsByUserIdNegativeUserDoesntExist() throws SQLException {
		
		when(mockDao.getUser(eq(1))).thenThrow(new SQLException("Unable to find user"));
		
		SQLException e = assertThrows(SQLException.class, () -> {
			
			sut.getAllRequests(1);
			
		});
		
		assertEquals("Unable to get requests. User does not exist", e.getMessage());
		
	}
	
	@Test
	public void getAllRequestsByStatusPositive() {
		
		ArrayList<ERS_reimbursement> expectedList = new ArrayList<>();
		ERS_reimbursement request1 = new ERS_reimbursement();
		ERS_reimbursement request2 = new ERS_reimbursement();
		request1.setReimb_id(1);
		request2.setReimb_id(2);
		expectedList.add(request2);
		expectedList.add(request1);
		
		when(mockDao.getAllRequests(eq("pending"))).thenReturn(expectedList);
		
		assertEquals(expectedList, sut.getAllRequests("pending"));
		
	}
	
	@Test
	public void getAllRequestsByStatusNegativeInvalidStatus() {
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.getAllRequests("invalid status");
			
		});
		
		assertEquals("Unable to get requests. Invalid status. Please search by a valid status", e.getMessage());
		
	}
	
	@Test
	public void getAllRequestsByStatusAndUserIdPositive() throws SQLException {
		
		ERS_user user = new ERS_user();
		ERS_reimbursement request1 = new ERS_reimbursement();
		ERS_reimbursement request2 = new ERS_reimbursement();
		ArrayList<ERS_reimbursement> expectedList = new ArrayList<>();
		request1.setReimb_id(1);
		request2.setReimb_id(2);
		user.setUser_id(1);
		expectedList.add(request2);
		expectedList.add(request1);
		
		when(mockDao.getUser(eq(1))).thenReturn(user);
		when(mockDao.getAllRequests(eq("pending"), eq(1))).thenReturn(expectedList);
		
		assertEquals(expectedList, sut.getAllRequests("pending", 1));
		
		
	}
	
	@Test
	public void getAllRequestsByStatusAndUserIdNegativeUserDoesntExist() throws SQLException {
		
		when(mockDao.getUser(eq(1))).thenThrow(new SQLException("Unable to find user"));
		
		SQLException e = assertThrows(SQLException.class, () -> {
			
			sut.getAllRequests("pending", 1);
			
		});
		
		assertEquals("Unable to get requests. User does not exist", e.getMessage());
	}
	
	@Test
	public void getAllRequestsByStatusAndUserIdNegativeInvalidStatus() {
		
		IllegalArgumentException e = assertThrows(IllegalArgumentException.class, () -> {
			
			sut.getAllRequests("invalid status", 1);
			
		});
		
		assertEquals("Unable to get requests. Invald status. Pkease search by a valid status", e.getMessage());
		
	}
	
	
	
	/**************************************************************END GET ALL REQUESTS TESTS******************************************/
	
	/**************************************************************BEGIN EMPLOYEE VALIDATION TESTS**************************************/
	/*
	 * @Test public void validateEmployeeTestPositive() { //So this really doesn't
	 * return anything. Its working if it DOESNT throw an exception //Im sure there
	 * a way to assert this...
	 * 
	 * 
	 * 
	 * }
	 */
	
	/*
	 * @Test public void validateEmployeeTestNegative() { //Maybe a cookie expires
	 * or something idk
	 * 
	 * 
	 * 
	 * }
	 */
	
	
	
	/**************************************************************END EMPLOYEE VALIDATION TESTS******************************************/
	
	/**************************************************************BEGIN MANAGER VALIDATION TESTS**************************************/
	
	/*
	 * @Test public void validateManagerTestPositive() { //Same story here
	 * 
	 * 
	 * }
	 * 
	 * @Test public void validateManagerNegativeCookieExpired() {
	 * 
	 * 
	 * 
	 * }
	 * 
	 * @Test public void validateManagerNegativeUserIsEmployee() {
	 * 
	 * 
	 * 
	 * }
	 */
	
	
	//********************NEED TO ADD TESTS FOR ELEVATING USER TO MANAGER ROLE**********************/
	
	
	@Test
	public void getRequestByIdPositive() {
		
		ERS_reimbursement request = new ERS_reimbursement(); 
		when(mockDao.getRequest(eq(1))).thenReturn(request);
		
		assertEquals(request, sut.getRequest(1));
		
	}
	
	@Test
	public void getRequestByIdNegativeRequestDoesntExist() {
		
		when(mockDao.getRequest(eq(1))).thenThrow(new SQLException("Unable to find request"));
		
		SQLException e = assertThrows(SQLException.class, () -> {
			
			sut.getRequest(1);
			
		});
		
		assertEquals("Unable to find request", e.getMessage());
		
	}
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
