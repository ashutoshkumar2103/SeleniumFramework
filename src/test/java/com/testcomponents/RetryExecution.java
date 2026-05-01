package com.testcomponents;

import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

public class RetryExecution implements IRetryAnalyzer{

	int count = 0;
	int retryLimit = 1;
	

	@Override
	public boolean retry(ITestResult result) {
		if(!result.isSuccess() && count < retryLimit) {
			count++;
			return true;
		}
		return false;
	}

}
