package main.java.com.ilovediamonds.basetest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import main.java.com.ilovediamonds.utilities.Utilities;



public class BaseTest {
	protected WebDriver driver1 = null;
	protected Utilities utility = null;
	protected Integer count = null;
	protected String to=null;
	protected boolean isTotalCount;
public ThreadLocal<WebDriver> threadDriver = new InheritableThreadLocal<WebDriver>();
	

	protected WebDriver getDriver(String url, String browser) {
		WebDriver d = null;

		if (browser.equalsIgnoreCase("Firefox"))
			d = new FirefoxDriver();
		if (browser.equalsIgnoreCase("Chrome")) {
			System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
			d = new ChromeDriver();
			/*if (browser.equalsIgnoreCase("Safari")) {
				System.setProperty("webdriver.safari.driver", "C://Users//ILD LT 015//SafariDriver.exe");
				d = new SafariDriver();*/
		
		
		}

		d.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		d.get(url);
		d.manage().window().maximize();
		d.manage().timeouts().implicitlyWait(120, TimeUnit.SECONDS);
		d.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		d.manage().timeouts().setScriptTimeout(120, TimeUnit.SECONDS);
		d.manage().deleteAllCookies();
		return d;

	}

	// User passes the parameter i.e numbers during runtime in build.xml file
	@BeforeTest
	
	@Parameters({ "numberOfCount","to"})
	
	public void beforeSetup1(String noCount,String to) {

		if (noCount.equalsIgnoreCase("ALL") || noCount.equalsIgnoreCase("max"))
			this.isTotalCount = true;
		else {
			this.isTotalCount = false;
			this.count = Integer.parseInt(noCount);
		}
		utility = new Utilities();
		this.to=to;

	}

	// User choose the type of environment during runtime in build.xml file
	@BeforeMethod

	@Parameters({ "environment", "browser" })
	public void beforeSetup(String env, String browser) {
		threadDriver.remove();
		if (env.equalsIgnoreCase("prod"))
			threadDriver.set(getDriver("http://www.ilovediamonds.com", browser));
		else if (env.equalsIgnoreCase("dev"))
			threadDriver.set(getDriver("http://www.dev.ilovediamonds.com", browser));
		else if (env.equalsIgnoreCase("staging"))
			threadDriver.set(getDriver("http://www.stage.ilovediamonds.com", browser));
		// utility = new Utilities();
	}

	// This will execute after each test.java program i.e closing the windows &
	// quit the driver
	@AfterMethod
	public void closeDriver() {
		if (threadDriver.get() != null) {
			threadDriver.get().quit();
			// driver.close();

		}
	}
}

