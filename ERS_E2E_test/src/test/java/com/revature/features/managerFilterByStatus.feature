Feature: Manager filter by status

Scenario: Manager filter all requests by pending (positive test)
	Given I am at the welcome page while logged in as a manager
	When I select pending requests from the filter menu 
	Then I should see all pending requests for all users

Scenario: Manager filter all requests by approved (positive test)
	Given I am at the welcome page while logged in as a manager
	When I select approved requests from the filter menu 
	Then I should see all approved requests for all users

Scenario: Manager filter all requests by denied (positive test)
	Given I am at the welcome page while logged in as a manager
	When I select denied requests from the filter menu 
	Then I should see all denied requests for all users


