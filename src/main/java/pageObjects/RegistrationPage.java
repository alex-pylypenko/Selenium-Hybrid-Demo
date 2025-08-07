package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class RegistrationPage extends BaseComponents {

	// create constructor

	public RegistrationPage(WebDriver driver) {

		super(driver);

	}

	// locators

	@FindBy(id = "input-firstname")
	WebElement firstName;

	@FindBy(xpath = "//input[@id = 'input-lastname']")
	WebElement lastName;

	@FindBy(xpath = "//input[@placeholder = 'E-Mail']")
	WebElement email;

	@FindBy(xpath = "//input[@id = 'input-telephone']")
	WebElement telephone;

	@FindBy(xpath = "//input[@id = 'input-password']")
	WebElement password;

	@FindBy(xpath = "//input[@id = 'input-confirm']")
	WebElement confirmPassword;

	@FindBy(xpath = "//input[@name = 'agree']")
	WebElement policy;

	@FindBy(xpath = "//input[@value = 'Continue']")
	WebElement continueBtn;

	@FindBy(xpath = "//h1[normalize-space() = 'Your Account Has Been Created!']")
	WebElement msgConfirmation;

	// action methods

	public void setFirstName(String myFname) {
		firstName.sendKeys(myFname);
	}

	public void setLastName(String myLname) {
		lastName.sendKeys(myLname);
	}

	public void setEmail(String myEmail) {
		email.sendKeys(myEmail);
	}

	public void setTelephone(String myTelephone) {
		telephone.sendKeys(myTelephone);
	}

	public void setPassword(String myPass) {
		password.sendKeys(myPass);
	}

	public void setConfirmPassword(String myConfirm) {
		confirmPassword.sendKeys(myConfirm);
	}

	public void checkPolicy() {
		policy.click();
	}

	public void clickContinue() {
		continueBtn.click();
	}

	public String getConfirmationMSG() {
		try {
			return msgConfirmation.getText();
		} catch (Exception e) {
			System.out.println("Failed to get confirmation message: " + e.getMessage());
			return null;
		}
	}

}
