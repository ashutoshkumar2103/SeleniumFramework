package com.test;

import java.io.IOException;
import java.time.Duration;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pageobject.CartPage;
import com.pageobject.CheckoutPage;
import com.pageobject.ConfirmationPage;
import com.pageobject.LoginPage;
import com.pageobject.OrderPage;
import com.pageobject.ProductCatalog;
import com.testcomponents.BaseTest;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SubmitOrderTest extends BaseTest {

	String productName = "Zara Coat 3";

	@Test
	public void submitOrder() throws IOException, InterruptedException {

		ProductCatalog productCatalog = login.login("wwwgmail@gmail.com", "Ashu@1234");

//		STEP 2: Finding the catalogs items
//		ProductCatalog productCatalog = new ProductCatalog(driver);
		List<WebElement> products = productCatalog.getProductList();
		productCatalog.addProductToCart(productName);
		CartPage cartPage = productCatalog.goToCartPage();
		
		Boolean match = cartPage.verifyProductDisplay(productName);
		Assert.assertTrue(match);
//		cartPage.goToCheckout();
		CheckoutPage checkoutPage = cartPage.goToCheckout();
		
		checkoutPage.selectCountry("india");
		ConfirmationPage confirmationPage = checkoutPage.submitOrder();
		String confirmMessage = confirmationPage.getConfirmationMessage();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase("THANKYOU FOR THE ORDER."));
		System.out.println("Confirmation matched: " + confirmMessage);
	}

//	To verify Zara Coat 3 is displaying in the order pagw
	@Test(dependsOnMethods = "submitOrder")
	public void OrderHistoryTest() {
		ProductCatalog productCatalog = login.login("wwwgmail@gmail.com", "Ashu@1234");
		OrderPage orderPage = productCatalog.goToOrderPage();
		Assert.assertTrue(orderPage.verifyOrderDisplay(productName));
	}
}
