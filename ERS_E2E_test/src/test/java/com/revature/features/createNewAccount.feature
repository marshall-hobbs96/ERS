Feature: Create new account

Scenario: Accessing the create account page (positive test)
	Given I am at the login page
	When I select the create new account button
	Then I should be taken to the create new account page

Scenario: Creating new account successfully (positive test)
	Given I am at the create account page
	When I type in a new unique username into the username field
	And I type in a new unique password into the password field
	And I type in a first name into the first name field
	And I type in a last name into the last name field
	And I type in a new unique email into the email field
	And I click the create user submit button
	Then I should see a message telling me a new user has been created
	
Scenario: Creating a new account but forgetting username (negative test)
	Given I am at the create account page
	And I type in a new unique password into the password field
	And I type in a first name into the first name field
	And I type in a last name into the last name field
	And I type in a new unique email into the email field
	And I click the create user submit button
	Then I should see a message telling me I am missing a username
	
Scenario: Creating a new account but forgetting password (negative test)
	Given I am at the create account page
	When I type in a new unique username into the username field
	And I type in a first name into the first name field
	And I type in a last name into the last name field
	And I type in a new unique email into the email field
	And I click the create user submit button
	Then I should see a message telling me I am missing a password

Scenario: Creating a new account but forgetting first name (negative test)
	Given I am at the create account page
	When I type in a new unique username into the username field
	And I type in a new unique password into the password field
	And I type in a last name into the last name field
	And I type in a new unique email into the email field
	And I click the create user submit button
	Then I should see a message telling me I am missing a first name

Scenario: Creating a new account but forgetting last name (negative test)
	Given I am at the create account page
	When I type in a new unique username into the username field
	And I type in a new unique password into the password field
	And I type in a first name into the first name field
	And I type in a new unique email into the email field
	And I click the create user submit button
	Then I should see a message telling me I am missing a last name

Scenario: Creating a new account but forgetting email (negative test)
	Given I am at the create account page
	When I type in a new unique username into the username field
	And I type in a new unique password into the password field
	And I type in a first name into the first name field
	And I type in a last name into the last name field
	And I click the create user submit button
	Then I should see a message telling me I am missing an email

Scenario: Creating a new account but username is not unique (negative test)
	Given I am at the create account page
	When I type in an existing username into the username field
	And I type in a new unique password into the password field
	And I type in a first name into the first name field
	And I type in a last name into the last name field
	And I type in a new unique email into the email field
	And I click the create user submit button
	Then I should see a message telling me username is already taken

Scenario: Creating a new account but email is not unique (negative test)
	Given I am at the create account page
	When I type in a new unique username into the username field
	And I type in a new unique password into the password field
	And I type in a first name into the first name field
	And I type in a last name into the last name field
	And I type in an existing email into the email field
	And I click the create user submit button
	Then I should see a message telling me email is already taken

Scenario: Creating a new account but email is not valid (negative test)
	Given I am at the create account page
	When I type in a new unique username into the username field
	And I type in a new unique password into the password field
	And I type in a first name into the first name field
	And I type in a last name into the last name field
	And I type in an invalid (no domain name) email into the email field
	And I click the create user submit button
	Then I should see a message telling me I have an invalid email 
	
Scenario: Creating a new account but retyped password is empty (negative test)
	Given I am at the create account page
	When I type in a new unique username into the username field
	And I type in a new unique password into the password field but forget to retype password
	And I type in a first name into the first name field
	And I type in a last name into the last name field
	And I type in a new unique email into the email field
	And I click the create user submit button
	Then I should see a message telling me the retyped password doesnt match
	
Scenario: Creating a new account but retyped password doesnt match(negative test)
	Given I am at the create account page
	When I type in a new unique username into the username field
	And I type in a new unique password into the password field but retyped password doesnt match
	And I type in a first name into the first name field
	And I type in a last name into the last name field
	And I type in a new unique email into the email field
	And I click the create user submit button
	Then I should see a message telling me the retyped password doesnt match
	
	
	
	
	
	
	