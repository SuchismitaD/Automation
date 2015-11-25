package test.java.com.smoketest;

import java.util.HashMap;

import java.util.LinkedHashMap;

import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import org.testng.Assert;

import org.testng.annotations.AfterClass;

import org.testng.annotations.BeforeClass;

import org.testng.annotations.DataProvider;

import org.testng.annotations.Test;

import main.java.com.ilovediamonds.basetest.BaseTest;
import main.java.com.ilovediamonds.entity.Product;
import main.java.com.ilovediamonds.utilities.SendEmail;

public class CSVAddtoCart extends BaseTest {

	private static String htmlReport;
	private WebDriver driver;

	Map<String, Boolean> finalResult;

	/**
	 * 
	 * It calls the random data values from the csv which are listed in the
	 * 
	 * testCSVData
	 *
	 * 
	 * 
	 * @return the number as per the user input for how much products we wants
	 * 
	 *         to run
	 * 
	 */

	@DataProvider(name = "getRandomData")

	public Object[][] getData() {

		String workingdirectory = System.getProperty("user.dir");

		LinkedHashMap<String, Integer> colNameToLocation = new LinkedHashMap<String, Integer>();

		String s[][] = utility.getDataFromCSV(workingdirectory + "\\products.csv", colNameToLocation);// from
																										// utility
																										// file
																										// it
																										// get
																										// the
																										// total
																										// no
																										// of
																										// data
																										// through
																										// the
																										// object
																										// and
																										// store
																										// it
																										// in
																										// s

		String random[][] = null;

		if (isTotalCount) // this is the boolean value declared in the basetest

			random = utility.getRandomNoFromCSV(s, s.length, colNameToLocation);

		else

			random = utility.getRandomNoFromCSV(s, count, colNameToLocation);

		return random;// here it stores the no of input from the testsuite file
						// i.e the user input

	}

	/**
	 * 
	 * This function is used to test the mapping values from product detail page
	 * with csv file
	 *
	 * 
	 * 
	 * @param Sting
	 * 
	 *            type parameters for those columns which is displayed in the
	 * 
	 *            product page.and as per the mapping it will check the actual
	 * 
	 *            value with expected value
	 *
	 * 
	 * 
	 * @throws InterruptedException
	 * 
	 */

	@Test(dataProvider = "getRandomData")

