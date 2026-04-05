package com.test;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.pageobject.CartPage;
import com.pageobject.CheckoutPage;
import com.pageobject.ConfirmationPage;
import com.pageobject.LoginPage;
import com.pageobject.ProductCatalog;

import io.github.bonigarcia.wdm.WebDriverManager;

public class SubmitOrderTest {

	public static void main(String[] args) throws InterruptedException {

		String productName = "Zara Coat 3";
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

//		STEP 1: Login to the Website
		LoginPage login = new LoginPage(driver);
		login.goTo();
		ProductCatalog productCatalog = login.login("wwwgmail@gmail.com", "Ashu@1234");

//		STEP 2: Finding the catalogs items
//		ProductCatalog productCatalog = new ProductCatalog(driver);
		List<WebElement> products = productCatalog.getProductList();
		productCatalog.AddProductToCart(productName);
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
//		Quit is used to close all the instances of the browser opened by the driver, whereas close is used to close the current instance of the browser opened by the driver
		driver.quit();
		
//		We can use the below code to write via Actions class as well, and that is wrriten in the above and it is more efficient than the below code as it is more readable and maintainable
//		driver.findElement(By.xpath("//div[@class=\"row\"]//div[@class=\"field\"]//input[@class=\"input txt text-validated\"]")).sendKeys("4387 9384 8530 9384");
//		
//		driver.findElement(By.xpath("//div[@class=\"row\"]//div[@class=\"field small\"]//input[@class=\"input txt\"]")).sendKeys("341");
//		driver.findElement(By.xpath("//div[@class=\"row\"]//div[@class=\"field\"]//input[@class=\"input txt\"]")).sendKeys("Ashutosh");
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//div[@class=\"form-group\"]//input"))).click();
//		WebElement countryInput = driver.findElement(By.xpath("//div[@class=\"form-group\"]//input"));
//		countryInput.sendKeys("india");
//		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".ta-results")));
//		List<WebElement> countryOptions = driver.findElements(By.cssSelector(".ta-results button"));
//
//		WebElement country = countryOptions.stream().filter(option -> option.getText().equals("India")).findFirst().orElse(null);
//		country.click();
//		
////		Clicking checkout
//		driver.findElement(By.cssSelector(".action__submit")).click();
//
////		STEP 5: Verifying the order confirmation
//		String message = "THANKYOU FOR THE ORDER.";
//		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
//		Assert.assertTrue(confirmMessage.equalsIgnoreCase(message));
//		System.out.println("Confirmation matched: " + confirmMessage);
	}
}
