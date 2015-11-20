

package test.java.com.regressiontest;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.Assert;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class SkuCheck {

	@DataProvider(name = "getProdcts")
	public Object[][] getSKUID() {
		String[][] aa = { { "JEWELLERY", "Rings", "Queen of Hearts Ring" },
				{ "JEWELLERY", "Pendants", "Ice Cream Cone Pendant" } };
		return aa;
	}

	@DataProvider(name = "getProdctRingSku")
	public Object[][] getRingSKUID() {
		String[][] aa = { 
				//{ "JEWELLERY", "Earrings", "Coquettish Comma Earrings", "ER0573A" },
				//{ "JEWELLERY", "Bracelets", "Celestial Eclipse Bracelet", "BR0032A" },
				//{ "JEWELLERY", "Bracelets", "The Intertwined Bracelet", "BR0002A" },
				{ "JEWELLERY", "Rings", "Gold Band Ring", "RG0275A" },
				//{ "JEWELLERY", "Rings", "Queen of Hearts Ring", "RG0200A" }
				};
		return aa;
	}

	
	@Test(dataProvider = "getProdctRingSku", enabled = true)
	public void Test_Ring_Sku_id(String menuItems, String subMenu, String productName, String sku)
			throws InterruptedException {
		WebDriver driver = new FirefoxDriver();

		driver.get("http://www.ilovediamonds.com/");
		driver.manage().deleteAllCookies();
		driver.manage().window().maximize();
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.manage().timeouts().pageLoadTimeout(60, TimeUnit.SECONDS);
		driver.manage().timeouts().setScriptTimeout(120, TimeUnit.SECONDS);

		driver.findElement(By.linkText(menuItems)).click();
		Thread.sleep(2000);
		driver.findElement(By.linkText(subMenu)).click();
		Thread.sleep(3000);
		driver.findElement(By.xpath("//a[contains(@title,'" + productName + "')]/img")).click();
		Thread.sleep(3000);

		Integer totalQualiy = driver
				.findElements(By.xpath("//div[@class='input-box']/label[@class='label-radio-configurable']/input"))
				.size();
		for (int k = 1; k <= totalQualiy; k++) {
			String getQualityName = driver
					.findElement(
							By.xpath("//div[@class='input-box']/label[@class='label-radio-configurable'][" + k + "]"))
					.getText();
			driver.findElement(
					By.xpath("//div[@class='input-box']/label[@class='label-radio-configurable'][" + k + "]/input"))
					.click();
			Thread.sleep(2000);
			String expectedSku = sku + "-" + getQualityName;
			try{
				List<WebElement> e = new ArrayList<WebElement>(driver.findElements(By.xpath("//select/option")));
				for (WebElement i : e) {
					i.click();
					Thread.sleep(5000);
					String s1 = "";
					String s = i.getText().trim();
					if (s.contains("inches"))
						s1 = s.split("inches")[0].trim();
					else if (s.contains("W")) {
						String h = s.split(" ")[2].trim();
						String w = s.split(" ")[7].trim();
						s1 = h + "X" + w;
					} else
						s1 = s;

					String expectedSku1 = expectedSku + "-" + s1;
					String actualSku = driver
							.findElement(By
									.xpath("//div[@id='product-options-wrapper']/table//span[contains(@class,'product-sku')]"))
							.getText();
					Assert.assertEquals(expectedSku1, actualSku);
				}
			} catch(Exception ex) {
				String expectedSku1 = expectedSku;
				String actualSku = driver
						.findElement(By
								.xpath("//div[@id='product-options-wrapper']/table//span[contains(@class,'product-sku')]"))
						.getText();
				Assert.assertEquals(expectedSku1, actualSku);
			}
		}
		driver.close();

	}
}