	public void testCSVData(String Sku, String Product, String DiaAmountSIGH, String DiaAmountVVSEF,

			String totalGemAmount, String MetalType, String height, String width, String MetalWeight, String Catagory,

			String TypeOfProduct, String diaColor, String diaClarity, String DiamondShape, String NoOfDiamonds,

			String TotalDiamondWeight, String Collection, String GemstoneType, String GemstoneShape,

			String NoOfGemstones, String TotalGemWeight) throws InterruptedException {

		initilizeEtry();

		System.out.println(Sku);
		driver = threadDriver.get();

		driver.findElement(By.xpath("//div[@id='ajax_register_close']")).click();// popup
																					// close

		driver.findElement(By.xpath("//div[@class='search_outer']//img")).click();// for
																					// search
																					// option
																					// click

		Thread.sleep(3000);

		WebElement elem = driver.findElement(By.xpath("//*[@id='ild_search_box']//form//div//input[@id='search']"));// click
																													// image

		elem.sendKeys(Sku);

		elem.sendKeys("\n");

		driver.findElement(By.xpath("//a[@class='product-image']")).click();

		// verify if the buynow button enable or not

		WebElement e = driver.findElement(By.xpath("//input[@class='add-cart2']"));

		Assert.assertTrue(e.isEnabled());// return true when the buynow button
											// enable

		if (driver.findElements(By.xpath("//a[@class='product-image']")).size() != 0) {

			System.out.println("Buynow button enable");

		} else {

			System.out.println("Buynow button disable");

		}
		Thread.sleep(4000);
		driver.findElement(By.xpath("//div/a[@id='viewbreakup']")).click();

		Thread.sleep(2000);

		Product productItms = null;

		productItms = new Product(driver);

		// for diamond quality radio button selection - SI-GH

		if (diaClarity.contains("SI") && diaColor.contains("GH")) {

			driver.findElement(
					By.xpath("//div[@class='input-box']/label[@class='label-radio-configurable'][" + 1 + "]/input"))
					.click();

			// breakup selection view click

			// verification of sku of the product for SI-GH

			String actualsku = productItms.getsku();

			if (!(actualsku.trim().toUpperCase().contains(Sku.trim().toUpperCase()))) {

				finalResult.put("Sku", finalResult.get("Sku") & false);

			}

			// Verification of Diamond price SIGH

			String actualDiamondPriceSIGH = utility.converPrice(productItms.getDiamondPriceSIGH());

			if (!(actualDiamondPriceSIGH.trim().toUpperCase().equals(DiaAmountSIGH.trim().toUpperCase()))) {

				finalResult.put("DiaAmountSIGH", finalResult.get("DiaAmountSIGH") & false);

			}

			/**
			 * 
			 * common mapping function for both SIGH & VVSEF
			 * 
			 */

			verifyProductDetailAttribute(Sku, totalGemAmount, MetalType, height, width, MetalWeight, Catagory, TypeOfProduct,

					DiamondShape, NoOfDiamonds, Collection, GemstoneType, GemstoneShape, NoOfGemstones, TotalGemWeight,

					TotalDiamondWeight, productItms);

		}

		// VVSEF - Radio button click

		driver.findElement(
				By.xpath("//div[@class='input-box']/label[@class='label-radio-configurable'][" + 2 + "]/input"))
				.click();

		Thread.sleep(5000);

		productItms = new Product(driver);

		// verification of sku of the product for VVSEF

		String actualsku = productItms.getsku();

		if (!(actualsku.trim().toUpperCase().contains(Sku.trim().toUpperCase()))) {

			finalResult.put("Sku", finalResult.get("Sku") & false);

		}

		// Verification of Diamond price VVSEF

		String x = productItms.getDiamondPriceVVSEF();

		String actualDiamondPriceSIGH = utility.converPrice(x);

		if (!(actualDiamondPriceSIGH.trim().toUpperCase().equals(DiaAmountVVSEF.trim().toUpperCase()))) {

			finalResult.put("DiaAmountVVSEF", finalResult.get("DiaAmountVVSEF") & false);

		}

		/**
		 * 
		 * common mapping function for both SIGH & VVSEF
		 * 
		 */

		verifyProductDetailAttribute(Sku, totalGemAmount, MetalType, height, width, MetalWeight, Catagory, TypeOfProduct,

				DiamondShape, NoOfDiamonds, Collection, GemstoneType, GemstoneShape, NoOfGemstones, TotalGemWeight,

				TotalDiamondWeight, productItms);

		// Add to cart

		WebElement button1 = driver.findElement(By.xpath("//input[@class='add-cart2']"));

		button1.click();

		// verify the cart message in cart page

		if (driver.getPageSource().contains("was added to your shopping cart.")) {

			System.out.println("cart page message is coming for the added product");

		} else {

			System.out.println("cart page message is not coming");

			driver.close();

		}

		// for edit

		driver.findElement(By.xpath("//a[@title='Edit item parameters']")).click();

		driver.findElement(By.xpath("//button[@title='Update Cart']")).click();

		// for increase and decrease quantity

		driver.findElement(By.xpath("//button[@title='Increase Quantity']")).click();

		driver.findElement(By.xpath("//button[@title='Decrease Quantity']")).click();

		driver.findElement(By.xpath("//span[contains(text(),'Proceed to Checkout')]")).click();
        if((Sku.contains("RG")||Sku.contains("BR")))
        {
           MetalWeight="N/A";
           
        }


		createHtML(Sku, Product, DiaAmountSIGH, DiaAmountVVSEF, totalGemAmount, MetalType, height, width,

				MetalWeight, Catagory, TypeOfProduct, diaColor, diaClarity, DiamondShape, NoOfDiamonds, Collection,

				GemstoneType, GemstoneShape, NoOfGemstones, TotalGemWeight, TotalDiamondWeight);

	}

