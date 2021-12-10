package com.revature.tests;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.time.Duration;
import java.util.ArrayList;
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
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.revature.model.CreateRequestPage;
import com.revature.model.CreateUserPage;
import com.revature.model.LoginPage;
import com.revature.model.WelcomeManagerPage;
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

		WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(3));
		wdw.until(ExpectedConditions.urlMatches(CreateUserPage.URL));
		
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
			
			newUsername += (char)((newRandGen.nextInt() % 25) + 65);
			
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
			
			newEmail += (char)((newRandGen.nextInt() % 25) + 65);
			
		}
		
		newEmail += "@gmail.com";
		
		WebElement emailField = driver.findElement(By.xpath(CreateUserPage.emailField));
		emailField.sendKeys(newEmail);
		  
	}

	@Then("I should see a message telling me a new user has been created")
	public void i_should_see_a_message_telling_me_a_new_user_has_been_created() {
		//Message that should display in submitButton helper: User successfully created
		WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(5));
		WebElement submitHelper = driver.findElement(By.xpath(CreateUserPage.submitHelper));
		wdw.until(ExpectedConditions.textToBePresentInElement(submitHelper, "User successfully created"));
		
		assertEquals("User successfully created", submitHelper.getText());
		driver.close();
		driver.quit();
		
	}


	@Then("I should see a message telling me I am missing a username")
	public void i_should_see_a_message_telling_me_i_am_missing_a_username() {
	    WebElement usernameHelper = driver.findElement(By.xpath(CreateUserPage.usernameHelper));
	    
	    WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(2));
	    wdw.until(ExpectedConditions.textToBePresentInElement(usernameHelper, "Please enter a valid username"));
	    
	    assertEquals("Please enter a valid username", usernameHelper.getText());
	    driver.close();
	    driver.quit();
	}

	@Then("I should see a message telling me I am missing a password")
	public void i_should_see_a_message_telling_me_i_am_missing_a_password() {
	    WebElement passwordHelper = driver.findElement(By.xpath(CreateUserPage.passwordHelper));
	    
	    WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(2));
	    wdw.until(ExpectedConditions.textToBePresentInElement(passwordHelper, "Please enter a valid password"));
	    
	    assertEquals("Please enter a valid password", passwordHelper.getText());
	    driver.close();
	    driver.quit();
	}

	@Then("I should see a message telling me I am missing a first name")
	public void i_should_see_a_message_telling_me_i_am_missing_a_first_name() {
	    WebElement firstnameHelper = driver.findElement(By.xpath(CreateUserPage.firstnameHelper));
	    
	    WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(2));
	    wdw.until(ExpectedConditions.textToBePresentInElement(firstnameHelper, "Please enter a valid firstname"));
	    
	    assertEquals("Please enter a valid firstname", firstnameHelper.getText());
	    driver.close();
	    driver.quit();
	}

	@Then("I should see a message telling me I am missing a last name")
	public void i_should_see_a_message_telling_me_i_am_missing_a_last_name() {
	    WebElement lastnameHelper = driver.findElement(By.xpath(CreateUserPage.lastnameHelper));
	    
	    WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(2));
	    wdw.until(ExpectedConditions.textToBePresentInElement(lastnameHelper, "Please enter a valid lastname"));
	    
	    assertEquals("Please enter a valid lastname", lastnameHelper.getText());
	    driver.close();
	    driver.quit();
	}

	@Then("I should see a message telling me I am missing an email")
	public void i_should_see_a_message_telling_me_i_am_missing_an_email() {
	    WebElement emailHelper = driver.findElement(By.xpath(CreateUserPage.emailHelper));
	    
	    WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(2));
	    wdw.until(ExpectedConditions.textToBePresentInElement(emailHelper, "Please enter a valid email"));
	    
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
		
		driver.close();
		driver.quit();
		
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
		driver.close();
		driver.quit();
		
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
	    
	    WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(2));
	    wdw.until(ExpectedConditions.textToBePresentInElement(emailHelper, "Please enter a valid email"));
	    
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
	    
	    WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(2));
	    wdw.until(ExpectedConditions.textToBePresentInElement(retypedPasswordHelper, "Passwords don't match!"));
	    
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

	

	
	//********************************Employee login steps
	
	@When("I enter in a valid username")
	public void i_enter_in_a_valid_username() {
	    
		WebElement usernameField = driver.findElement(By.xpath(LoginPage.usernameField));
		usernameField.sendKeys("employeetest");
		
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
	    
		WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(2));
		wdw.until(ExpectedConditions.urlMatches(WelcomePage.URL));
		
		assertEquals(WelcomePage.URL, driver.getCurrentUrl());
		
		driver.close();
		driver.quit();
		
	}

	@Then("I should see a message telling me to please enter in a username")
	public void i_should_see_a_message_telling_me_to_please_enter_in_a_username() {
	    WebElement usernameHelper = driver.findElement(By.xpath(LoginPage.usernameHelper));
	    
	    WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(2));
	    wdw.until(ExpectedConditions.textToBePresentInElement(usernameHelper, "Please enter a username"));
	    
	    assertEquals("Please enter a username", usernameHelper.getText());
	    
	    driver.close();
	    driver.quit();
	    
	}


	@Then("I should see a message telling me to please enter in a password")
	public void i_should_see_a_message_telling_me_to_please_enter_in_a_password() {
	    WebElement passwordHelper = driver.findElement(By.xpath(LoginPage.passwordHelper));
	    
	    WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(2));
	    wdw.until(ExpectedConditions.textToBePresentInElement(passwordHelper, "Please enter a password"));
	    
	    assertEquals("Please enter a password", passwordHelper.getText());
	    
	    driver.close();
	    driver.quit();
	    
	    
	}

	@When("I enter in an invalid username")
	public void i_enter_in_an_invalid_username() {
	    WebElement usernameField = driver.findElement(By.xpath(LoginPage.usernameField));
	    usernameField.sendKeys(" asdf  asdf hg aergads fgadfg fc"); //yea not valid
	}

	@When("I enter in an invalid password")
	public void i_enter_in_an_invalid_password() {
	    
		WebElement passwordField = driver.findElement(By.xpath(LoginPage.passwordField));
		passwordField.sendKeys("I mean, hopefully this is an invalid password."); //Really passwords can be anything, but hopefully noone would use this
		
	}


	
	@Given("I am at the create request page")
	public void i_am_at_the_create_request_page() {
		System.setProperty("webdriver.chrome.driver", "C:/WebDrivers/chromedriver.exe");
		driver = new ChromeDriver(); 
		
	    driver.get(LoginPage.URL);
	    
	    WebElement usernameField = driver.findElement(By.xpath(LoginPage.usernameField));
	    WebElement passwordField = driver.findElement(By.xpath(LoginPage.passwordField));
	    WebElement loginButton = driver.findElement(By.xpath(LoginPage.loginButton));
	    
	    usernameField.sendKeys("employeetest");
	    passwordField.sendKeys("password");
	    loginButton.click();
	    
	    WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(2));
	    wdw.until(ExpectedConditions.urlMatches(WelcomePage.URL));
	    
	    driver.get(CreateRequestPage.URL);
	    
	    wdw.until(ExpectedConditions.urlMatches(CreateRequestPage.URL));
	    
	}
	
	@Then("I should see a message saying request successfully submitted")
	public void i_should_see_a_message_saying_request_successfully_submitted() {
	    WebElement submitHelper = driver.findElement(By.xpath(CreateRequestPage.submitHelper)); 
	    
	    WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(2));
	    wdw.until(ExpectedConditions.textToBePresentInElement(submitHelper, "Request successfully created"));
	    
	    assertEquals("Request successfully created", submitHelper.getText());
	    
	    driver.close();
	    driver.quit();
	    
	    
	}

	@When("I enter in an amount equal to or less than zero")
	public void i_enter_in_an_amount_equal_to_or_less_than_zero() {
	    
		WebElement amountField = driver.findElement(By.xpath(CreateRequestPage.amountInput));
		amountField.sendKeys("-100");
	}

	@When("I enter in a valid description in the description field")
	public void i_enter_in_a_valid_description_in_the_description_field() {
	    WebElement descriptionField = driver.findElement(By.xpath(CreateRequestPage.descriptionInput));
	    descriptionField.sendKeys("A totally valid description");
	}

	@When("I attach a valid receipt")
	public void i_attach_a_valid_receipt() {
	    
		WebElement receiptField = driver.findElement(By.xpath(CreateRequestPage.receiptInput));
		receiptField.sendKeys("C:\\Users\\apers\\OneDrive\\Desktop\\170px-ReceiptSwiss.jpg");
		
	}

	@When("I click the submit button")
	public void i_click_the_submit_button() {
	    WebElement submitButton = driver.findElement(By.xpath(CreateRequestPage.submitButton));
	    submitButton.click();
	}

	@Then("I should see a message saying request amount can not be equal to or less than zero")
	public void i_should_see_a_message_saying_request_amount_can_not_be_equal_to_or_less_than_zero() {
	    
		WebElement submitHelper = driver.findElement(By.xpath(CreateRequestPage.submitHelper));
		
	    WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(2));
	    wdw.until(ExpectedConditions.textToBePresentInElement(submitHelper, "Unable to create request. Amount is invalid. Must be greater than 0"));
		
		assertEquals("Unable to create request. Amount is invalid. Must be greater than 0", submitHelper.getText());
		
		driver.close();
		driver.quit();
		
		
	}

	@Then("I should see a message saying please select a valid type for request")
	public void i_should_see_a_message_saying_please_select_a_valid_type_for_request() {
	    
		WebElement submitHelper = driver.findElement(By.xpath(CreateRequestPage.submitHelper));
		
	    WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(2));
	    wdw.until(ExpectedConditions.textToBePresentInElement(submitHelper, "Unable to create request. Type is invalid. Please provide valid type of request"));
		
		assertEquals("Unable to create request. Type is invalid. Please provide valid type of request", submitHelper.getText());
		
		driver.close();
		driver.quit();
		
	}



	@Then("I should see a message saying please attach a valid receipt")
	public void i_should_see_a_message_saying_please_attach_a_valid_receipt() {
	    
		WebElement submitHelper = driver.findElement(By.xpath(CreateRequestPage.submitHelper));
		
	    WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(2));
	    wdw.until(ExpectedConditions.textToBePresentInElement(submitHelper, "Unable to create request. No receipt attached. Please provide a receipt"));
		
		
		assertEquals("Unable to create request. No receipt attached. Please provide a receipt", submitHelper.getText());
		
		driver.close();
		driver.quit();
		
		
	}

	@Then("I should see a message saying please provide a description for request")
	public void i_should_see_a_message_saying_please_provide_a_description_for_request() {
		
	    WebElement submitHelper = driver.findElement(By.xpath(CreateRequestPage.submitHelper));
	    
	    WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(2));
	    wdw.until(ExpectedConditions.textToBePresentInElement(submitHelper, "Unable to create request. Please provide a valid description"));
		
	
	    
	    assertEquals("Unable to create request. Please provide a valid description", submitHelper.getText());
	    
	    driver.close();
	    driver.quit();
	    
	    
	}
	
	@Given("I enter in a description that is too long")
	public void i_enter_in_a_description_that_is_too_long() {
	   
		WebElement descriptionField = driver.findElement(By.xpath(CreateRequestPage.descriptionInput));
		String stringTooLong = "aaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaaassssssssssssssssssssssssssssssssssssss"
				+ "sssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssssss"
				+ "ssssssssssddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddffffffffffffffffffffffffffffffffffffffffff";
		descriptionField.sendKeys(stringTooLong);
		
	}

	@Then("I should see a message saying description is too long")
	public void i_should_see_a_message_saying_description_is_too_long() {
	    
		WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(2));
		WebElement submitHelper = driver.findElement(By.xpath(CreateRequestPage.submitHelper));
		
		
		WebElement submitButton = driver.findElement(By.xpath(CreateRequestPage.submitButton));
		submitButton.click();//I shouldnt have to click here AGAIN but selenium refuses to click the damn button
		
		wdw.until(ExpectedConditions.textToBePresentInElement(submitHelper, "Unable to create request. Description length too long. Please provide a description under 255 characters"));
		
		
	    assertEquals("Unable to create request. Description length too long. Please provide a description under 255 characters", submitHelper.getText());
	    
	    driver.close();
	    driver.quit();
		
	}
	
	@When("I enter in a valid type in the type field")
	public void i_enter_in_a_valid_type_in_the_type_field() {
		
	    WebElement typeField = driver.findElement(By.xpath(CreateRequestPage.typeInput));
	    typeField.sendKeys("food");
	    
	}
	
	@Given("I enter in a valid amount in the amount field")
	public void i_enter_in_a_valid_amount_in_the_amount_field() {
		
	    WebElement amountField = driver.findElement(By.xpath(CreateRequestPage.amountInput));
	    amountField.sendKeys("1000");
	    
	}
	
	@Then("I should be taken to the employee welcome page")
	public void i_should_be_taken_to_the_employee_welcome_page() {
		
		WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(2));
		wdw.until(ExpectedConditions.urlMatches(WelcomePage.URL));
		
	    assertEquals(WelcomePage.URL, driver.getCurrentUrl());
	    
	    driver.close();
	    driver.quit();
	    
	    
	}

	@Then("I should see a message telling me I have an invalid username\\/password")
	public void i_should_see_a_message_telling_me_i_have_an_invalid_username_password() {
	    
		WebElement wdw = new WebDriverWait(driver, Duration.ofSeconds(2)).until(driver -> driver.findElement(By.xpath("//p[contains(text(),'Invalid username password combination')]")));
		
		WebElement usernameHelper = driver.findElement(By.xpath(LoginPage.usernameHelper));
		assertEquals("Invalid username password combination", usernameHelper.getText());
		
		driver.close();
		driver.quit();
		
		
	}
	
	@When("I enter a valid username")
	public void i_enter_a_valid_username() {
	    WebElement usernameField = driver.findElement(By.xpath(LoginPage.usernameField));
	    usernameField.sendKeys("employeetest");
	}

	@Then("I should be taken to the manager welcome page")
	public void i_should_be_taken_to_the_manager_welcome_page() {
	    
		WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(2));
		wdw.until(ExpectedConditions.urlMatches(WelcomeManagerPage.URL));
		
		assertEquals(WelcomeManagerPage.URL, driver.getCurrentUrl());
		
		driver.close();
		driver.quit();
		
	}
	
	@When("I enter a valid manager username")
	public void i_enter_a_valid_manager_username() {
	    WebElement usernameField = driver.findElement(By.xpath(LoginPage.usernameField));
	    usernameField.sendKeys("managertest");
	}

	@When("I enter in a valid manager password")
	public void i_enter_in_a_valid_manager_password() {
	    
		WebElement passwordField = driver.findElement(By.xpath(LoginPage.passwordField));
		passwordField.sendKeys("password");
		
	}
	
	@Given("I am at the welcome page while logged in as a manager")
	public void i_am_at_the_welcome_page_while_logged_in_as_a_manager() {
	    
		System.setProperty("webdriver.chrome.driver", "C:/WebDrivers/chromedriver.exe");
		driver = new ChromeDriver(); 
		
	    driver.get(LoginPage.URL);
	    
	    WebElement usernameField = driver.findElement(By.xpath(LoginPage.usernameField));
	    WebElement passwordField = driver.findElement(By.xpath(LoginPage.passwordField));
	    WebElement loginButton = driver.findElement(By.xpath(LoginPage.loginButton));
	    
	    usernameField.sendKeys("managertest");
	    passwordField.sendKeys("password");
	    loginButton.click();
		
	}

	@When("I select pending requests from the filter menu")
	public void i_select_pending_requests_from_the_filter_menu() {
	    
		WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(2));
		wdw.until(ExpectedConditions.urlMatches(WelcomeManagerPage.URL));
		
		WebElement pendingFilterButton = driver.findElement(By.xpath(WelcomeManagerPage.pendingFilter));
		WebElement filterDropdown = driver.findElement(By.xpath(WelcomeManagerPage.filterDropdown));
		
		Actions action = new Actions(driver);
		
		action.moveToElement(filterDropdown).moveToElement(pendingFilterButton).click().build().perform();
		
	}

	@Then("I should see all pending requests for all users")
	public void i_should_see_all_pending_requests_for_all_users() {
		
		WebElement wdw = new WebDriverWait(driver, Duration.ofSeconds(2)).until(driver -> driver.findElement(By.className("reimb_status")));
		
	    ArrayList<WebElement> statusArray = (ArrayList<WebElement>) driver.findElements(By.className("reimb_status"));
	    
	    boolean pass = true;
	    
	    for(int i = 0; i < statusArray.size(); i++) {
	    	 
	    	if(statusArray.get(i).getText().compareTo("PENDING") != 0) {
	    		
	    		pass = false; 
	    		
	    	}
	    	
	    }
	    
	    assertEquals(true, pass); 
	    
	    driver.close();
	    driver.quit();
	    
	    
	}

	@When("I select approved requests from the filter menu")
	public void i_select_approved_requests_from_the_filter_menu() {

		WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(2));
		wdw.until(ExpectedConditions.urlMatches(WelcomeManagerPage.URL));
		
		WebElement approvedFilterButton = driver.findElement(By.xpath(WelcomeManagerPage.approvedFilter));
		WebElement filterDropdown = driver.findElement(By.xpath(WelcomeManagerPage.filterDropdown));
		
		Actions action = new Actions(driver);
		
		action.moveToElement(filterDropdown).moveToElement(approvedFilterButton).click().build().perform();
		
		
		
	}

	@Then("I should see all approved requests for all users")
	public void i_should_see_all_approved_requests_for_all_users() {


		WebElement wdw = new WebDriverWait(driver, Duration.ofSeconds(2)).until(driver -> driver.findElement(By.className("reimb_status")));
		
	    ArrayList<WebElement> statusArray = (ArrayList<WebElement>) driver.findElements(By.className("reimb_status"));
	    
	    boolean pass = true;
	    
	    for(int i = 0; i < statusArray.size(); i++) {
	    	 
	    	if(statusArray.get(i).getText().compareTo("APPROVED") != 0) {
	    		
	    		pass = false; 
	    		
	    	}
	    	
	    }
	    
	    assertEquals(true, pass); 
	    
	    driver.close();
	    driver.quit();
	    
		
		
	}

	@When("I select denied requests from the filter menu")
	public void i_select_denied_requests_from_the_filter_menu() {

		WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(2));
		wdw.until(ExpectedConditions.urlMatches(WelcomeManagerPage.URL));
		
		
		WebElement deniedFilterButton = driver.findElement(By.xpath(WelcomeManagerPage.deniedFilter));
		WebElement filterDropdown = driver.findElement(By.xpath(WelcomeManagerPage.filterDropdown));
		
		Actions action = new Actions(driver);
		
		action.moveToElement(filterDropdown).moveToElement(deniedFilterButton).click().build().perform();
		
		
	}

	@Then("I should see all denied requests for all users")
	public void i_should_see_all_denied_requests_for_all_users() {

		
		WebElement wdw = new WebDriverWait(driver, Duration.ofSeconds(2)).until(driver -> driver.findElement(By.className("reimb_status")));
		
	    ArrayList<WebElement> statusArray = (ArrayList<WebElement>) driver.findElements(By.className("reimb_status"));
	    
	    boolean pass = true;
	    
	    for(int i = 0; i < statusArray.size(); i++) {
	    	 
	    	if(statusArray.get(i).getText().compareTo("DENIED") != 0) {
	    		
	    		pass = false; 
	    		
	    	}
	    	
	    }
	    
	    assertEquals(true, pass); 
	    
	    driver.close();
	    driver.quit();
		
		
	}
	
	@When("I click the approve request button")
	public void i_click_the_approve_request_button() {
	    
		WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(2));
		wdw.until(ExpectedConditions.urlMatches(WelcomeManagerPage.URL));
		wdw.until(ExpectedConditions.elementToBeClickable(By.xpath(WelcomeManagerPage.approveRequestButton)));
		
		WebElement approveButton = driver.findElement(By.xpath(WelcomeManagerPage.approveRequestButton));
		approveButton.click();
		
	}

	@Then("I should see a message saying the request has been approved")
	public void i_should_see_a_message_saying_the_request_has_been_approved() {
	   
		WebElement helper = driver.findElement(By.xpath(WelcomeManagerPage.helper));
		
	    WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(2));
	    wdw.until(ExpectedConditions.textToBePresentInElement(helper, "Updating request successful"));
		
		
		
		assertEquals("Updating request successful", helper.getText());
		
		driver.close();
		driver.quit();
		
		
	}

	@When("I sclick the deny request button")
	public void i_sclick_the_deny_request_button() {

		WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(2));
		wdw.until(ExpectedConditions.urlMatches(WelcomeManagerPage.URL));
		wdw.until(ExpectedConditions.elementToBeClickable(By.xpath(WelcomeManagerPage.denyRequestButton)));
		
		WebElement denyButton = driver.findElement(By.xpath(WelcomeManagerPage.denyRequestButton));
		denyButton.click();
		
		
		
	}

	@Then("I should see a message saying the request has been denied")
	public void i_should_see_a_message_saying_the_request_has_been_denied() {
	    
		WebElement helper = driver.findElement(By.xpath(WelcomeManagerPage.helper));
		
	    WebDriverWait wdw = new WebDriverWait(driver, Duration.ofSeconds(2));
	    wdw.until(ExpectedConditions.textToBePresentInElement(helper, "Updating request successful"));
		
		assertEquals("Updating request successful", helper.getText());
		
		driver.close();
		driver.quit();
		
		
	}

	
	
		
}
