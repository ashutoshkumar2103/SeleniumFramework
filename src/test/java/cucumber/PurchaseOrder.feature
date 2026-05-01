@tag
Feature: Purchase the order from Ecommerce Website
	I want to use this template for my feature file

Background:
Given I am on ecommerce website
	
@RegressionOrder
Scenario Outline: Positive test of Submitting the order
Given logged in with username <username> and password <password>
When I add the <productName> to the cart
And Checkout with the <productName> and submit the order
Then I should see the confirmation message as "THANKYOU FOR THE ORDER."

Examples:
| username         | password    | productName |
| wwwgmail@gmail.com    | Ashu@1234   | Zara Coat 3 |