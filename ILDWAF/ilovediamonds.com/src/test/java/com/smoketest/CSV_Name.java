
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

public class CSV_Name extends BaseTest {
                private static String htmlReport;
                
               
                HashMap<String, Boolean> finalResult;
                
                
                private String ZoomIcon;
                private String Thumbnail1;
                private String Thumbnail2;
                private String Thumbnail3;
                private String Thumbnail4;
                private String Thumbnail5;
                private WebDriver driver;

                /**
                * It calls the random data values from the csv which are listed in the
                * testCSVData
                * 
                 * @return the number as per the user input for how much products we wants
                *         to run
                */
                @DataProvider(name = "getRandomDataImage")
                public Object[][] getData() {
                         String workingdirectory = System.getProperty("user.dir");
                         LinkedHashMap<String, Integer> colNameToLocation = new LinkedHashMap<String, Integer>();

                         String s[][] = utility.getDataFromCSV(workingdirectory + "\\ProductName.csv", colNameToLocation);
                         String random[][] = null;
                         if (isTotalCount) // this is the boolean value declared in the basetest
                           random = utility.getRandomSku(s, s.length, colNameToLocation);                                                                                                                                                                                                                   // csv
                         else
                           random = utility.getRandomSku(s, count, colNameToLocation);
                         return random;
                               
                }

                /**
                * This function is used to test the mapping values
                * @param ZoomIcon 
                 * @param name1 
                 * @param ZoomIcon 
                 * @param ZoomIcon 
                 * 
                 * @param str1
                * @param str2
                * 
                 * @param Sting
                *            type parameters for those columns which is displayed in the
                *            product page.and as per the mapping it will check the actual
                *            value with expected value
                * 
                 * @throws InterruptedException
                */
                @Test(dataProvider = "getRandomDataImage")
                public void testCSVData(String sku, String name) throws InterruptedException {
                	initilizeEtry();
                	driver = threadDriver.get();
                             //   driver.findElement(By.xpath("//div[@id='ajax_register_close']")).click();
                    driver.findElement(By.xpath("//div[@class='search_outer']//img")).click();
                    Thread.sleep(3000);
                    WebElement elem = driver.findElement(By.xpath("//*[@id='ild_search_box']//form//div//input[@id='search']"));
                    elem.sendKeys(sku);
                    elem.sendKeys("\n");
                    driver.findElement(By.xpath("//a[@class='product-image']")).click();
                                                
     //Zoom Functionality
                    if(driver.findElements(By.xpath("//a[@id='zoom-btn']")).size() != 0){
                    	   ZoomIcon ="<font color=green>" + "Zoom Icon is present" + "</font>";
                    }else{
                    	   ZoomIcon ="<font color=red>" + "Zoom Icon is  not present" + "</font>";
                         }
                    
    //Image Visibility
                    
    if(driver.findElements(By.xpath("//div[@id='wrap']/div")).size() != 0){
    	
        Thumbnail1="<font color=green>" + "Thumbnail 1 is Present" + "</font>";
     }else{
          
          Thumbnail1="<font color=red>" + "Thumbnail 1 is not Present" + "</font>";
                                        }
                                        
    if(driver.findElements(By.xpath("//form[@id='product_addtocart_form']/div[2]/div/ul/li[1]/a[1]/img")).size() != 0){
    	Thumbnail2="<font color=green>" + "Thumbnail 2 is Present" + "</font>";
    }else{
         
         Thumbnail2="<font color=red>" + "Thumbnail 2 is not Present" + "</font>";
                                       }
                                        
   if(driver.findElements(By.xpath("//form[@id='product_addtocart_form']/div[2]/div/ul/li[2]/a[1]/img")).size() != 0){
	   Thumbnail3="<font color=green>" + "Thumbnail 3 is Present" + "</font>";
   }else{
        
        Thumbnail3="<font color=red>" + "Thumbnail 3 is not Present" + "</font>";
                                      }
                                                           
   if(driver.findElements(By.xpath("//form[@id='product_addtocart_form']/div[2]/div/ul/li[3]/a[1]/img")).size() != 0){
	   Thumbnail4="<font color=green>" + "Thumbnail 4 is Present" + "</font>";
   }else{
        
        Thumbnail4="<font color=red>" + "Thumbnail 4 is not Present" + "</font>";
                                      }
                                                                 
                                                               
           if(driver.findElements(By.xpath("//form[@id='product_addtocart_form']/div[2]/div/ul/li[4]/a[1]/img")).size() != 0){
        	   Thumbnail5="<font color=green>" + "Thumbnail 5 is Present" + "</font>";
           }else{
                
                Thumbnail5="<font color=red>" + "Thumbnail 5 is not Present" + "</font>";
                                              }
                    
                    //End
                    Product productItms = null;
                    productItms = new Product(driver);

                    // verification of sku of the product
                    String actualsku = productItms.getsku();
                    String expectedsku = sku;
                    Assert.assertTrue(actualsku.trim().toUpperCase().contains(expectedsku.trim().toUpperCase()));

                    // verification of the product
                     String actualname = productItms.getname();
                     String expectedname = name.toUpperCase();
                     Assert.assertTrue(actualname.trim().toUpperCase().contains(expectedname));

                    // verifyProductDetailAttribute(sku, name );
                     createHtML("PASS", sku, name, ZoomIcon, Thumbnail1,Thumbnail2,Thumbnail3,Thumbnail4,Thumbnail5);
                }
                     public void initilizeEtry() {
                         finalResult = new HashMap<String, Boolean>();
                         finalResult.put("Sku", true);
                         finalResult.put("name", true);
                     }
                        
                
                @BeforeClass
                
