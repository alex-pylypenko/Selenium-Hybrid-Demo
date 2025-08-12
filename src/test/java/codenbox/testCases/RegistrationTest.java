package codenbox.testCases;

import org.testng.Assert;
import org.testng.annotations.Test;

import codenbox.testBase.Base;
import pageObjects.HomePage;
import pageObjects.RegistrationPage;

public class RegistrationTest extends Base {

	@Test //(groups = {"Regression", "Sanity"})
	public void verify_refistration() {

		logger.info("Enter Registration Details");
		
		HomePage myAcc = new HomePage(getDriver());
		myAcc.clickMyAccount();
		myAcc.clickRegister();
		
		RegistrationPage reg = new RegistrationPage(getDriver());
		reg.setFirstName(getRandomString().toUpperCase());
		reg.setLastName(getRandomString().toUpperCase());
		reg.setEmail(getRandomString() + "@gmail.com");
		reg.setTelephone(getRandomNumber(9));
		String enterPassword = getRandomAlphaNumeric();
		reg.setPassword(enterPassword);
		reg.setConfirmPassword(enterPassword);
		reg.checkPolicy();
		reg.clickContinue();
		
		String actualMsg = reg.getConfirmationMSG();
		
		if (actualMsg.equalsIgnoreCase("Your Account Has Been Created!")) {
			Assert.assertTrue(true); //passed
		} else {
			System.out.println("Message didn't match. Test case failed");
			Assert.fail();
		}
	}

}
