package test.java.com.smoketest;


import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class Add_to_shortlist {

	@DataProvider(name = "getProdcts")
	public Object[][] getSKUID() {
		String[][] aa = { { "JEWELLERY", "Rings", "Queen of Hearts Ring" }};
		return aa;
	}
	@Test(dataProvider = "getProdcts", enabled = true)
	public void Test_shortlist(String menuItems, String subMenu, String productName)
			throws InterruptedException {
		WebDriver driver = new FirefoxDriver();

		driver.get("http://www.ilovediamonds.com/");
		//driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(120, TimeUnit.SECONDS);
		driver.findElement(By.xpath("//div[@id='ajax_register_close']")).click();
		
		driver.manage().timeouts().implicitlyWait(40, TimeUnit.SECONDS);
		driver.findElement(By.id("link-account")).click();
		driver.findElement(By.id("mini-login")).sendKeys("alaka@ilovediamonds.com");
		driver.findElement(By.id("mini-password")).sendKeys("123456");
		driver.findElement(By.xpath(".//div[@class='login_buttons_outer']/div[@class='actions']/button[@class='button']")).click();
		driver.findElement(By.xpath("//a[@class='logo']/img[@alt='ILoveDiamonds.Com']")).click();
		
		driver.findElement(By.linkText(menuItems)).click();
		Thread.sleep(2000);
		driver.findElement(By.linkText(subMenu)).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[contains(@title,'" + productName + "')]/img")).click();
		Thread.sleep(3000);
		
		
    		
		WebElement button1 = driver.findElement(By.xpath("//input[@class='add-wishlist2 1']"));
   	    button1.click();
   	    Thread.sleep(4000);
   	    driver.findElement(By.xpath("//textarea[@title='Comment']")).sendKeys("This product is very nice");
   	    driver.findElement(By.xpath("//button[@title='Share Shortlist']")).click();
   	    driver.findElement(By.xpath("//textarea[@id='email_address']")).sendKeys("alaka@ilovediamonds.com");
   	    driver.findElement(By.xpath("//button[@class='button'][@title='Share Shortlist']")).click();
    		
        		
    		driver.close();
    		
	}
}