	public void initilizeEtry() {

		finalResult = new HashMap<String, Boolean>();

		finalResult.put("Sku", true);

		finalResult.put("Product", true);

		finalResult.put("DiaAmountSIGH", true);

		finalResult.put("DiaAmountVVSEF", true);

		finalResult.put("totalGemAmount", true);

		finalResult.put("MetalType", true);

		finalResult.put("height", true);

		finalResult.put("width", true);

		finalResult.put("MetalWeight", true);

		finalResult.put("Catagory", true);

		finalResult.put("TypeOfProduct", true);

		finalResult.put("diaColor", true);

		finalResult.put("diaClarity", true);

		finalResult.put("DiamondShape", true);

		finalResult.put("NoOfDiamonds", true);

		finalResult.put("TotalDiamondWeight", true);

		finalResult.put("Collection", true);

		finalResult.put("GemstoneType", true);

		finalResult.put("GemstoneShape", true);

		finalResult.put("NoOfGemstones", true);

		finalResult.put("TotalGemWeight", true);

	}

	public void verifyProductDetailAttribute(String Sku, String totalGemAmount, String MetalType, String height, String width,

			String MetalWeight, String Catagory, String TypeOfProduct, String DiamondShape, String NoOfDiamonds,

			String Collection, String GemstoneType, String GemstoneShape, String NoOfGemstones, String TotalGemWeight,

			String TotalDiamondWeight, Product productItms) {

		// Verification of Total Gemstone Amount

		if (productItms.getTGemAmount() != null && !productItms.getTGemAmount().trim().equals("N/A")) {

			String actualTGemAmount = utility.converPrice(productItms.getTGemAmount());

			if (!(actualTGemAmount.trim().toUpperCase().equals(totalGemAmount.trim().toUpperCase()))) {

				finalResult.put("totalGemAmount", finalResult.get("totalGemAmount") & false);

			}

		}

		else

		{

			finalResult.put(totalGemAmount, finalResult.get("totalGemAmount") & false);

			totalGemAmount = "N/A";

		}

		// verification of Metal type

		if (productItms.getmetalPurity() != null && !productItms.getmetalPurity().trim().equals("N/A")) {

			String actualmetalPurity = productItms.getmetalPurity();

			if (!(actualmetalPurity.trim().toUpperCase().equals(MetalType.trim().toUpperCase()))) {

				finalResult.put("MetalType", finalResult.get("MetalType") & false);

			}

		}

		else

		{

			finalResult.put(MetalType, finalResult.get("MetalType") & false);

			MetalType = "N/A";

		}

		// Verification of height of the product page

		if (productItms.getHeight() != null && !productItms.getHeight().trim().equals("N/A")) {

			String actualHeight = utility.convertDecimal(productItms.getHeight());

			String expectedHeight = utility.convertDecimal(height);

			if (!(actualHeight.trim().toUpperCase().equals(expectedHeight.trim().toUpperCase()))) {

				finalResult.put("height", finalResult.get("height") & false);

			}

		}

		else if (height == "0")

		{

			height = "N/A";

			finalResult.put(height, finalResult.get("height") & false);

		}

		// verification of width of the product page

		if (productItms.getWidth() != null && !productItms.getWidth().trim().equals("N/A")) {

			String actualWidth = utility.convertDecimal(productItms.getWidth());

			String expectedWidth = utility.convertDecimal(width);

			if (!(actualWidth.trim().toUpperCase().equals(expectedWidth.trim().toUpperCase()))) {

				finalResult.put("width", finalResult.get("width") & false);

			}

		}

		else if (width == "0")

		{

			finalResult.put("width", finalResult.get("width") & false);

			width = "N/A";

		}

		// verification of gold weight

        if((Sku.contains("RG")||Sku.contains("BR")))
        {
           MetalWeight="N/A";
           
        }
     else {
     
     if(productItms.getGoldWeight() != null && !productItms.getGoldWeight().trim().equals("N/A")){


			String actualGoldWeight = utility.convertDecimal(productItms.getGoldWeight().split(" ")[0].toString());

			String expectedGoldWeight = utility.convertDecimal(MetalWeight);

			if (!(actualGoldWeight.trim().toUpperCase().equals(expectedGoldWeight.trim().toUpperCase()))) {

				finalResult.put("MetalWeight", finalResult.get("MetalWeight") & false);

			}

		}

		else

		{

			finalResult.put("MetalWeight", finalResult.get("MetalWeight") & false);

			MetalWeight = "N/A";

		}

		// verification of Design Type

		if (productItms.getDesignType() != null && !productItms.getDesignType().trim().equals("N/A")) {

			String actualDesignType = productItms.getDesignType();

			if (!(actualDesignType.trim().toUpperCase().equals(Catagory.trim().toUpperCase()))) {

				finalResult.put("Catagory", finalResult.get("Catagory") & false);

			}

		}

		else

		{

			finalResult.put("Catagory", finalResult.get("Catagory") & false);

			Catagory = "N/A";

		}

		// Verification of style of the product in Product Page. String

		if (productItms.getstyle() != null && !productItms.getstyle().trim().equals("N/A")) {

			if (!(utility.matchTwoString(productItms.getstyle(), TypeOfProduct)))

				finalResult.put("TypeOfProduct", finalResult.get("TypeOfProduct") & false);

		}

		else

		{

			finalResult.put("TypeOfProduct", finalResult.get("TypeOfProduct") & false);

			TypeOfProduct = "N/A";

		}

		// verification of Diamond shape

		if (productItms.getDshape() != null && !productItms.getDshape().trim().equals("N/A")) {

			if (!(utility.matchTwoString(productItms.getDshape(), DiamondShape)))

				finalResult.put("DiamondShape", finalResult.get("DiamondShape") & false);

		}

		else

		{

			finalResult.put("DiamondShape", finalResult.get("TypeOfProduct") & false);

			DiamondShape = "N/A";

		}

		// verification of Total number of Diamonds

		String actualTotalnoDiamonds = productItms.getTotalnoDiamonds();

		if (!(actualTotalnoDiamonds.trim().toUpperCase().equals(NoOfDiamonds.trim().toUpperCase()))) {

			finalResult.put("NoOfDiamonds", finalResult.get("NoOfDiamonds") & false);

		}

		// verification of Diamond total Weight

		String actualDtotalWeight = utility.convertDecimal(productItms.getDtotalWeight().split("ct")[0].toString());

		String expetedDtotalWeight1 = utility.convertDecimal(TotalDiamondWeight.trim());

		if (!(actualDtotalWeight.trim().toUpperCase().equals(expetedDtotalWeight1.trim().toUpperCase()))) {

			finalResult.put("TotalDiamondWeight", finalResult.get("TotalDiamondWeight") & false);

		}

		// verification of Collections

		if (!(utility.matchTwoString(productItms.getCollections(), Collection)))

			finalResult.put("Collection", finalResult.get("Collection") & false);

		// verification of Gemstone Type

		if (GemstoneType.length() > 0)

			if (!(utility.matchTwoString(productItms.getGemType(), GemstoneType)))

				finalResult.put("GemstoneType", finalResult.get("GemstoneType") & false);

		// verification of Gemstone shape

		if (GemstoneShape.length() > 0)

			if (!(utility.matchTwoString(productItms.getGemShape(), GemstoneShape)))

				finalResult.put("GemstoneShape", finalResult.get("GemstoneShape") & false);

		// verification of Number of Gemstones

		if (!(NoOfGemstones.equals("0"))) {

			String actualTotalNoGem = productItms.getTotalNoGem();

			if (!(actualTotalNoGem.trim().toUpperCase().equals(NoOfGemstones.trim().toUpperCase()))) {

				finalResult.put("NoOfGemstones", finalResult.get("NoOfGemstones") & false);

			}

		}

		// verification of Total Gemstone weight

		if (!(TotalGemWeight.equals("0"))) {

			String actualTotalGemWeight = utility

					.convertDecimal(productItms.getTotalGemWeight().split("ct")[0].toString());

			String expectedTotalGemWeight = utility.convertDecimal(TotalGemWeight);

			if (!(actualTotalGemWeight.trim().toUpperCase().equals(expectedTotalGemWeight.trim().toUpperCase()))) {

				finalResult.put("TotalGemWeight", finalResult.get("TotalGemWeight") & false);

			}
		}
     }

		

	}

