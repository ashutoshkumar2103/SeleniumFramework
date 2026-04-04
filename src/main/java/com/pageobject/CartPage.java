package com.pageobject;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.abstractcomponents.AbstractComponent;

public class CartPage extends AbstractComponent {

	WebDriver driver;
	public CartPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

	@FindBy(xpath = "//div[@class='cartSection']/h3")
	private List<WebElement> cartProduct;

	@FindBy(xpath = "//button[contains(text(),'Checkout')]")
	WebElement checkoutBtn;

//	We will use a new thing here and that is PageFactory, which will help us to initialize the WebElements in the page class and we can use them in the test class without writing the code again and again. It will also help us to reduce the code and make it more readable and maintainable.
//	Also PageFactory will help us to use the @FindBy annotation to locate the elements in the page class. hence we dont need to write the code for locating the elements in the test class using driver.findElement etc.

	public Boolean verifyProductDisplay(String productName) {
		Boolean match = cartProduct.stream().anyMatch(cartProduct -> cartProduct.getText().equalsIgnoreCase(productName));
		return match;
	}

	public CheckoutPage goToCheckout() {
		checkoutBtn.click();
		CheckoutPage checkoutPage = new CheckoutPage(driver);
		return checkoutPage;
	}
}
