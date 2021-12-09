Feature: Manager login 

Scenario: Manager successfully logs in (positive test)
	Given I am at the login page 
	When I enter a valid manager username
	And I enter in a valid manager password 
	And I click the login button
	Then I should be taken to the manager welcome page
