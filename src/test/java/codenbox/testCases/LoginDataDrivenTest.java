package codenbox.testCases;

import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;

import codenbox.testBase.Base;
import codenbox.utilities.DataProviders;
import pageObjects.HomePage;
import pageObjects.LoginPage;
import pageObjects.MyAccountPage;

// FIRST IMPLEMENTATION (MY OWN)

public class LoginDataDrivenTest extends Base {

	@Test // (dataProvider = "LoginData", dataProviderClass = DataProviders.class)
	public void verify_loginDDT() throws InterruptedException { // String userName, String column, String result

		HomePage hm = new HomePage(getDriver());

		logger.info("*** Starting Login Test ***");

		//
		DataProviders data = new DataProviders();

		String[][] loginData = data.getData();

		for (int i = 0; i < loginData.length; i++) {
			
			Thread.sleep(2000l);
			
			String login = loginData[i][0];
			String password = loginData[i][1];
			String result = loginData[i][2];

			System.out.println(login + " " + password + " " + result);
			
			// home page

			hm.clickMyAccount();
			hm.clickLogin();

			// login page

			LoginPage lp = new LoginPage(getDriver());
			lp.setEmail(login);
			lp.setPassword(password);
			lp.clickLoginBtn();
			
			// my account page
			
			MyAccountPage myAcc = new MyAccountPage(getDriver());
			SoftAssert sa = new SoftAssert();
			
			if (result.equalsIgnoreCase("Invalid") && myAcc.isMyAccountExist()) {
				sa.fail("Wrong but successful login with invalid user: " + login);
				sa.assertTrue(false);
				sa.assertAll();			
			}
			
			if (result.equalsIgnoreCase("Invalid") && !myAcc.isMyAccountExist()) {
				System.out.println("Failed to login with invalid user: " + login);
				sa.assertTrue(true);
				sa.assertAll();
			}
			
			if (result.equalsIgnoreCase("Valid") && myAcc.isMyAccountExist()) {
				System.out.println("Successful login with valid user: " + login);
				sa.assertTrue(true);
				sa.assertAll();
			}
			
			if (result.equalsIgnoreCase("Valid") && !myAcc.isMyAccountExist()) {
				System.out.println("Failed to login with valid user: " + login);
				sa.assertTrue(false);
				sa.assertAll();
			}
			
			if (!myAcc.isMyAccountExist()) {
				continue;
			}

			myAcc.clickLogut();
			
			//
			MyAccountPage myact = new MyAccountPage(getDriver());
			boolean targetPage = myact.isMyAccountExist();
			logger.info("** validation for invalid result **");

		}

	}

}
