package pageObjects;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class HomePage extends BaseComponents {
	
	// create constructor
	
	public HomePage (WebDriver driver) {
		
		super(driver);
		
	}
	
	//locators
	
	@FindBy(xpath = "//a[@class = 'dropdown-toggle']")
	WebElement myAccount;
	
	@FindBy(linkText = "Register")
	WebElement register;
	
	@FindBy(xpath = "//a[contains(@href, '/demo/index.php?route=account/login')]")
	WebElement login;
	
	//action methods
	
	public void clickMyAccount() {
		myAccount.click();
	}
	
	public void clickRegister() {
		register.click();
	}
	
	public void clickLogin() {
		login.click();
	}
	
	

}
