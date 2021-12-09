Feature: Create new request


Scenario: Creating a new request successfully (positive test)
	Given I am at the create request page
	And I enter in a valid amount in the amount field
	And I enter in a valid description in the description field
	And I enter in a valid type in the type field
	And I attach a valid receipt
	And I click the submit button
	Then I should see a message saying request successfully submitted 
	
Scenario: Creating a new request amount equal to or less than zero (negative test)
	Given I am at the create request page
	When I enter in an amount equal to or less than zero
	And I enter in a valid description in the description field
	And I enter in a valid type in the type field
	And I attach a valid receipt
	And I click the submit button
	Then I should see a message saying request amount can not be equal to or less than zero 

Scenario: Creating a new request type not selected (negative test)
	Given I am at the create request page
	And I enter in a valid amount in the amount field
	And I enter in a valid description in the description field
	And I attach a valid receipt
	And I click the submit button
	Then I should see a message saying please select a valid type for request

Scenario: Creating a new request no receipt attached (negative test)
	Given I am at the create request page
	And I enter in a valid amount in the amount field
	And I enter in a valid description in the description field
	And I enter in a valid type in the type field
	And I click the submit button
	Then I should see a message saying please attach a valid receipt

#Scenario: Creating a new request description too long (negative test)
#	Given I am at the create request page
#	And I enter in a valid amount in the amount field
#	And I enter in a valid type in the type field
#	And I attach a valid receipt
#	And I enter in a description that is too long
#	And I click the submit button
#	Then I should see a message saying description is too long


	