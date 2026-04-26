package com.test;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import com.pageobject.CartPage;
import com.pageobject.CheckoutPage;
import com.pageobject.ConfirmationPage;
import com.pageobject.OrderPage;
import com.pageobject.ProductCatalog;
import com.testcomponents.BaseTest;
import org.apache.commons.io.FileUtils;

public class SubmitOrderTest extends BaseTest {

	String productName = "Zara Coat 3";


	@Test(dataProvider = "getData", groups = {"Purchase"})
	public void submitOrder(HashMap<String, String> data) throws IOException, InterruptedException {

		ProductCatalog productCatalog = login.login(data.get("email"), data.get("password"));

//		STEP 2: Finding the catalogs items
//		ProductCatalog productCatalog = new ProductCatalog(driver);
		List<WebElement> products = productCatalog.getProductList();
		productCatalog.addProductToCart(data.get("product_name"));
		CartPage cartPage = productCatalog.goToCartPage();
		
		Boolean match = cartPage.verifyProductDisplay(data.get("product_name"));
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

//	This is the old way of passing the data to the test method, we can use the HashMap to pass the data to the test method.
//	@DataProvider
//	public Object[][] getData1() {
//		return new Object[][] {{"wwwgmail@gmail.com","Ashu@1234","Zara Coat 3"},{"wwwtest@gmail.com","Ashu@1234","ADIDAS ORIGINAL"}};
//	}

//	This is the modified was of above code for passing the data to the test method, as you can see that we are using the HashMap to pass the data to the test method, this is more readable and maintainable way of passing the data to the test method.
	@DataProvider
	public Object[][] getData() throws IOException {

//		The hasmap can again be written in the form of json and we can use the jackson api to read the data from the json file and pass it to the test method, this is more readable and maintainable way of passing the data to the test method.
//		HashMap<String, String> map = new HashMap<String, String>();
//		map.put("email", "wwwgmail@gmail.com");
//		map.put("password", "Ashu@1234");
//		map.put("product_name", "Zara Coat 3");
//
//		HashMap<String, String> map1 = new HashMap<String, String>();
//		map1.put("email", "wwwtest@gmail.com");
//		map1.put("password", "Ashu@1234");
//		map1.put("product_name", "ADIDAS ORIGINAL");
//
//		return new Object[][] {{map},{map1}};
		List<HashMap<String, String>> data = getJsonDataToHashMap(System.getProperty("user.dir") + "\\src\\test\\java\\com\\data\\PurchaseOrder.json");
		return new Object[][] {{data.get(0)}, {data.get(0)}, {data.get(1)}};
	}
}
