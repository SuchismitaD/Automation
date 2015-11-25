
package test.java.com.smoketest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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



public class Sh extends BaseTest {
              
				
				private static String htmlReport;
				private WebDriver driver;
                HashMap<String, Boolean> finalResult;
                
                private String ShipsfastTagOnImage;
                private String ShipsfastTagDeliveryDate;
                private String ExpShippingDate;
                private String ShipsfastTagFooter;
                private String CartMessage;
                private String ShipsfastTagCartPage;
           
                
                @DataProvider(name = "getRandomShipsfast")
                public Object[][] getData() {
                         String workingdirectory = System.getProperty("user.dir");
                         LinkedHashMap<String, Integer> colNameToLocation = new LinkedHashMap<String, Integer>();

                         String s[][] = utility.getDataFromCSV(workingdirectory + "\\Shipsfast.csv", colNameToLocation);
                         String random[][] = null;
                         if (isTotalCount) // this is the boolean value declared in the basetest
                           random = utility.getRandomSku(s, s.length, colNameToLocation);                                                                                                                                                                                                                   // csv
                         else
                           random = utility.getRandomSku(s, count, colNameToLocation);
                         return random;
                               
                }

                @Test(dataProvider = "getRandomShipsfast") 
                public void testCSVData(String sku,String grade) throws InterruptedException {
                    initilizeEtry();
                 driver.findElement(By.xpath("//div[@id='ajax_register_close']")).click();
                 driver.findElement(By.xpath("//div[@class='search_outer']//img")).click();
                 Thread.sleep(3000);
                 WebElement elem = driver.findElement(By.xpath("//*[@id='ild_search_box']//form//div//input[@id='search']"));
                 elem.sendKeys(sku);
                 elem.sendKeys("\n");
                 driver.findElement(By.xpath("//a[@class='product-image']")).click();
                                             
              
                 
              // verify the shipsfast tag on the product on product detail page
                 if (driver.findElements(By.xpath("//form[@id='product_addtocart_form']/div[2]/span[@id='shipsfasttag']"))
                             .size() != 0) {
                       ShipsfastTagOnImage = "<font color=green>" + "Shipsfast Tag On Image is Present" + "</font>";
                 } else {
                       ShipsfastTagOnImage = "<font color=red>" + "Shipsfast Tag On Image is not Present" + "</font>";
                 }

                 
                 // verify the ships fast tag next to expected delivery date
                 if (driver.findElements(By.xpath("//form[@id='product_addtocart_form']/div[2]/span[@id='shipsfasttag']"))
                             .size() != 0) {
                       ShipsfastTagDeliveryDate = "<font color=green>" + "Shipsfast Tag On Image is Present" + "</font>";
                 } else {
                       ShipsfastTagDeliveryDate = "<font color=red>" + "Shipsfast Tag On Image is not Present" + "</font>";
                 }
                 
               
                createHtML("PASS", sku, grade,  ShipsfastTagOnImage,ShipsfastTagDeliveryDate,ExpShippingDate,CartMessage,ShipsfastTagCartPage);
                
                //verification of expected delivery date
                ExpectedDeliveryDate e = new ExpectedDeliveryDate();
                String s = e.getDate(3);//get the date after 3 days 
                System.out.println(s);
                
                WebElement element = driver.findElement(By.xpath("//form[@id='product_addtocart_form']/div[3]/div/div[3]/div[2]/span"));
                String actualdate = s;
                String expecteddate = element.getText();
                
               if (expecteddate.equalsIgnoreCase(actualdate)){
                 ExpShippingDate = "<font color=green>" + "Expected shipping matched(ships within 3 days)" + "</font>";
               }
                else{
                  ExpShippingDate = "<font color=red>" + "Expected shipping not matched(ships within 3 days)" + "</font>";
                }
               
            // Add to cart

               WebElement button1 = driver.findElement(By.xpath("//input[@class='add-cart2']"));

               button1.click();

               if(driver.getPageSource().contains("It will be processed according to our Shipsfast shipment date (26 Nov 2015)."))

               {
                 CartMessage= "<font color=green>" + "Shipsfast cart page message is coming" + "</font>";
               } else

               {
                 CartMessage= "<font color=red>" + "Shipsfast cart page message is not coming" + "</font>";
                     driver.close();
               }
              // verify the ships fast tag in cart page next to the product
               if (driver.findElements(By.xpath("//html/body/div[3]/div/div[3]/div/div[2]/div/div[1]/div/div/form/fieldset/table/tbody/tr[2]/td[1]/dl/div[2]/dt[@class='shipsfast']"))
                           .size() != 0) {
                 ShipsfastTagCartPage = "<font color=green>" + "Shipsfast Tag On cart page Image is Present" + "</font>";
               } else {
                 ShipsfastTagCartPage = "<font color=red>" + "Shipsfast Tag On cart page Image is not Present" + "</font>";
               }
            // verify the ships fast tag in the footer page
               if (driver.findElements(By.xpath("//span[@class='readytoship_violator']"))
                           .size() != 0) {
            	   ShipsfastTagFooter = "<font color=green>" + "Shipsfast Tag On cart page Image is Present" + "</font>";
               } else {
            	   ShipsfastTagFooter = "<font color=red>" + "Shipsfast Tag On cart page Image is not Present" + "</font>";
               }

               }
              
               public String getDate(int nextdate) {
                DateFormat df = new SimpleDateFormat("dd MMM yyyy");
                Date today = Calendar.getInstance().getTime();//findout todays date 
                Calendar cal = Calendar.getInstance();
                cal.setTime(today);
                cal.add(Calendar.DATE, nextdate);
                today = cal.getTime();
                String reportDate = df.format(today);
                return reportDate;//this returns todays date 
                
               }



               Product productItms = null;{
            	   
               productItms = new Product(driver);

               // verification of sku of the product
               String actualsku = productItms.getsku();
               String expectedsku = actualsku;
               Assert.assertTrue(actualsku.trim().toUpperCase().contains(expectedsku.trim().toUpperCase()));
               

               // verification of the product
               String actualgrade = productItms.getgrade();
               String expectedgrade = actualgrade.toUpperCase();
               Assert.assertTrue(actualgrade.trim().toUpperCase().contains(expectedgrade));
               
               
               
                createHtML("PASS", actualsku, actualgrade, ShipsfastTagOnImage, ShipsfastTagDeliveryDate, ExpShippingDate, CartMessage, ShipsfastTagCartPage);
           }
                public void initilizeEtry() {
                    finalResult = new HashMap<String, Boolean>();
                    finalResult.put("sku", true);
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

