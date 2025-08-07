package codenbox.testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import codenbox.testBase.Base;
import pageObjects.HomePage;

public class HomeTest extends Base {
	
	@Test (groups = "Regression", retryAnalyzer = codenbox.utilities.Retry.class)
	public void homeAccount() {
		
		logger.info("****Starting Registration Test****");
		
		HomePage myAcc = new HomePage(getDriver());
		
		logger.info("Clicked on My Account Link");
		myAcc.clickMyAccount();
		myAcc.clickRegister();
	}

}
