package test.java.com.smoketest;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

import org.apache.bcel.generic.Select;
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

public class ShipsFast extends BaseTest {
      private static String htmlReport;
      HashMap<String, Boolean> finalResult;
      private WebDriver driver;
      private String ShipsfastTagOnImage;
      private String ShipsfastTagDeliveryDate;
      private String ExpShippingDate;
      private String CartMessage;
      private String ShipsfastTagCartPage;

      @DataProvider(name = "getRandomShips")
      public Object[][] getData() {
            String workingdirectory = System.getProperty("user.dir");
            LinkedHashMap<String, Integer> colNameToLocation = new LinkedHashMap<String, Integer>();

            String s[][] = utility.getDataFromCSV(workingdirectory + "\\Shipsfast.csv", colNameToLocation);
            String random[][] = null;
            if (isTotalCount) // this is the boolean value declared in the basetest
                  random = utility.getRandomShipsfast(s, s.length, colNameToLocation); // csv
            else
                  random = utility.getRandomShipsfast(s, count, colNameToLocation);
            return random;

      }

      @Test(dataProvider = "getRandomShips")
      public void testCSVData(String sku, String grade, String size) throws InterruptedException {
            driver = threadDriver.get();
            initilizeEtry();
            driver.findElement(By.xpath("//div[@id='ajax_register_close']")).click();
            driver.findElement(By.xpath("//div[@class='search_outer']//img")).click();
            Thread.sleep(3000);
            WebElement elem = driver.findElement(By.xpath("//*[@id='ild_search_box']//form//div//input[@id='search']"));
            elem.sendKeys(sku);
            elem.sendKeys("\n");
            driver.findElement(By.xpath("//a[@class='product-image']")).click();

            // verification of sku of the product
            String actualsku = driver.findElement(By.xpath("//span[@class='product-sku']")).getText();
            if (!(actualsku.trim().toUpperCase().contains(sku.trim().toUpperCase()))) {
                  finalResult.put("sku", finalResult.get("sku") & false);
            }
        Thread.sleep(2000);
     // verification of expected delivery date
            ExpectedDeliveryDate e = new ExpectedDeliveryDate();
            String s = e.getDate(3);// get the date after 3 days
            System.out.println(s);
            
            WebElement element = driver.findElement(By.xpath("//form[@id='product_addtocart_form']/div[3]/div/div[3]/div[2]/span"));
            String expecteddate = s;
            String actualdate = element.getText();
            
            if (expecteddate.equalsIgnoreCase(actualdate)) {
                  ExpShippingDate = "<font color=green>" + "Expected shipping matched(ships within 3 days)" + "</font>";
            } else {
                  ExpShippingDate = "<font color=red>" + "Expected shipping not matched(ships within 3 days)" + "</font>";
           }

            // verification of grade of the product
            if (grade.contains("SI-GH")) {
                  driver.findElement(
                              By.xpath("//div[@class='input-box']/label[@class='label-radio-configurable'][" + 1 + "]/input"))
                              .click();
                  
                  String actualgrade = driver.findElement(By.xpath("//span[@class='product-sku']")).getText();
                  if (!(actualgrade.trim().toUpperCase().contains(grade.trim().toUpperCase()))) {
                        finalResult.put("grade", finalResult.get("grade") & false);
                  }
            } else {
                  driver.findElement(
                              By.xpath("//div[@class='input-box']/label[@class='label-radio-configurable'][" + 2 + "]/input"))
                              .click();
                  
                  String actualgrade = driver.findElement(By.xpath("//span[@class='product-sku']")).getText();
                  if (!(actualgrade.trim().toUpperCase().contains(grade.trim().toUpperCase()))) {
                        finalResult.put("grade", finalResult.get("grade") & false);
                  }
            }
            
            try {
                  if (driver.findElement(By.xpath("//select/option")).isDisplayed()) {

                        List<WebElement> e1 = new ArrayList<WebElement>(driver.findElements(By.xpath("//select/option")));
                        
                        for (WebElement i : e1) {
                              i.click();
                              
                              
                              String actualsize = i.getText().trim();
                              if (actualsize.startsWith(size)) {
                                    
                                    if (actualsize.contains("*ShipsFast")) {
                                          size = "<font color=green>" + size + "</font>";
                                          i.click();
                                          
                                          break;
                                    }

                                    else {
                                          size = "<font color=red>" + size + "</font>";
                                          break;
                                          
                                    }
                              }
                        }
                  }
            }

            catch (Exception ex) {
                  size = "<font color=green>" + "N/A" + "</font>";
            }

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

            
            
            // Add to cart button clicked
            WebElement button1 = driver.findElement(By.xpath("//input[@class='add-cart2']"));
            button1.click();

            // verify the cart message of shipsfast product
            
            if (driver.findElements(By.xpath("//li[@class='notice-msg']//span")).size() != 0) {
                CartMessage = "<font color=green>" + "Shipsfast cart page message is coming" + "</font>";
            } else

            {
                  CartMessage = "<font color=red>" + "Shipsfast cart page message is not coming" + "</font>";

            }

            // verify the ships fast tag in cart page next to the product
            
            if (driver.findElements(By.xpath("//dt[@class='shipsfast']")).size() != 0) {
                  ShipsfastTagCartPage = "<font color=green>" + "Shipsfast Tag On cart page Image is Present" + "</font>";
            } else {
                  ShipsfastTagCartPage = "<font color=red>" + "Shipsfast Tag On cart page Image is not Present" + "</font>";
            }
            createHtML("PASS", sku, grade, size, ShipsfastTagOnImage, ShipsfastTagDeliveryDate, ExpShippingDate,
                        CartMessage, ShipsfastTagCartPage);
      }

