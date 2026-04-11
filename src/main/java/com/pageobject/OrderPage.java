package com.pageobject;

import java.util.List;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.abstractcomponents.AbstractComponent;

public class OrderPage extends AbstractComponent {

	WebDriver driver;
	
	public OrderPage(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}



	@FindBy(css = "tr td:nth-child(3)")
	private List<WebElement> orderProducts;

	@FindBy(xpath = "//button[contains(text(),'Checkout')]")
	WebElement checkoutBtn;

	public Boolean verifyOrderDisplay(String productName) {
		Boolean match = orderProducts.stream().anyMatch(orderProduct -> orderProduct.getText().equalsIgnoreCase(productName));
		return match;
	}

}