	@BeforeClass

	public void createHTML() {

		htmlReport = "<!DOCTYPE html><html><head><p>Hi All,</p>"

				+ "<p>Please find Automation Result for Random Product Add to cart : </p>"

				+ "<style>table {width:100%;}table, th, td {border: 1px solid black;border-collapse: collapse;}"

				+ "th, td { padding: 5px; width:100%; text-align: left;} "
				+ "table#t01 tr:nth-child(even) {background-color: #eee;}"

				+ "table#t01 tr:nth-child(odd) {background-color:#fff;}"

				+ "table#t01 th {background-color: black;color: white;}</style></head>" + "<body> " + ""

				+ "<table ><tr>" + "<th>Result</th>" + "<th>Sku</th>" + "<th>Product</th>" + "<th>DiaAmountSIGH</th>"

				+ "<th>DiaAmount VVSEF</th>" + "<th>Total GemAmount </th>" + "<th>Metal Type</th>" + "<th>Height</th>"

				+ "<th>Width</th>" + "<th>MetalWeight</th>" + "<th>Catagory</th>" + "<th>TypeOfProduct</th>"

				+ "<th>DiaColor</th><th>DiaClarity</th>" + "<th>DiamondShape</th>" + "<th>NoOfDiamonds</th>"

				+ "<th>Collection</th>" + "<th>GemstoneType</th>" + "<th>GemstoneShape</th>" + "<th>NoOfGemstones</th>"

				+ "<th>TotalGemWeight</th>" + "<th>TotalDiamondWeight</th>" + "<th>AddToCart</th>" + "</tr>";

	}