                public void createHtML() {
                htmlReport = "<!DOCTYPE html><html><head><p>Hi All,</p>"
                            + "<p>Please find Automation Result for Random Product  Name CSV Compare: </p>"
                            + "<style>table {width:100%;}table, th, td {border: 1px solid black;border-collapse: collapse;}"
                            + "th, td { padding: 5px;text-align: left;} " + "table#t01 tr:nth-child(even) {background-color: #eee;}"
                            + "table#t01 tr:nth-child(odd) {background-color:#fff;}"
                            + "table#t01 th {background-color: black;color: white;}</style></head><body> " + "" + "<table ><tr>"
                            + "<th> Result </th>" + "<th>Sku</th>" + "<th>name</th>" +"<th>ZoomIcon</th>" 
                            +"<th>Thumbnail1</th>" +"<th>Thumbnail2</th>" +"<th>Thumbnail3</th>" +
                            "<th>Thumbnail4</th>" +"<th>Thumbnail5</th>" +"</tr>";
                
                }


                @AfterClass
                public void prepareAndSendHTML() {
                     htmlReport = htmlReport + "</body></html>";
                     SendEmail sendEmail = new SendEmail();
                     sendEmail.sendMail(htmlReport, "Automation Test Report", to);
                }

                public void createHtML(String result, String sku, String name, String ZoomIcon,String Thumbnail1,
                                                String Thumbnail2,String Thumbnail3,String Thumbnail4,String Thumbnail5) 
                {
                	 boolean finalRes = true;
                     String result1 = "PASS";
                     String ZoomICon;
                 htmlReport = htmlReport + "<tr>" + "<td>" + result1 + "</td>" + "<td>" + sku + "</td>" + "<td>" + name + "</td>"+
                  "<td>" + ZoomIcon + " </td>" +"<td>" + Thumbnail1 + "</td>"+
                                                                                  "<td>" + Thumbnail2 + "</td>"+
                                                                                  "<td>" + Thumbnail3 + "</td>"+
                                                                                  "<td>" + Thumbnail4 + "</td>"+
                           
                                                                                 "<td>" + Thumbnail5 + "</td>"+"</tr>";
                                finalResult = null;
                }
}



