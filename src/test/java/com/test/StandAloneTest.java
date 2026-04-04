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
import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args) {

		String productName = "Zara Coat 3";
		
		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		driver.get("https://rahulshettyacademy.com/client");

//		STEP 1: LOGIN
		driver.findElement(By.id("userEmail")).sendKeys("wwwgmail@gmail.com");
		driver.findElement(By.id("userPassword")).sendKeys("Ashu@1234");
		driver.findElement(By.id("login")).click();
		WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));

//		STEP 2: Finding the catalogs items
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector(".mb-3")));
		List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
		WebElement prod = products.stream().filter(product -> 
				product.findElement(By.cssSelector("b")).getText().equalsIgnoreCase(productName)).findFirst().orElse(null);
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

//		STEP 3: Confirming the Product Addded to cart through Overlay message (toast container)
//		We will use explicit wait for this
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));	
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@routerlink*='cart']"))).click();
		driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();

//		STEP 4: Verifying the product in the cart and Checking out the from the cart page
		List<WebElement> cartProducts = driver.findElements(By.xpath("//div[@class='cartSection']/h3"));
		Boolean isProductInCart = cartProducts.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
		Assert.assertTrue(isProductInCart);
		driver.findElement(By.xpath("//button[contains(text(),'Checkout')]")).click();

//		Using Actions class to write in the input field
		
		Actions a = new Actions(driver);
		a.sendKeys(driver.findElement(By.cssSelector("[placeholder='Select Country']")), "india").build().perform();
		wait.until(ExpectedConditions.visibilityOfAllElementsLocatedBy(By.cssSelector(".ta-results")));
		driver.findElement(By.cssSelector(".ta-item:nth-of-type(2)")).click();
		
//		Checking out
		driver.findElement(By.cssSelector(".action__submit")).click();
		
//		STEP 5: Verifying the order confirmation
		String message = "THANKYOU FOR THE ORDER.";
		String confirmMessage = driver.findElement(By.cssSelector(".hero-primary")).getText();
		Assert.assertTrue(confirmMessage.equalsIgnoreCase(message));
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
