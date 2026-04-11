package com.test;

import java.io.IOException;
import java.util.List;

import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.pageobject.ProductCatalog;
import com.testcomponents.BaseTest;

public class ErrorValidationTest extends BaseTest {

	@Test(groups = {"ErrorHandling"})
	public void submitOrder() throws IOException, InterruptedException {
//		String productName = "ZARA COAT 3" ;
		login.login("wwwgmail@gmail.com", "Asu@1234");
		login.getErrorMessage();
		Assert.assertEquals("Incorrect email or password.", login.getErrorMessage());
	}

	@Test
	public void productErrorValidation() throws IOException, InterruptedException {
		String productName = "ZARA COAT 3";
		ProductCatalog productCatalog = login.login("wwwgmail@gmail.com", "Ashu@1234");
		List<WebElement> products = productCatalog.getProductList();
		productCatalog.addProductToCart(productName);
		productCatalog.goToCartPage();
	}
}