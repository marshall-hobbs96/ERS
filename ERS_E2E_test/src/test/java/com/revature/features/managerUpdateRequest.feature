Feature: Manager approve or deny request

Scenario: Manager approves request (positive test)
	Given I am at the welcome page while logged in as a manager
	When I click the approve request button
	Then I should see a message saying the request has been approved
	
Scenario: Manager denies request (positive test)
	Given I am at the welcome page while logged in as a manager
	When I sclick the deny request button
	Then I should see a message saying the request has been denied
