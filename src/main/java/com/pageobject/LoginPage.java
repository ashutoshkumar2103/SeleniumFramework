package com.pageobject;

import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

import com.abstractcomponents.AbstractComponent;

public class LoginPage extends AbstractComponent {

	WebDriver driver;
	public LoginPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

//	We will use a new thing here and that is PageFactory, which will help us to initialize the WebElements in the page class and we can use them in the test class without writing the code again and again. It will also help us to reduce the code and make it more readable and maintainable.
//	Also PageFactory will help us to use the @FindBy annotation to locate the elements in the page class. hence we dont need to write the code for locating the elements in the test class using driver.findElement etc.

	@FindBy(id = "userEmail")
	WebElement email;

	@FindBy(id = "userPassword")
	WebElement password;

	@FindBy(id = "login")
	WebElement loginBtn;

	@FindBy(css = "[class*='flyInOut']")
	WebElement errorMessage;

	public ProductCatalog login(String userEmail, String userPassword) {
		email.sendKeys(userEmail);
		password.sendKeys(userPassword);
		loginBtn.click();
		ProductCatalog productCatalog = new ProductCatalog(driver);
		return productCatalog;
	}

	public String getErrorMessage() throws InterruptedException {
		Thread.sleep(10);
		waitForWebElementToAppear(errorMessage);
		return errorMessage.getText();
	}

	public void goTo() {
		driver.get("https://rahulshettyacademy.com/client");
	}
}
