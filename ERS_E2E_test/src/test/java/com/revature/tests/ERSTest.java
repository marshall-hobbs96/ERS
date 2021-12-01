package com.revature.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.Random;

import org.junit.Before;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import com.revature.model.CreateUserPage;
import com.revature.model.LoginPage;
import com.revature.model.WelcomePage;

import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class ERSTest {
	
	private WebDriver driver;

	@BeforeEach
	public void setup() {
		
		System.setProperty("webdriver.chrome.driver", "C:/WebDrivers/chromedriver.exe");
		driver = new ChromeDriver();
		
	}
	
	@AfterEach
	public void quit() {
		
		driver.quit();
		
	}
	
	@Given("I am at the login page")
	public void i_am_at_the_login_page() {
		
		System.setProperty("webdriver.chrome.driver", "C:/WebDrivers/chromedriver.exe");
		driver = new ChromeDriver();
		
	    driver.get(LoginPage.URL);
	    
	}

	@When("I select the create new account button")
	public void i_select_the_create_new_account_button() {

		WebElement signupButton = driver.findElement(By.xpath(LoginPage.newUserButton));
		signupButton.click();
		
	}

	@Then("I should be taken to the create new account page")
	public void i_should_be_taken_to_the_create_new_account_page() {

	    String currentURL = driver.getCurrentUrl();
	    assertEquals(CreateUserPage.URL, currentURL);
	    driver.close();
	    driver.quit();
	}

	@Given("I am at the create account page")
	public void i_am_at_the_create_account_page() {

		System.setProperty("webdriver.chrome.driver", "C:/WebDrivers/chromedriver.exe");
		driver = new ChromeDriver();
		
		driver.get(CreateUserPage.URL);
	}

	@When("I type in a new unique username into the username field")
	public void i_type_in_a_new_unique_username_into_the_username_field() {
	
		Random newRandGen = new Random();
		String newUsername = ""; 
		
		for(int i = 0; i < 20; i++) {
			
			newUsername += (char)newRandGen.nextInt();
			
		}
		
	    WebElement usernameField = driver.findElement(By.xpath(CreateUserPage.usernameField));
	    usernameField.sendKeys(newUsername);
	}

	@When("I type in a new unique password into the password field")
	public void i_type_in_a_new_unique_password_into_the_password_field() {
		
		String newPassword = "password"; //Luckily password doesnt need to be unique, unlike username
		
		WebElement passwordField = driver.findElement(By.xpath(CreateUserPage.passwordField));
		WebElement retypePasswordField = driver.findElement(By.xpath(CreateUserPage.retypePasswordField));
		retypePasswordField.sendKeys(newPassword);
		passwordField.sendKeys(newPassword);
		
	}

	@When("I type in a first name into the first name field")
	public void i_type_in_a_first_name_into_the_first_name_field() {
	    
		String firstname = "firstname";
		
		WebElement firstnameField = driver.findElement(By.xpath(CreateUserPage.firstNameField));
		firstnameField.sendKeys(firstname);
		
	}

	@When("I type in a last name into the last name field")
	public void i_type_in_a_last_name_into_the_last_name_field() {
	    
		String newLastname = "lastname";
		
		WebElement lastnameField = driver.findElement(By.xpath(CreateUserPage.lastNameField));
		lastnameField.sendKeys(newLastname);
		
	}

	@When("I type in a new unique email into the email field")
	public void i_type_in_a_new_unique_email_into_the_email_field() {

		Random newRandGen = new Random();
		String newEmail = ""; 
		
		for(int i = 0; i < 20; i++) {
			
			newEmail += (char)newRandGen.nextInt();
			
		}
		
		newEmail += "@gmail.com";
		
		WebElement emailField = driver.findElement(By.xpath(CreateUserPage.emailField));
		emailField.sendKeys(newEmail);
		  
	}

	@Then("I should see a message telling me a new user has been created")
	public void i_should_see_a_message_telling_me_a_new_user_has_been_created() {
		//Message that should display in submitButton helper: User successfully created
		
		WebElement submitHelper = driver.findElement(By.xpath(CreateUserPage.submitHelper));
		assertEquals("User successfully created", submitHelper.getText());
		driver.close();
		driver.quit();
		
	}


	@Then("I should see a message telling me I am missing a username")
	public void i_should_see_a_message_telling_me_i_am_missing_a_username() {
	    WebElement usernameHelper = driver.findElement(By.xpath(CreateUserPage.usernameHelper));
	    assertEquals("Please enter a valid username", usernameHelper.getText());
	    driver.close();
	    driver.quit();
	}

	@Then("I should see a message telling me I am missing a password")
	public void i_should_see_a_message_telling_me_i_am_missing_a_password() {
	    WebElement passwordHelper = driver.findElement(By.xpath(CreateUserPage.passwordHelper));
	    assertEquals("Please enter a valid password", passwordHelper.getText());
	    driver.close();
	    driver.quit();
	}

	@Then("I should see a message telling me I am missing a first name")
	public void i_should_see_a_message_telling_me_i_am_missing_a_first_name() {
	    WebElement firstnameHelper = driver.findElement(By.xpath(CreateUserPage.firstnameHelper));
	    assertEquals("Please enter a valid firstname", firstnameHelper.getText());
	    driver.close();
	    driver.quit();
	}

	@Then("I should see a message telling me I am missing a last name")
	public void i_should_see_a_message_telling_me_i_am_missing_a_last_name() {
	    WebElement lastnameHelper = driver.findElement(By.xpath(CreateUserPage.lastnameHelper));
	    assertEquals("Please enter a valid lastname", lastnameHelper.getText());
	    driver.close();
	    driver.quit();
	}

	@Then("I should see a message telling me I am missing an email")
	public void i_should_see_a_message_telling_me_i_am_missing_an_email() {
	    WebElement emailHelper = driver.findElement(By.xpath(CreateUserPage.emailHelper));
	    assertEquals("Please enter a valid email", emailHelper.getText());
	    driver.close();
	    driver.quit();
	}

	@When("I type in an existing username into the username field")
	public void i_type_in_an_existing_username_into_the_username_field() {
	    String existingUsername = "username";
	    WebElement usernameField = driver.findElement(By.xpath(CreateUserPage.usernameField));
	    usernameField.sendKeys(existingUsername);
	    
	}

	@Then("I should see a message telling me username is already taken")
	public void i_should_see_a_message_telling_me_username_is_already_taken() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I type in an existing email into the email field")
	public void i_type_in_an_existing_email_into_the_email_field() {
	    
		String existingEmail = "email@gmail.com";
		WebElement emailField = driver.findElement(By.xpath(CreateUserPage.emailField));
		emailField.sendKeys(existingEmail);
		
	}
	
	@When("I click the create user submit button")
	public void i_click_the_create_user_submit_button() {
	    WebElement userSubmit = driver.findElement(By.xpath(CreateUserPage.submitButton));
	    userSubmit.click();
	}

	@Then("I should see a message telling me email is already taken")
	public void i_should_see_a_message_telling_me_email_is_already_taken() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I type in an invalid \\(no domain name) email into the email field")
	public void i_type_in_an_invalid_no_domain_name_email_into_the_email_field() {
		String invalidEmail = "email";
		WebElement emailField = driver.findElement(By.xpath(CreateUserPage.emailField));
		emailField.sendKeys(invalidEmail);
	}

	@Then("I should see a message telling me I have an invalid email")
	public void i_should_see_a_message_telling_me_i_have_an_invalid_email() {
	    WebElement emailHelper = driver.findElement(By.xpath(CreateUserPage.emailHelper));
	    assertEquals("Please enter a valid email", emailHelper.getText());
	    driver.close();
	    driver.quit();
	}
	
	@When("I type in a new unique password into the password field but forget to retype password")
	public void i_type_in_a_new_unique_password_into_the_password_field_but_forget_to_retype_password() {
	    WebElement passwordField = driver.findElement(By.xpath(CreateUserPage.passwordField));
	    
	    passwordField.sendKeys("password");
	}

	@Then("I should see a message telling me the retyped password doesnt match")
	public void i_should_see_a_message_telling_me_the_retyped_password_doesnt_match() {
	    WebElement retypedPasswordHelper = driver.findElement(By.xpath(CreateUserPage.retypePasswordHelper));
	    assertEquals("Passwords don't match!", retypedPasswordHelper.getText());
	    driver.close();
	    driver.quit();
	}

	@When("I type in a new unique password into the password field but retyped password doesnt match")
	public void i_type_in_a_new_unique_password_into_the_password_field_but_retyped_password_doesnt_match() {
	    WebElement passwordField = driver.findElement(By.xpath(CreateUserPage.passwordField));
	    WebElement retypedPasswordField = driver.findElement(By.xpath(CreateUserPage.retypePasswordField));
	    
	    passwordField.sendKeys("password");
	    retypedPasswordField.sendKeys("passwoooord");
	    
	}
	
	
	
	
	
	
	
	
	
	
	
	@Given("I am at the welcome page")
	public void i_am_at_the_welcome_page() {
		System.setProperty("webdriver.chrome.driver", "C:/WebDrivers/chromedriver.exe");
		driver = new ChromeDriver();
		
	    driver.get(WelcomePage.URL);
	}

	@When("I I click the create new request button")
	public void i_i_click_the_create_new_request_button() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I enter in a valid amount in the amount field")
	public void i_enter_in_a_valid_amount_in_the_amount_field() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I enter in a valid description in the description field")
	public void i_enter_in_a_valid_description_in_the_description_field() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I select a valid type")
	public void i_select_a_valid_type() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I attach a valid receipt")
	public void i_attach_a_valid_receipt() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I click the submit button")
	public void i_click_the_submit_button() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("I should see a message saying request successfully submitted and the new request displayed in the requests tab")
	public void i_should_see_a_message_saying_request_successfully_submitted_and_the_new_request_displayed_in_the_requests_tab() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I enter in an amount equal to or less than zero")
	public void i_enter_in_an_amount_equal_to_or_less_than_zero() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("I should see a message saying request amount can not be equal to or less than zero")
	public void i_should_see_a_message_saying_request_amount_can_not_be_equal_to_or_less_than_zero() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("I should see a message saying please select a valid type for request")
	public void i_should_see_a_message_saying_please_select_a_valid_type_for_request() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("I should see a message saying please attach a valid receipt")
	public void i_should_see_a_message_saying_please_attach_a_valid_receipt() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("I should see a message saying please provide a description for request")
	public void i_should_see_a_message_saying_please_provide_a_description_for_request() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Given("I am at the welcome page while logged in as an employee")
	public void i_am_at_the_welcome_page_while_logged_in_as_an_employee() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I click the delete request button next to a pending request")
	public void i_click_the_delete_request_button_next_to_a_pending_request() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("I should see a message saying the request has been deleted and see the request removed from the requests tab")
	public void i_should_see_a_message_saying_the_request_has_been_deleted_and_see_the_request_removed_from_the_requests_tab() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	@When("I select pending requests from the filter menu")
	public void i_select_pending_requests_from_the_filter_menu() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("I should see only my own pending requests")
	public void i_should_see_only_my_own_pending_requests() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I select approved requests from the filter menu")
	public void i_select_approved_requests_from_the_filter_menu() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("I should see only my own approved requests")
	public void i_should_see_only_my_own_approved_requests() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I select denied requests from the filter menu")
	public void i_select_denied_requests_from_the_filter_menu() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("I should see only my own denied requests")
	public void i_should_see_only_my_own_denied_requests() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	@When("I click on the update account button")
	public void i_click_on_the_update_account_button() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I successfully verify my current password")
	public void i_successfully_verify_my_current_password() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("I should be taken to the update account page")
	public void i_should_be_taken_to_the_update_account_page() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I give the wrong current password")
	public void i_give_the_wrong_current_password() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("I should see a message saying current password is incorrect")
	public void i_should_see_a_message_saying_current_password_is_incorrect() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Given("I am at the employee update account page")
	public void i_am_at_the_employee_update_account_page() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I enter in a new first name")
	public void i_enter_in_a_new_first_name() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I enter in a new last name")
	public void i_enter_in_a_new_last_name() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I enter in a new valid email")
	public void i_enter_in_a_new_valid_email() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I enter in a new password")
	public void i_enter_in_a_new_password() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("I should see a message saying account has been successfully updated and I see should my account information has changed")
	public void i_should_see_a_message_saying_account_has_been_successfully_updated_and_i_see_should_my_account_information_has_changed() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I enter in a new invalid email")
	public void i_enter_in_a_new_invalid_email() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("I should see a message saying cannot update account, new email is invalid")
	public void i_should_see_a_message_saying_cannot_update_account_new_email_is_invalid() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	@Given("I am at the update request page")
	public void i_am_at_the_update_request_page() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("I should see a message informing me the request has been updated")
	public void i_should_see_a_message_informing_me_the_request_has_been_updated() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I select an invalid type")
	public void i_select_an_invalid_type() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("I should see a message informing me I must select a valid type")
	public void i_should_see_a_message_informing_me_i_must_select_a_valid_type() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I enter in nothing for the description")
	public void i_enter_in_nothing_for_the_description() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	
	//********************************Employee login steps
	
	@When("I enter in a valid username")
	public void i_enter_in_a_valid_username() {
	    
		WebElement usernameField = driver.findElement(By.xpath(LoginPage.usernameField));
		usernameField.sendKeys("username");
		
	}
	
	@When("I enter in a valid password")
	public void i_enter_in_a_valid_password() {
	    
		WebElement passwordField = driver.findElement(By.xpath(LoginPage.passwordField));
		passwordField.sendKeys("password");
		
	}
	
	@When("I click the login button")
	public void i_click_the_login_button() {
	    WebElement loginButton = driver.findElement(By.xpath(LoginPage.loginButton));
	    loginButton.click();
	}

	@Then("I should be taken to the employee welcome page and be able to see my first and last name displayed in the webpage")
	public void i_should_be_taken_to_the_employee_welcome_page_and_be_able_to_see_my_first_and_last_name_displayed_in_the_webpage() {
	    
		assertEquals(WelcomePage.URL, driver.getCurrentUrl());
		
	}

	@Then("I should see a message telling me to please enter in a username")
	public void i_should_see_a_message_telling_me_to_please_enter_in_a_username() {
	    WebElement usernameHelper = driver.findElement(By.xpath(LoginPage.usernameHelper));
	    assertEquals("Please enter a username", usernameHelper.getText());
	}


	@Then("I should see a message telling me to please enter in a password")
	public void i_should_see_a_message_telling_me_to_please_enter_in_a_password() {
	    WebElement passwordHelper = driver.findElement(By.xpath(LoginPage.passwordHelper));
	    assertEquals("Please enter a password", passwordHelper.getText());
	}

	@When("I enter in an invalid username")
	public void i_enter_in_an_invalid_username() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I enter in an invalid password")
	public void i_enter_in_an_invalid_password() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("I should see a message telling me I have an invalid username\\/password")
	public void i_should_see_a_message_telling_me_i_have_an_invalid_username_password() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	
	//*************************************Employee login steps
	
	
	@Then("I should be taken to the manager welcome page and be able to see my first and last name displayed in the webpage")
	public void i_should_be_taken_to_the_manager_welcome_page_and_be_able_to_see_my_first_and_last_name_displayed_in_the_webpage() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	
	
	//************************************************Manager login step
	// **********************************************Manager requests
	
	@Given("I am at the welcome page while logged in as a manager")
	public void i_am_at_the_welcome_page_while_logged_in_as_a_manager() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I click the delete request button next to a request")
	public void i_click_the_delete_request_button_next_to_a_request() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	@Then("I should see all pending requests for all users")
	public void i_should_see_all_pending_requests_for_all_users() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("I should see all approved requests for all users")
	public void i_should_see_all_approved_requests_for_all_users() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("I should see all denied requests for all users")
	public void i_should_see_all_denied_requests_for_all_users() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I enter in a valid employee ID in the employee search bar")
	public void i_enter_in_a_valid_employee_id_in_the_employee_search_bar() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("I should see all pending requests for that user")
	public void i_should_see_all_pending_requests_for_that_user() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("I should see all approved requests for that user")
	public void i_should_see_all_approved_requests_for_that_user() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("I should see all denied requests for that user")
	public void i_should_see_all_denied_requests_for_that_user() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	@Then("I should see all requests related to that employee")
	public void i_should_see_all_requests_related_to_that_employee() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I enter in an invalid employee ID in the employee search bar")
	public void i_enter_in_an_invalid_employee_id_in_the_employee_search_bar() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("I should see a message stating that that employee does not exist")
	public void i_should_see_a_message_stating_that_that_employee_does_not_exist() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	@When("I select approve request from one of the request drop down menues")
	public void i_select_approve_request_from_one_of_the_request_drop_down_menues() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I select submit")
	public void i_select_submit() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("I should see a message saying the request has been approved")
	public void i_should_see_a_message_saying_the_request_has_been_approved() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I select deny request from one of the request drop down menues")
	public void i_select_deny_request_from_one_of_the_request_drop_down_menues() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("I should see a message saying the request has been denied")
	public void i_should_see_a_message_saying_the_request_has_been_denied() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	@Given("I am at the manager update account page")
	public void i_am_at_the_manager_update_account_page() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I select manager role from the selection menu")
	public void i_select_manager_role_from_the_selection_menu() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("I Should see a message saying employee account has been successfully updated")
	public void i_should_see_a_message_saying_employee_account_has_been_successfully_updated() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I select employee role from the selection menu")
	public void i_select_employee_role_from_the_selection_menu() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@When("I select the employee tab")
	public void i_select_the_employee_tab() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}

	@Then("I should be taken to the employees list page")
	public void i_should_be_taken_to_the_employees_list_page() {
	    // Write code here that turns the phrase above into concrete actions
	    throw new io.cucumber.java.PendingException();
	}
	
	
	
	
	
	
	
	
		
}
