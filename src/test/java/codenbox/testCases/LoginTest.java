package codenbox.testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import codenbox.testBase.Base;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;

public class LoginTest extends Base {
	
	@Test (groups = {"Regression"})
	public void verify_login() {
		
		HomePage hm = new HomePage(getDriver());
		
		logger.info("*** Starting Login Test ***");
		// home page
		
		hm.clickMyAccount();
		hm.clickLogin();
		
		// login page
		
		LoginPage lp = new LoginPage(getDriver());
		lp.setEmail(prop.getProperty("myEmail_2"));
		lp.setPassword(prop.getProperty("myPassword"));
		lp.clickLoginBtn();
		
		// my account page
		
		MyAccountPage myAcc = new MyAccountPage(getDriver());
		
		SoftAssert sa = new SoftAssert();
		sa.assertTrue(myAcc.isMyAccountExist());
		sa.assertAll();
		
		myAcc.clickLogut();
		
	}

}
