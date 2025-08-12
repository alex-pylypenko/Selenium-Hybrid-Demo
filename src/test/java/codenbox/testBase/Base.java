package codenbox.testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.util.Date;
import java.util.Properties;

import org.apache.commons.io.FileUtils;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

public class Base {
    private static ThreadLocal<WebDriver> driver = new ThreadLocal<>();
    public Logger logger = LogManager.getLogger(this.getClass());
    public static Properties prop;
	public FileInputStream fs;

	public WebDriver getDriver() {
		return driver.get();
	}

	@Parameters({"Browser"})
	//@BeforeTest (alwaysRun = true) // @BeforeMethod (groups = {"Regression", "Sanity"})
	@BeforeMethod (alwaysRun = true)
	public void setUp(String browserName) throws IOException {
		if (getDriver() == null) {
			
			ChromeOptions options = new ChromeOptions();
			FirefoxOptions optFirefox = new FirefoxOptions();
			prop = new Properties();
			fs = new FileInputStream("./src//test//resources//data.properties"); // will connect with a file
			prop.load(fs); // will load the file

			switch (browserName.toLowerCase()) {
			case "chrome":
				options.addArguments("--incognito");
				driver.set(new ChromeDriver(options));
				break;
			case "chromeheadless":
				options.addArguments("headless");
				driver.set(new ChromeDriver(options));
				getDriver().manage().window().setSize(new Dimension(1440, 900));
				break;
			case "firefox":
				driver.set(new FirefoxDriver());
				break;
			case "firefoxheadless":
				optFirefox.addArguments("--headless");
				driver.set(new FirefoxDriver(optFirefox));
				getDriver().manage().window().setSize(new Dimension(1440, 900));
				break;
			default:
				System.out.println("There is no valid browser: " + browserName);
				return;
			}

			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			getDriver().manage().window().maximize();
			//getDriver().get("https://tutorialsninja.com/demo");
			getDriver().get(prop.getProperty("baseUrl"));
		}
	}

	//@AfterTest (alwaysRun = true) // @BeforeMethod (groups = {"Regression", "Sanity"})
	@AfterMethod (alwaysRun = true)
	public void tearDown() {
		if (getDriver() != null) {
			getDriver().quit();
			driver.remove();
		}
	}

	// Helper methods
	public String getRandomString() {
		return RandomStringUtils.randomAlphabetic(8);
	}

	public String getRandomNumber(int length) {
		return RandomStringUtils.randomNumeric(length);
	}

	public String getRandomAlphaNumeric() {
		String stringValue = RandomStringUtils.randomAlphabetic(4);
		String numericValue = RandomStringUtils.randomNumeric(3);
		return (stringValue + numericValue + "!@");
	}
	
	public void waitForElementVisible(By locator, int seconds) {
	    new WebDriverWait(getDriver(), Duration.ofSeconds(seconds))
	        .until(ExpectedConditions.visibilityOfElementLocated(locator));
	}
	
//	public String getScreenshot(String methodName) throws IOException {
//		
//		String currentTimeStamp=new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());
//		File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);
//		String targetFilePath = System.getProperty("user.dir") + "\\screenshots\\" + methodName + "_" + currentTimeStamp + ".png";
//		File targetFile = new File(targetFilePath);
//		FileUtils.copyFile(srcFile, targetFile);
//		
//		return targetFilePath;
//		
//	}
	
	public String getScreenshot(String methodName) throws IOException {
	    String timestamp = new SimpleDateFormat("yyyy.MM.dd.HH.mm.ss").format(new Date());

	    File srcFile = ((TakesScreenshot) getDriver()).getScreenshotAs(OutputType.FILE);

	    String relativePath = "screenshots/" + methodName + "_" + timestamp + ".png";
	    String fullPath = System.getProperty("user.dir") + File.separator + relativePath;

	    // Ensure directory exists
	    new File(System.getProperty("user.dir") + File.separator + "screenshots").mkdirs();

	    FileUtils.copyFile(srcFile, new File(fullPath));

	    return relativePath; // ✅ Return relative path
	}

}