      /**
      * Function to get the current date value
      * 
       * @param pass
      *            the string value
      * @return todays date in string format
      */
      public String getDate(int nextdate) {
            DateFormat df = new SimpleDateFormat("dd MMM yyyy");
            Date today = Calendar.getInstance().getTime();// findout todays date
            Calendar cal = Calendar.getInstance();
            cal.setTime(today);
            cal.add(Calendar.DATE, nextdate);
            today = cal.getTime();
            String reportDate = df.format(today);
            return reportDate;// this returns todays date

      }

      public void initilizeEtry() {
            finalResult = new HashMap<String, Boolean>();
            finalResult.put("sku", true);
            finalResult.put("grade", true);
            finalResult.put("size", true);

      }

      @BeforeClass

      public void createHtML() {
            htmlReport = "<!DOCTYPE html><html><head><p>Hi All,</p>"
                        + "<p>Please find Automation Result for Shipsfast products: </p>"
                        + "<style>table {width:100%;}table, th, td {border: 1px solid black;border-collapse: collapse;}"
                        + "th, td { padding: 5px;text-align: left;} " + "table#t01 tr:nth-child(even) {background-color: #eee;}"
                        + "table#t01 tr:nth-child(odd) {background-color:#fff;}"
                        + "table#t01 th {background-color: black;color: white;}</style></head><body> " + "" + "<table ><tr>"
                        + "<th> Result </th>" + "<th>Sku</th>" + "<th>Grade</th>" + "<th>Size</th>"
                        + "<th>ShipsfastTagOnImage</th>" + "<th>ShipsfastTagDeliveryDate</th>" + "<th>ExpShippingDate</th>"
                        + "<th>CartMessage</th>" + "<th>ShipsfastTagCartPage+</th>"  + "</tr>";
      }

      @AfterClass
    public void prepareAndSendHTML() {
                    htmlReport = htmlReport + "</body></html>";
                    SendEmail sendEmail = new SendEmail();
                    sendEmail.sendMail(htmlReport, "Automation Test Report", to);
    }

      public void createHtML(String result, String sku, String grade, String size, String ShipsfastTagOnImage,
                  String ShipsfastTagDeliveryDate, String ExpShippingDate, String CartMessage, String ShipsfastTagCartPage) {
            boolean finalRes = true;
            String result1 = "PASS";

            if (!(finalResult.get("sku"))) {
                  sku = "<font color=red>" + sku + "</font>";
                  finalRes = finalRes & false;
            }
            if (!(finalResult.get("grade"))) {
                  sku = "<font color=red>" + grade + "</font>";
                  finalRes = finalRes & false;
            }
            

            if (finalRes) {
                  result1 = "<font color=green>" + result1 + "</font>";
            } else {
                  result1 = "<font color=red>" + "FAIL" + "</font>";

            }

            htmlReport = htmlReport + "<tr>" + "<td>" + result1 + "</td>" + "<td>" + sku + "</td>" + "<td>" + grade
                        + "</td>" + "<td>" + size + "</td>" + "<td>" + ShipsfastTagOnImage + " </td>" + "<td>"
                        + ShipsfastTagDeliveryDate + "</td>" + "<td>" + ExpShippingDate + "</td>" + "<td>" + CartMessage
                        + "</td>" + "<td>" + ShipsfastTagCartPage + "</td>" + "<td>" + "</tr>";
            finalResult = null;
      }
}


