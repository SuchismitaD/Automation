package test.java.com.smoke_test;


import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.testng.Assert;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import main.java.com.ilovediamonds.basetest.BaseTest;
import main.java.com.ilovediamonds.entity.Product;
import main.java.com.ilovediamonds.utilities.SendEmail;



public class test1 extends BaseTest {
      
	private static String htmlReport;
      Map<String, Boolean> finalResult;
               

      /**
      * It calls the random data values from the csv which are listed in the
      * testCSVData
      * 
       * @return the number as per the user input for how much products we wants
      *         to run
      */
      @DataProvider(name = "getRandomData1")
      public Object[][] getData() {
            String workingdirectory = System.getProperty("user.dir");
            LinkedHashMap<String, Integer> colNameToLocation = new LinkedHashMap<String, Integer>();
            String s[][] = utility.getDataFromCSV(workingdirectory + "\\products3.csv", colNameToLocation);// from utility file it get the total no of data through the object and store it in s
            String random[][] = null;
            if (isTotalCount) // this is the boolean value declared in the base test
                  random = utility1.getRandomNoFromCSV(s, s.length, colNameToLocation);
            else
                  random = utility1.getRandomNoFromCSV(s, count, colNameToLocation);
            
            return random;// here it stores the no of input from the test suite file i.e the user input

      }

   
      @Test(dataProvider = "getRandomData1")
              
      public void testCSVData(String Sku, String goldweight) throws InterruptedException {
            
            initilizeEtry();

            driver.findElement(By.xpath("//div[@id='ajax_register_close']")).click();
            driver.findElement(By.xpath("//div[@class='search_outer']//img")).click();
            Thread.sleep(3000);
            WebElement elem = driver.findElement(By.xpath("//*[@id='ild_search_box']//form//div//input[@id='search']"));
            elem.sendKeys(Sku);
            elem.sendKeys("\n");
            driver.findElement(By.xpath("//a[@class='product-image']")).click();
            
            
          // verify if the buy now button enable or not
    		WebElement e = driver.findElement(By.xpath("//input[@class='add-cart2']"));
    		Assert.assertTrue(e.isEnabled());//return true when the buy now button enable
    		if (driver.findElements(By.xpath("//a[@class='product-image']")).size() != 0) {
    			System.out.println("Buynow button enable");
    		} else {
    			System.out.println("Buynow button disable");
    		}

    		/**
    		 * First it will execute all the verification according to the mapping
    		 * for diamond quality SIGH
    		 */
    		Product productItms = null;
    		productItms = new Product(driver);
    		
    		
    		// breakup selection view click
    		driver.findElement(By.xpath("//div/a[@id='viewbreakup']")).click();
    		Thread.sleep(2000);
    		
    		/** verification of all sku ids with all different sizes of each random products with diamond quality SI-GH
    		 * 	
    		 */
     
    		// for diamond quality radio button selection - SI-GH
    		driver.findElement(By.xpath("//div[@class='input-box']/label[@class='label-radio-configurable'][" + 1 + "]/input")).click();
    		
    		//verification of different sku ids for different sizes of each product
    		List<WebElement> e1 = new ArrayList<WebElement>(driver.findElements(By.xpath("//select/option")));
            for (WebElement j : e1) 
            {
            	j.click();
                Thread.sleep(5000);
                // verification of sku of the product for SI-GH
        		String actualsku = productItms.getsku();
        		if (!(actualsku.trim().toUpperCase().contains(Sku.trim().toUpperCase())))
        		  {
        		   finalResult.put("Sku", finalResult.get("Sku") & false);
        		  }
        		j.click();
                Thread.sleep(5000);
        		String actualGoldWeight = productItms.getGoldWeight();
       		    String expectedGoldWeight = utility1.convertDecimal(goldweight);
       		    if (!(actualGoldWeight.trim().toUpperCase().equals(expectedGoldWeight.trim().toUpperCase())))
       		      {
            	   finalResult.put("goldweight", finalResult.get("goldweight") & false);
                  }
        		
        		 Sku=actualsku;
        		 createHTML(Sku,goldweight);
            }	
          
            /** verification of all sku ids with all different sizes of each random products with diamond quality VVS-EF
    		 * 	
    		 */
     
    		// for diamond quality radio button selection - VVS-EF
    		driver.findElement(By.xpath("//div[@class='input-box']/label[@class='label-radio-configurable'][" + 2 + "]/input")).click();
    		
    		for (WebElement j : e1) 
            { 
           
            	j.click();
                Thread.sleep(5000);
                // verification of sku of the product for VVS-EF
        		String actualsku = productItms.getsku();
        		if (!(actualsku.trim().toUpperCase().contains(Sku.trim().toUpperCase())))
        		 {
        		   finalResult.put("Sku", finalResult.get("Sku") & false);
        		 }
        		String actualGoldWeight = productItms.getGoldWeight();
       		    String expectedGoldWeight = utility1.convertDecimal(goldweight);
       		    if (!(actualGoldWeight.trim().toUpperCase().equals(expectedGoldWeight.trim().toUpperCase())))
       		      {
       	           finalResult.put("goldweight", finalResult.get("goldweight") & false);        	     
       		      }
        		
        		Sku=actualsku;
        		createHTML(Sku,goldweight);
            }	
          

      
     // Add to cart
   		WebElement button1 = driver.findElement(By.xpath("//input[@class='add-cart2']"));
   		button1.click();
   		finalResult = null;
   	
      }
            public void initilizeEtry()
            {
            finalResult = new HashMap<String, Boolean>();
            finalResult.put("Sku", true);
            finalResult.put("goldweight", true);
        
            }

      @BeforeClass
    public void createHtML() {
          htmlReport = "<!DOCTYPE html><html><head><p>Hi All,</p>"
                      + "<p>Please find Automation Result for Random Product Add to cart : </p>"
                      + "<style>table {width:100%;}table, th, td {border: 1px solid black;border-collapse: collapse;}"
                      + "th, td { padding: 5px; width:100%; text-align: left;} " + "table#t01 tr:nth-child(even) {background-color: #eee;}"
                      + "table#t01 tr:nth-child(odd) {background-color:#fff;}"
                      + "table#t01 th {background-color: black;color: white;}</style></head>" + "<body> " + ""
                      + "<table ><tr>" + "<th>Result</th>" + "<th>Sku</th>" + "<th>goldweight</th>" 
                     + "</tr>";
    }
     @AfterClass
      public void prepareAndSendHTML() {
            htmlReport = htmlReport + "</body></html>";
            SendEmail sendEmail = new SendEmail();
            sendEmail.sendMail(htmlReport, "Automation Test Report");
     }
      

    public void createHTML(String Sku, String goldweight) {

      boolean finalRes = true;
      String result = "PASS";
      

            if (!(finalResult.get("Sku"))) {
                  Sku = "<font color=red>" + Sku + "</font>";
                  finalRes = finalRes & false;
            }
            
            if (!(finalResult.get("goldweight"))) {
                goldweight = "<font color=red>" + goldweight + "</font>";
                  finalRes = finalRes & false;
            }
            if(finalRes){
    			result =  "<font color=green>" + result + "</font>";
            }
    			
    		else{
    			result =  "<font color=red>" + "FAIL" + "</font>";
    		
    		}
        htmlReport = htmlReport + "<tr>" + "<td>" + result + "</td>" + "<td>" + Sku + "</td>" + "<td>" + goldweight
                + "</td>"+ "</tr>";
                

    }
   }
		
   		
   		
   		