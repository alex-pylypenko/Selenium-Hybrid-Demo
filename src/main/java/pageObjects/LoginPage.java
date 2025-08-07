package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class LoginPage extends BaseComponents {

	public LoginPage(WebDriver driver) {

		super(driver);

	}

	// locators

	@FindBy(xpath = "//input[@placeholder = 'E-Mail Address']")
	WebElement email;

	@FindBy(xpath = "//input[@placeholder = 'Password']")
	WebElement pass;

	@FindBy(xpath = "//input[@class = 'btn btn-primary']")
	WebElement loginBtn;

	// action methods

	public void setEmail(String usersEmail) {
		email.clear();
		email.sendKeys(usersEmail);
	}

	public void setPassword(String usersPass) {
		pass.clear();
		pass.sendKeys(usersPass);
	}

	public void clickLoginBtn() {
		loginBtn.click();
	}
	

}
