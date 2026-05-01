@tag
Feature: Error Validation

@ErrorValidations
Scenario Outline: 
	Given I am on ecommerce website
	When logged in with username <username> and password <password>
	Then "Incorrect email or password." error message should be displayed
	
Examples:
	| username		| password    |
	| wwwp@gmail.com     | Ashu@1234 |
