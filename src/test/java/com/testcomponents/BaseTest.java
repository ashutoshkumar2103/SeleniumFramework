package com.testcomponents;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Duration;
import java.util.HashMap;
import java.util.List;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.pageobject.LoginPage;

import io.github.bonigarcia.wdm.WebDriverManager;

public class BaseTest {

	public WebDriver driver;
	public LoginPage login;

	public WebDriver initializeDriver() throws IOException {
//		FileInputStream is used to read the data from the file and load it into the Properties object
		FileInputStream fis = new FileInputStream(System.getProperty("user.dir") + "\\src\\main\\java\\com\\resources\\GlobalData.properties");
//		Properties class is used for reading the data from the properties file and it is a key-value pair, where the key is the name of the property and the value is the value of the property
		Properties prop = new Properties();
		prop.load(fis);
		String browName = prop.getProperty("browser");
		if (browName.equalsIgnoreCase("chrome")) {
			WebDriverManager.chromedriver().setup();
			driver = new ChromeDriver();
		}
		driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
		driver.manage().window().maximize();
		return driver;
	}

	public List<HashMap<String, String>> getJsonDataToHashMap(String filepath) throws IOException {

//		read json to String
		String jsonContent = FileUtils.readFileToString(new File(filepath), StandardCharsets.UTF_8);

//		String to HashMap - de serialization using jackson data bind
		ObjectMapper mapper = new ObjectMapper();
		List<HashMap<String, String>> data = mapper.readValue(jsonContent, new TypeReference<List<HashMap<String, String>>>() {});
		return data;
	}

	@BeforeMethod(alwaysRun = true)
	public LoginPage launchApplication() throws IOException {
		driver = initializeDriver();
		login = new LoginPage(driver);
		login.goTo();
		return login;
	}

	@AfterMethod(alwaysRun = true)
	public void tearDown() {
		driver.quit();
	}
}
