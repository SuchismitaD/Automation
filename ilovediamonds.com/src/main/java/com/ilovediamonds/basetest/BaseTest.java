package main.java.com.ilovediamonds.basetest;

import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Parameters;

import main.java.com.ilovediamonds.utilities.Utilities;
import main.java.com.ilovediamonds.utilities.Utilities_bracelet;


public class BaseTest {
      protected WebDriver driver = null;
      protected Utilities utility = null;
      protected Utilities_bracelet utility1 = null;
      protected Integer count = null;
      protected boolean isTotalCount;

      protected WebDriver getDriver(String url, String browser) {
          WebDriver d = null;

          if (browser.equalsIgnoreCase("Firefox"))
                          d = new FirefoxDriver();
          if (browser.equalsIgnoreCase("Chrome")) {
                          System.setProperty("webdriver.chrome.driver", "chromedriver.exe");
                          d = new ChromeDriver();
                          
          
          }

          d.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
          d.get(url);
          d.manage().window().maximize();
          d.manage().timeouts().implicitlyWait(100, TimeUnit.SECONDS);
          d.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
          d.manage().timeouts().setScriptTimeout(120, TimeUnit.SECONDS);
          d.manage().deleteAllCookies();
          return d;

}


      // User passes the parameter i.e numbers during runtime in build.xml file
      @BeforeTest
      @Parameters({ "numberOfCount" })
      public void initialize(String noCount) {
            if (noCount.equalsIgnoreCase("ALL") || noCount.equalsIgnoreCase("max"))
                  this.isTotalCount = true;
            else {
                  this.isTotalCount = false;
                  this.count = Integer.parseInt(noCount);
            }
            utility = new Utilities();
            utility1 = new Utilities_bracelet();
      }

      // User choose the type of environment during runtime in build.xml file
      @BeforeMethod
   
      @Parameters({ "environment", "browser" })
      public void beforeSetup(String env, String browser) {
                      if (env.equalsIgnoreCase("prod"))
                                      driver = getDriver("http://www.ilovediamonds.com", browser);
                      else if (env.equalsIgnoreCase("dev"))
                                      driver = getDriver("http://www.dev.ilovediamonds.com", browser);
                      else if (env.equalsIgnoreCase("staging"))
                                      driver = getDriver("http://www.stage.ilovediamonds.com", browser);
                      // utility = new Utilities();
      }

      // This will execute after each test.java program i.e closing the windows &
      // quit the driver
      @AfterMethod
      public void closeDriver() {
            if (driver != null) {
                  driver.quit();
                  // driver.close();

            }
      }
}
