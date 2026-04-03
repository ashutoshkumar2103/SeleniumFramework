package com.test;

import java.time.Duration;
import java.util.List;
import org.openqa.selenium.*;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.github.bonigarcia.wdm.WebDriverManager;

public class StandAloneTest {

	public static void main(String[] args) {

		WebDriverManager.chromedriver().setup();
		WebDriver driver = new ChromeDriver();
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
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
				product.findElement(By.cssSelector("b")).getText().equalsIgnoreCase("Zara Coat 3")).findFirst().orElse(null);
		prod.findElement(By.cssSelector(".card-body button:last-of-type")).click();

//		STEP 3: Confirming the Product Addded to cart through Overlay message (toast container)
//		We will use explicit wait for this
		
		wait.until(ExpectedConditions.visibilityOfElementLocated(By.cssSelector("#toast-container")));
		wait.until(ExpectedConditions.invisibilityOf(driver.findElement(By.cssSelector(".ng-animating"))));	
//		wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[@routerlink*='cart']"))).click();
		driver.findElement(By.xpath("//button[@routerlink='/dashboard/cart']")).click();
	}
}
