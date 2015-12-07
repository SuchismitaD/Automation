
package test.java.com.smoketest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import main.java.com.ilovediamonds.basetest.BaseTest;
import main.java.com.ilovediamonds.entity.Product;
import main.java.com.ilovediamonds.utilities.SendEmail;


public class RingCSVAddToCart extends BaseTest {
	private static String htmlReport;
	Map<String, Boolean> finalResult;
	private WebDriver driver;
	private Map<String, List<String[]>> totalValues;

	@DataProvider(name = "getRandomData")
	public Object[][] getData() {
		String workingdirectory = System.getProperty("user.dir");
		LinkedHashMap<String, Integer> colNameToLocation = new LinkedHashMap<String, Integer>();
		String s[][] = utility.getDataFromCSV(workingdirectory + "\\bracelet.csv", colNameToLocation);
		totalValues = utility.getMapValues(s, colNameToLocation);
		Integer l = totalValues.size();
		String[][] random = new String[l][1];
		for (int i = 0; i < l; i++) {
			random[i][0] = totalValues.keySet().toArray()[i].toString();
		}
		if (isTotalCount)
			return random;
		else {
			String[][] sss = getRandomKey(this.count, random);
			return sss;
		}
		/// return random;// here it stores the no of input from the test suite
		/// file
		// i.e the user input

	}

	@Test(dataProvider = "getRandomData")
	public void testCSVData(String key) throws InterruptedException {

		List<String[]> ss = totalValues.get(key);
		List<Map<String, Boolean>> finalResultForAll = new ArrayList<Map<String, Boolean>>();
		driver = threadDriver.get();
		initilizeEtry();
		// driver.findElement(By.xpath("//div[@id='ajax_register_close']")).click();
		driver.findElement(By.xpath("//div[@class='search_outer']//img")).click();
		Thread.sleep(3000);
		WebElement elem = driver.findElement(By.xpath("//*[@id='ild_search_box']//form//div//input[@id='search']"));
		elem.sendKeys(key);

		elem.sendKeys("\n");

		driver.findElement(By.xpath("//a[@class='product-image']")).click();
		for (String[] a : ss) {
			System.out.println(a[0] + " " + a[1] + "   " + a[2]);
			if (a[0].toUpperCase().contains("SI-GH"))
				driver.findElement(
						By.xpath("//div[@class='input-box']/label[@class='label-radio-configurable'][" + 1 + "]/input"))
						.click();
			else
				driver.findElement(
						By.xpath("//div[@class='input-box']/label[@class='label-radio-configurable'][" + 2 + "]/input"))
						.click();
			List<WebElement> e = new ArrayList<WebElement>(driver.findElements(By.xpath("//select/option")));
			for (WebElement i : e) {
				if (i.getText().trim().equalsIgnoreCase(a[2].trim().toString())) {
					i.click();
					break;
				}
			}
			Thread.sleep(2000);
			// End
			Product productItms = null;
			productItms = new Product(driver);

			// verification of sku of the product
			String actualsku = productItms.getsku();
			String expectedsku = a[0];

			if (!(actualsku.trim().toUpperCase().contains(expectedsku.trim().toUpperCase()))) {

				finalResult.put("Sku", finalResult.get("Sku") & false);
			}

			if (productItms.getGoldWeight() != null && !productItms.getGoldWeight().trim().equals("N/A")) {

				String actualGoldWeight = utility.convertDecimal(productItms.getGoldWeight().split(" ")[0].toString());

				String expectedGoldWeight = utility.convertDecimal(a[1]);

				if (!(actualGoldWeight.trim().toUpperCase().equals(expectedGoldWeight.trim().toUpperCase()))) {

					finalResult.put("goldweight", finalResult.get("goldweight") & false);
				}
			}

			String x = productItms.getsize();
			System.out.println(x);
			// finalResultForAll.add(finalResult);
			createHTML(a[0], a[1], a[2]);
			finalResult.put("Sku", true);
			finalResult.put("goldweight", true);
			finalResult.put("ringsize", true);
		}

		// verifyProductDetailAttribute(sku, name );

	}

	public void initilizeEtry() {
		finalResult = new HashMap<String, Boolean>();
		finalResult.put("Sku", true);
		finalResult.put("goldweight", true);
		finalResult.put("ringsize", true);

	}

	@BeforeClass
	public void createHtML() {
		htmlReport = "<!DOCTYPE html><html><head><p>Hi All,</p>"
				+ "<p>Please find Automation Result for Random Product RingBracelet: </p>"
				+ "<style>table {width:100%;}table, th, td {border: 1px solid black;border-collapse: collapse;}"
				+ "th, td { padding: 5px; width:100%; text-align: left;} "
				+ "table#t01 tr:nth-child(even) {background-color: #eee;}"
				+ "table#t01 tr:nth-child(odd) {background-color:#fff;}"
				+ "table#t01 th {background-color: black;color: white;}</style></head>" + "<body> " + ""
				+ "<table ><tr>" + "<th>Sku</th>" + "<th>goldweight</th>" + "<th>ringsize</th>" + "<th>Result</th>"
				+ "</tr>";
	}

	@AfterClass
	public void prepareAndSendHTML() {
		htmlReport = htmlReport + "</table></body></html>";
		SendEmail sendEmail = new SendEmail();
		sendEmail.sendMail(htmlReport, "Automation Test Report", to);
	}

	public void createHTML(String Sku, String goldweight, String ringsize) {

		boolean finalRes = true;
		String result1 = "PASS";

		if (!(finalResult.get("Sku"))) {
			Sku = "<font color=red>" + Sku + "</font>";
			finalRes = finalRes & false;
		}

		if (!(finalResult.get("goldweight"))) {
			goldweight = "<font color=red>" + goldweight + "</font>";
			finalRes = finalRes & false;
		}
		if (!(finalResult.get("ringsize"))) {
			ringsize = "<font color=red>" + ringsize + "</font>";
			finalRes = finalRes & false;
		}

		if (finalRes) {
			result1 = "<font color=green>" + result1 + "</font>";
		}

		else {
			result1 = "<font color=red>" + "FAIL" + "</font>";

		}
		htmlReport = htmlReport + "<tr>" + "<td>" + Sku + "</td>" + "<td>" + goldweight + "</td>" + "<td>"
				+ ringsize + "</td><td>"+ result1 + "</td>" + "</tr>";

	}

	public String[][] getRandomKey(Integer count, String[][] s1) {

		int rows = s1.length;
		String[][] s2 = new String[count][1];
		if (count < rows) {
			Random ran = new Random();
			Set<Integer> generated = new LinkedHashSet<Integer>();
			while (generated.size() < count) {
				Integer next = ran.nextInt(rows) + 1;
				generated.add(next);
			}

			int i = 0;
			for (Integer j : generated) {
				s2[i][0] = s1[j][0];
				i++;
			}
		} else {
			System.out.println("Please enter less number");
		}
		return s2;// it return the 17 coloum datas for the random searchable
		// products and print it
	}

}