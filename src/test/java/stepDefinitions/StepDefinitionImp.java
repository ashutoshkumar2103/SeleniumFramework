package stepDefinitions;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;

import com.pageobject.CartPage;
import com.pageobject.CheckoutPage;
import com.pageobject.ConfirmationPage;
import com.pageobject.LoginPage;
import com.pageobject.ProductCatalog;
import com.testcomponents.BaseTest;

import io.cucumber.java.en.And;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class StepDefinitionImp extends BaseTest {

	public LoginPage login;
	public ProductCatalog productCatalog;
	public ConfirmationPage confirmationPage;

	@Given("I am on ecommerce website")
	public void i_am_on_ecommerce_website() throws IOException {
	    login = launchApplication();
	}

	@Given("^logged in with username (.+) and password (.+)$")
	public void logged_in_with_username_and_password(String username, String password) {
		productCatalog = login.login(username, password);
	}

	@When("^I add the (.+) to the cart$")
	public void i_add_the_to_the_cart(String productName) throws InterruptedException {
		List<WebElement> products = productCatalog.getProductList();
		productCatalog.addProductToCart(productName);
	}

	@And("^Checkout with the (.+) and submit the order$")
	public void checkout_with_the_and_submit_the_order(String productName) {
		CartPage cartPage = productCatalog.goToCartPage();
		Boolean match = cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match);
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		checkoutPage.selectCountry("india");
		confirmationPage = checkoutPage.submitOrder();
	}

	@Then("I should see the confirmation message as {string}")
	public void i_should_see_the_confirmation_message_as(String expectedMessage) {
		String confirmMessage = confirmationPage.getConfirmationMessage();
		System.out.println("Confirmation matched: " + confirmMessage);
		Assert.assertTrue(confirmMessage.equalsIgnoreCase(expectedMessage));
		System.out.println("Confirmation matched: " + confirmMessage);
		driver.close();
	}

	@Then("{string} error message should be displayed")
	public void error_message_should_be_displayed(String expectedErrorMessage) {
		Assert.assertEquals("Incorrect email or password.", login.getErrorMessage());
		driver.close();
	}
}