	@AfterClass

	public void prepareAndSendHTML() {

		htmlReport = htmlReport + "</body></html>";

		SendEmail sendEmail = new SendEmail();

		sendEmail.sendMail(htmlReport, "Automation Test Report", to);

	}

	public void createHtML(String Sku, String Product, String DiaAmountSIGH, String DiaAmountVVSEF,

			String totalGemAmount, String MetalType, String height, String width, String MetalWeight, String Catagory,

			String TypeOfProduct, String diaColor, String diaClarity, String DiamondShape, String NoOfDiamonds,

			String Collection, String GemstoneType, String GemstoneShape, String NoOfGemstones, String TotalGemWeight,

			String TotalDiamondWeight) {

		boolean finalRes = true;

		String result = "PASS";

		String Addtocart;

		if (!(finalResult.get("Sku"))) {

			Sku = "<font color=red>" + Sku + "</font>";

			finalRes = finalRes & false;

		}

		if (!(finalResult.get("DiaAmountSIGH"))) {

			DiaAmountSIGH = "<font color=red>" + DiaAmountSIGH + "</font>";

			finalRes = finalRes & false;

		}

		if (!(finalResult.get("DiaAmountVVSEF"))) {

			DiaAmountVVSEF = "<font color=red>" + DiaAmountVVSEF + "</font>";

			finalRes = finalRes & false;

		}

		if (!(finalResult.get("totalGemAmount"))) {

			totalGemAmount = "<font color=red>" + totalGemAmount + "</font>";

			finalRes = finalRes & false;

		}

		if (!(finalResult.get("MetalType"))) {

			MetalType = "<font color=red>" + MetalType + "</font>";

			finalRes = finalRes & false;

		}

		if (!(finalResult.get("height"))) {

			height = "<font color=red>" + height + "</font>";

			finalRes = finalRes & false;

		}

		if (!(finalResult.get("width"))) {

			width = "<font color=red>" + width + "</font>";

			finalRes = finalRes & false;

		}

		if (!(finalResult.get("MetalWeight"))) {

			MetalWeight = "<font color=red>" + MetalWeight + "</font>";

			finalRes = finalRes & false;

		}

		if (!(finalResult.get("Catagory"))) {

			Catagory = "<font color=red>" + Catagory + "</font>";

			finalRes = finalRes & false;

		}

		if (!(finalResult.get("TypeOfProduct"))) {

			TypeOfProduct = "<font color=red>" + TypeOfProduct + "</font>";

			finalRes = finalRes & false;

		}

		if (!(finalResult.get("diaColor"))) {

			diaColor = "<font color=red>" + diaColor + "</font>";

			finalRes = finalRes & false;

		}

		if (!(finalResult.get("diaClarity"))) {

			diaClarity = "<font color=red>" + diaClarity + "</font>";

			finalRes = finalRes & false;

		}

		if (!(finalResult.get("DiamondShape"))) {

			DiamondShape = "<font color=red>" + DiamondShape + "</font>";

			finalRes = finalRes & false;

		}

		if (!(finalResult.get("NoOfDiamonds"))) {

			NoOfDiamonds = "<font color=red>" + NoOfDiamonds + "</font>";

			finalRes = finalRes & false;

		}

		if (!(finalResult.get("TotalDiamondWeight"))) {

			TotalDiamondWeight = "<font color=red>" + TotalDiamondWeight + "</font>";

			finalRes = finalRes & false;

		}

		if (!(finalResult.get("Collection"))) {

			Collection = "<font color=red>" + Collection + "</font>";

			finalRes = finalRes & false;

		}

		if (!(finalResult.get("GemstoneType"))) {
			if (GemstoneType.contains("CREAM PEARL"))
				GemstoneType.replace("CREAM PEARL", "PEARL");

			GemstoneType = "<font color=red>" + GemstoneType + "</font>";

			finalRes = finalRes & false;

		}

		if (!(finalResult.get("GemstoneShape"))) {

			GemstoneShape = "<font color=red>" + GemstoneShape + "</font>";

			finalRes = finalRes & false;

		}

		if (!(finalResult.get("NoOfGemstones"))) {

			NoOfGemstones = "<font color=red>" + NoOfGemstones + "</font>";

			finalRes = finalRes & false;

		}

		if (!(finalResult.get("TotalGemWeight"))) {

			TotalGemWeight = "<font color=red>" + TotalGemWeight + "</font>";

			finalRes = finalRes & false;

		}

		if (finalRes) {

			result = "<font color=green>" + result + "</font>";

			Addtocart = "<font color=green>" + "Added successfully into cart" + "</font>";
		}

		else {

			result = "<font color=red>" + "FAIL" + "</font>";

			Addtocart = "<font color=red>" + "Not Added successfully" + "</font>";

		}

		htmlReport = htmlReport + "<tr>" + "<td>" + result + "</td>" + "<td>" + Sku + "</td>" + "<td>" + Product

				+ "</td>" + "<td>" + DiaAmountSIGH + "</td>" + "<td>" + DiaAmountVVSEF + "</td>" + "<td>"

				+ totalGemAmount + "</td>" + "<td>" + MetalType + "</td>" + "<td>" + height + "</td>" + "<td>" + width

				+ "</td>" + "<td>" + MetalWeight + "</td>" + "<td>" + Catagory + "</td>" + "<td>" + TypeOfProduct

				+ "</td>" + "<td>" + diaColor + "</td>" + "<td>" + diaClarity + "</td>" + "<td>" + DiamondShape

				+ "</td>" + "<td>" + NoOfDiamonds + "</td>" + "<td>" + Collection + "</td>" + "<td>" + GemstoneType

				+ "</td>" + "<td>" + GemstoneShape + "</td>" + "<td>" + NoOfGemstones + "</td>" + "<td>"

				+ TotalGemWeight + "</td>" + "<td>" + TotalDiamondWeight + "</td>" +

		"<td>" + Addtocart + "</td>" + "</tr>";

		finalResult = null;

	}

}
