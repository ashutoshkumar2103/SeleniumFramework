package com.pageobject;

import java.util.List;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

import com.abstractcomponents.AbstractComponent;

public class ProductCatalog extends AbstractComponent {

	WebDriver driver;
	public ProductCatalog(WebDriver driver) {
		super(driver);
		this.driver = driver;
		PageFactory.initElements(driver, this);
	}

//	List<WebElement> products = driver.findElements(By.cssSelector(".mb-3"));
	@FindBy(css = ".mb-3")
	List<WebElement> products;

	@FindBy(css = ".ng-animating")
	WebElement spinner;

//	We used the By class to locate the elements
//	And here PageFactory cannot be used because we are using the By class to locate the elements and PageFactory is used to locate the elements using the @FindBy annotation. Hence we will use the By class to locate the elements and then we will use the PageFactory to initialize the elements in the page class.
	By productsBy = By.cssSelector(".mb-3");
	By addToCartBtn = By.cssSelector(".card-body button:last-of-type");
	By toastMessage = By.cssSelector("#toast-container");

	public List<WebElement> getProductList() {
		waitForElementToAppear(productsBy);
		return products;
	}
	
	@FindBy(xpath = "//button[@routerlink='/dashboard/cart']")
	WebElement cartBtn;

	public WebElement getProductByName(String productName) {
		WebElement prod = getProductList().stream().filter(product -> 
		product.findElement(By.cssSelector("b")).getText().equalsIgnoreCase(productName)).findFirst().orElse(null);
		return prod;
	}

	public void AddProductToCart(String productName) {
//		Can you apply PageFactory within WebElement.findElement() method? 
//		No, you cannot apply PageFactory within WebElement.findElement() method because PageFactory is used to initialize the elements in the page class and WebElement.findElement() is used to locate the elements in the page class. Hence we cannot use PageFactory within WebElement.findElement() method.
//		It is not possible to apply Pagefactory within WebElement.findElement() method.
		WebElement prod = getProductByName(productName);
		prod.findElement(addToCartBtn).click();
		waitForElementToAppear(toastMessage);
		waitForElementToDisappear(spinner);
	}
}
