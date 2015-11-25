package test.java.com.smoketest;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;


public class ExpectedDeliveryDate {

 /**
  * @param args
  * @throws ParseException
  */
 public static void main(String[] args) throws ParseException {
	 WebDriver driver =  new FirefoxDriver();
	 driver.get("http://www.ilovediamonds.com/jewellery/earrings/purple-squiggle-earrings-13571.html");
     driver.manage().window().maximize();

     
  ExpectedDeliveryDate e = new ExpectedDeliveryDate();
  String s = e.getDate(3);//get the date after 17 days 
  System.out.println(s);
  
  WebElement element = driver.findElement(By.xpath("//form[@id='product_addtocart_form']/div[3]/div/div[3]/div[2]/span"));
  String actualdate = s;
  String expecteddate = element.getText();
  
 if (expecteddate.equalsIgnoreCase(actualdate)){
	  System.out.println("Date matched with the product page");
	 }
  else{
	  System.out.println("Date not matched with the product page");
  }
 }
/** Function to get the current date value
 * 
 * @param pass the string value
 * @return todays date in string format
 */
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
 
 
 }