
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
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;

import main.java.com.ilovediamonds.basetest.BaseTest;
import main.java.com.ilovediamonds.entity.Product;
import main.java.com.ilovediamonds.utilities.SendEmail;



public class ShipsFast extends BaseTest {
                private static String htmlReport;
                HashMap<String, Boolean> finalResult;
				private String ShipsfastTagOnImage;
				private WebDriver driver;
				private String ShipsfastTagDeliveryDate;
				private String ExpShippingDate;
				private String CartMessage;
				private String ShipsfastTagCartPage;
                
              
                

                /**
                * It calls the random data values from the csv which are listed in the
                * testCSVData
                * 
                 * @return the number as per the user input for how much products we wants
                *         to run
                */
                @DataProvider(name = "getRandomData")
                public Object[][] getData() {
                         String workingdirectory = System.getProperty("user.dir");
                         LinkedHashMap<String, Integer> colNameToLocation = new LinkedHashMap<String, Integer>();

                         String s[][] = utility.getDataFromCSV(workingdirectory + "\\sh.csv", colNameToLocation);
                         String random[][] = null;
                         if (isTotalCount) // this is the boolean value declared in the basetest
                           random = utility.getRandomShips(s, s.length, colNameToLocation);                                                                                                                                                                                                                   // csv
                         else
                           random = utility.getRandomShips(s, count, colNameToLocation);
                         return random;
                               
                }

               
                @Test(dataProvider = "getRandomData")
                public void testCSVData(String sku, String name, String grade) throws InterruptedException {
                	initilizeEtry();
                                driver.findElement(By.xpath("//div[@id='ajax_register_close']")).click();
                    driver.findElement(By.xpath("//div[@class='search_outer']//img")).click();
                    Thread.sleep(3000);
                    WebElement elem = driver.findElement(By.xpath("//*[@id='ild_search_box']//form//div//input[@id='search']"));
                    elem.sendKeys(sku);
                    elem.sendKeys("\n");
                    driver.findElement(By.xpath("//a[@class='product-image']")).click();
    
                    

                    Product productItms = null;
                    productItms = new Product(driver);

                    // verification of sku of the product
                    String actualsku = productItms.getsku();
                    String expectedsku = sku;
                    Assert.assertTrue(actualsku.trim().toUpperCase().contains(expectedsku.trim().toUpperCase()));

                    // verification of the product
                    String actualgrade = productItms.getgrade();
                    String expectedgrade = grade.toUpperCase();
                    Assert.assertTrue(actualgrade.trim().toUpperCase().contains(expectedgrade));

                    //String ShipsfastTagCartPage = null;
					// verifyProductDetailAttribute(sku, name );
                     createHtML("PASS", sku, grade, ShipsfastTagOnImage, ShipsfastTagDeliveryDate, ExpShippingDate, CartMessage, ShipsfastTagCartPage);
                }
                     public void initilizeEtry() {
                         finalResult = new HashMap<String, Boolean>();
                         finalResult.put("Sku", true);
                         finalResult.put("grade", true);
                     }
                        
                
         @BeforeClass
                     
                     public void createHtML() {
                     htmlReport = "<!DOCTYPE html><html><head><p>Hi All,</p>"
                                 + "<p>Please find Automation Result for Random Product  Name CSV Compare: </p>"
                                 + "<style>table {width:100%;}table, th, td {border: 1px solid black;border-collapse: collapse;}"
                                 + "th, td { padding: 5px;text-align: left;} " + "table#t01 tr:nth-child(even) {background-color: #eee;}"
                                 + "table#t01 tr:nth-child(odd) {background-color:#fff;}"
                                 + "table#t01 th {background-color: black;color: white;}</style></head><body> " + "" + "<table ><tr>"
                                 + "<th> Result </th>" + "<th>Sku</th>" +  "<th>grade</th>" +"<th>ShipsfastTagOnImage</th>" 
                     + "<th>ShipsfastTagDeliveryDate</th>" + "<th>ExpShippingDate</th>"+ "<th>CartMessage</th>"
                     + "<th>ShipsfastTagCartPage</th>"+"</tr>";
                     }


                     @AfterClass
                     public void prepareAndSendHTML() {
                          htmlReport = htmlReport + "</body></html>";
                          SendEmail sendEmail = new SendEmail();
                          sendEmail.sendMail(htmlReport, "Automation Test Report",to);
                     }

                     public void createHtML(String result, String sku,String grade,
                    		 String ShipsfastTagOnImage, String ShipsfastTagDeliveryDate,String ExpShippingDate,String CartMessage,String ShipsfastTagCartPage) 
                     {
                        boolean finalRes = true;
                          String result1 = "PASS";
                     

                             if (!(finalResult.get("sku"))) {
                                   sku = "<font color=red>" + sku + "</font>";
                                   finalRes = finalRes & false;
                             }
                             
                             if(finalRes){
                                   result1 =  "<font color=green>" + result1 + "</font>";
                             }
                             else{
                                   result1 =  "<font color=red>" + "FAIL" + "</font>";
                             
                             }
                         
                      htmlReport = htmlReport + "<tr>" + "<td>" + result1 + "</td>" + "<td>" + sku + "</td>" +
                    		  "<td>" + grade + "</td>" + "<td>" + ShipsfastTagOnImage + " </td>" +"<td>" + ShipsfastTagDeliveryDate + "</td>"+
                       "<td>" + ExpShippingDate+ "</td>"+"<td>" + CartMessage+ "</td>"+ "<td>"+ ShipsfastTagCartPage + "</td>"+"</tr>";
                                     finalResult = null;
                     }
     }

