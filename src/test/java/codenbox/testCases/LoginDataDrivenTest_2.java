package codenbox.testCases;

import org.testng.Assert;
import org.testng.annotations.Test;
//import org.testng.asserts.SoftAssert;

import codenbox.testBase.Base;
import codenbox.utilities.DataProviders;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;

public class LoginDataDrivenTest_2 extends Base {

	@Test(dataProvider = "LoginData", dataProviderClass = DataProviders.class, groups = {"Regression"})
	public void verify_loginDDT_2(String userName, String password, String result) throws InterruptedException {

		HomePage hm = new HomePage(getDriver());

		logger.info("*** Starting Login Test ***");

		//

		//Thread.sleep(2000l);

		System.out.println(userName + " " + password + " " + result);

		// home page

		hm.clickMyAccount();
		hm.clickLogin();

		// login page

		LoginPage lp = new LoginPage(getDriver());
		lp.setEmail(userName);
		lp.setPassword(password);
		lp.clickLoginBtn();

		// my account page

		MyAccountPage myAcc = new MyAccountPage(getDriver());
		Boolean myact = myAcc.isMyAccountExist();
		//SoftAssert sa = new SoftAssert();

		if (result.equalsIgnoreCase("Invalid") && myact) {
			myAcc.clickLogut();
			//sa.fail("Wrong but successful login with invalid user: " + userName);
			Assert.assertTrue(false);
		}

		if (result.equalsIgnoreCase("Invalid") && !myact) {
			System.out.println("Failed to login with invalid user: " + userName);
			//sa.assertTrue(true);
			//sa.assertAll();
			Assert.assertTrue(true);
		}

		if (result.equalsIgnoreCase("Valid") && myact) {
			myAcc.clickLogut();
			System.out.println("Successful login with valid user: " + userName);
			//sa.assertTrue(true);
			//sa.assertAll();
			Assert.assertTrue(true);
		}

		if (result.equalsIgnoreCase("Valid") && !myact) {
			//sa.fail("Failed to login with valid user: " + userName);
			Assert.assertTrue(false);
		}

	}

}
