package codenbox.testBase;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
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
import org.openqa.selenium.MutableCapabilities;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.Platform;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
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

	@Parameters({ "OS", "Browser" })
	// @BeforeTest (alwaysRun = true) // @BeforeMethod (groups = {"Regression",
	// "Sanity"})
	@BeforeMethod(alwaysRun = true)
	public void setUp(String osName, String browserName) throws IOException {
		if (getDriver() == null) {

			//ChromeOptions optionsChrome = new ChromeOptions();
			//FirefoxOptions optFirefox = new FirefoxOptions();
			/*
			 * prop = new Properties(); fs = new
			 * FileInputStream("./src//test//resources//data.properties"); // will connect
			 * with a file prop.load(fs); // will load the file
			 * 
			 * // remote or grid test env option
			 * 
			 * if (prop.getProperty("test_env").equalsIgnoreCase("remote")) {
			 * DesiredCapabilities cap = new DesiredCapabilities();
			 * 
			 * // OS switch (osName.toLowerCase()) { case "windows":
			 * cap.setPlatform(Platform.WIN11); break; case "mac":
			 * cap.setPlatform(Platform.MAC); break; default:
			 * System.out.println("OS is not matching"); return; }
			 * 
			 * // Browser switch (browserName.toLowerCase()) { case "chrome":
			 * optionsChrome.addArguments("--incognito"); cap.setBrowserName("chrome");
			 * cap.merge(optionsChrome); break; case "chromeheadless":
			 * optionsChrome.addArguments("headless"); cap.setBrowserName("headless");
			 * cap.merge(optionsChrome); getDriver().manage().window().setSize(new
			 * Dimension(1440, 900)); break; case "firefox": cap.setBrowserName("firefox");
			 * cap.merge(optFirefox); break; case "firefoxheadless":
			 * optFirefox.addArguments("--headless"); cap.setBrowserName("firefox");
			 * cap.merge(optFirefox); getDriver().manage().window().setSize(new
			 * Dimension(1440, 900)); break; default:
			 * System.out.println("There is no valid browser: " + browserName); return; }
			 * 
			 * String hubUrl = "http://192.168.0.105:4444/wd/hub"; //driver = new
			 * RemoteWebDriver(new URL(hubUrl), cap); driver.set(new RemoteWebDriver(new
			 * URL(hubUrl), cap));
			 * 
			 * }
			 */

			prop = new Properties();
			fs = new FileInputStream("./src/test/resources/data.properties");
			prop.load(fs);

			String testEnv = prop.getProperty("test_env");
			String platformName = osName.equalsIgnoreCase("windows") ? "Windows 11"
					: osName.equalsIgnoreCase("mac") ? "macOS" : osName.equalsIgnoreCase("linux") ? "Linux" : null;

			if (platformName == null) {
				System.out.println("OS is not matching");
				return;
			}

			// Remote or grid
			if (testEnv.equalsIgnoreCase("remote")) {
				MutableCapabilities options;

				switch (browserName.toLowerCase()) {
				case "chrome":
					ChromeOptions chromeOptions = new ChromeOptions();
					chromeOptions.addArguments("--incognito");
					chromeOptions.setPlatformName(platformName);
					options = chromeOptions;
					break;

				case "chromeheadless":
					ChromeOptions chromeHeadless = new ChromeOptions();
					chromeHeadless.addArguments("--headless");
					chromeHeadless.setPlatformName(platformName);
					options = chromeHeadless;
					break;

				case "firefox":
					FirefoxOptions firefoxOptions = new FirefoxOptions();
					firefoxOptions.setPlatformName(platformName);
					options = firefoxOptions;
					break;

				case "firefoxheadless":
					FirefoxOptions firefoxHeadless = new FirefoxOptions();
					firefoxHeadless.addArguments("--headless");
					firefoxHeadless.setPlatformName(platformName);
					options = firefoxHeadless;
					break;

				default:
					System.out.println("Invalid browser: " + browserName);
					return;
				}

				driver.set(new RemoteWebDriver(new URL("http://192.168.0.105:4444/wd/hub"), options));

				// For headless browsers, manually set window size
				if (browserName.toLowerCase().contains("headless")) {
					getDriver().manage().window().setSize(new Dimension(1440, 900));
				}
			}

			// local test env option

			if (testEnv.equalsIgnoreCase("local")) {

				switch (browserName.toLowerCase()) {
				case "chrome":
					ChromeOptions chromeLocal = new ChromeOptions();
					chromeLocal.addArguments("--incognito");
					driver.set(new ChromeDriver(chromeLocal));
					break;

				case "chromeheadless":
					ChromeOptions chromeHeadlessLocal = new ChromeOptions();
					chromeHeadlessLocal.addArguments("--headless");
					driver.set(new ChromeDriver(chromeHeadlessLocal));
					getDriver().manage().window().setSize(new Dimension(1440, 900));
					break;

				case "firefox":
					FirefoxOptions firefoxLocal = new FirefoxOptions();
					driver.set(new FirefoxDriver(firefoxLocal));
					break;

				case "firefoxheadless":
					FirefoxOptions firefoxHeadlessLocal = new FirefoxOptions();
					firefoxHeadlessLocal.addArguments("--headless");
					driver.set(new FirefoxDriver(firefoxHeadlessLocal));
					getDriver().manage().window().setSize(new Dimension(1440, 900));
					break;
				}

				/*
				 * switch (browserName.toLowerCase()) { case "chrome":
				 * optionsChrome.addArguments("--incognito"); driver.set(new
				 * ChromeDriver(optionsChrome)); break; case "chromeheadless":
				 * optionsChrome.addArguments("headless"); driver.set(new
				 * ChromeDriver(optionsChrome)); getDriver().manage().window().setSize(new
				 * Dimension(1440, 900)); break; case "firefox": driver.set(new
				 * FirefoxDriver()); break; case "firefoxheadless":
				 * optFirefox.addArguments("--headless"); driver.set(new
				 * FirefoxDriver(optFirefox)); getDriver().manage().window().setSize(new
				 * Dimension(1440, 900)); break; default:
				 * System.out.println("There is no valid browser: " + browserName); return; }
				 */
			}

			getDriver().manage().timeouts().implicitlyWait(Duration.ofSeconds(5));
			getDriver().manage().window().maximize();
			// getDriver().get("https://tutorialsninja.com/demo");
			getDriver().get(prop.getProperty("baseUrl"));
			getDriver().manage().deleteAllCookies();
		}
	}

	// @AfterTest (alwaysRun = true) // @BeforeMethod (groups = {"Regression",
	// "Sanity"})
	@AfterMethod(alwaysRun = true)
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

	// Utility method
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
