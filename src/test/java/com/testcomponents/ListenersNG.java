package com.testcomponents;

import java.io.IOException;

import org.openqa.selenium.WebDriver;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestResult;
import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.resources.ExtentReportersNG;

public class ListenersNG extends BaseTest implements ITestListener{
//	We are extending the BaseTest class to use the methods of the BaseTest class to capture the screenshots in case of test failure and attach the screenshots in the report.


	ExtentTest test;
	ExtentReports extent = ExtentReportersNG.getReporterObject();
	ThreadLocal<ExtentTest> localThread = new ThreadLocal<>();

	@Override
	public void onTestStart(ITestResult result) {
		test = extent.createTest(result.getMethod().getMethodName());
		localThread.set(test);
	}

	@Override
	public void onTestSuccess(ITestResult result) {
		test.log(Status.PASS, "Test Passed");
	}

	@Override
	public void onTestFailure(ITestResult result) {
//		test.log(Status.FAIL, result.getThrowable());
//		OR
		localThread.get().fail(result.getThrowable());

//		Here driver is null because the driver is not initialized in the BaseTest class, and since result has the reference of test class and has all the details of the test class, we can retrieve the driver from the test class and use it to capture the screenshot in case of test failure and attach the screenshots in the report.
		try {
			driver = (WebDriver) result.getTestClass().getRealClass().getField("driver").get(result.getInstance());
		}
		catch (Exception e) {
			e.printStackTrace();
		}
		
		String filePath = null;
		try {
			filePath = getScreenshot(result.getMethod().getMethodName(), driver);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		localThread.get().addScreenCaptureFromPath(filePath, result.getMethod().getMethodName());
	}

	@Override
	public void onFinish(ITestContext context) {
		extent.flush();
	}
}
