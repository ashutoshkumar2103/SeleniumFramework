package com.resources;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;

public class ExtentReportersNG {

	public static ExtentReports getReporterObject() {
//		ExtentReports and ExtendSparkReporter Classes are very helpful to generate the report and configure the report respectively.
		String path = System.getProperty("user.dir") + "/reports/index.html";
		ExtentSparkReporter reporter = new ExtentSparkReporter(path);
		reporter.config().setReportName("Web-Selenium Automation Results");
		reporter.config().setDocumentTitle("Test Results");
		
		ExtentReports extentReports = new ExtentReports();
		extentReports.attachReporter(reporter);
		extentReports.setSystemInfo("Tester", "Ashutosh");
		return extentReports;
	}
}
