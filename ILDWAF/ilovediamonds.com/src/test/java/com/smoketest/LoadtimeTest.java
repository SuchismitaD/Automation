package test.java.com.smoketest;


import java.util.LinkedHashMap;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.WebDriver;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Parameters;
import org.testng.annotations.Test;


import main.java.com.ilovediamonds.basetest.BaseTest;
import main.java.com.ilovediamonds.entity.Product;
import main.java.com.ilovediamonds.utilities.SendEmail;
import main.java.com.ilovediamonds.utilities.Utilities;


public class LoadtimeTest extends BaseTest {
                private static String htmlReport;
                private WebDriver driver;
                

                @DataProvider(name = "getTimeOut")
                public Object[][] getData() {
                                String workingdirectory = System.getProperty("user.dir");

                                LinkedHashMap<String, Integer> colNameToLocation = new LinkedHashMap<String, Integer>();

                                String s[][] = utility.getDataFromCSV(workingdirectory + "\\Timeout.csv", colNameToLocation);

                                String random[][] = null;
                                if (isTotalCount) // this is the boolean value declared in the basetest
                                                random = utility.getRandomLink(s, s.length, colNameToLocation);
                                else
                                                random = utility.getRandomLink(s, count, colNameToLocation);
                                return random;

                }

                @Test(dataProvider = "getTimeOut")
                public void setData(String link,String expectedTime) throws InterruptedException {
                	Thread.sleep(10000);
                	driver = threadDriver.get();
                                Thread.sleep(3000);
                                driver.manage().deleteAllCookies();
                                long startTime = System.currentTimeMillis();
                                System.out.println("The startTime is " + startTime);
                                driver.get(link);
                                long endTime = System.currentTimeMillis();
                                System.out.println("The endTime is " + endTime);
                                long totalTime = endTime - startTime;
                                long actualTime = TimeUnit.MILLISECONDS.toSeconds(totalTime);
                                System.out.println(actualTime);
                                long strExpectedTime = TimeUnit.SECONDS.toMillis(Long.parseLong(expectedTime));
                                

                                if (strExpectedTime <= totalTime)
                                                createHtML("FAIL", link,expectedTime, actualTime);
                                else
                                                createHtML("PASS", link,expectedTime, actualTime);
                }
           
                @BeforeClass
                public void createHTML() {
                	       htmlReport = "<!DOCTYPE html><html><head><p>Hi All,</p>"
                                                                + "<p>Please find Automation Result for Page Load Time Out: </p>"
                                                                + "<style>table {width:100%;}table, th, td {border: 1px solid black;border-collapse: collapse;}"
                                                                + "th, td { padding: 5px;text-align: left;} " + "table#t01 tr:nth-child(even) {background-color: #eee;}"
                                                                + "table#t01 tr:nth-child(odd) {background-color:#fff;}"
                                                                + "table#t01 th {background-color: black;color: white;}</style></head><body> " + "" + "<table ><tr>"
                                                                + "<th> Result </th>" + "<th>Link</th>" + "<th>ExpectedTime</th>" + "<th>ActualTime</th>" + "</tr>";
                }

                @AfterClass
                public void prepareAndSendHTML() {
                      htmlReport = htmlReport + "</body></html>";
                      SendEmail sendEmail = new SendEmail();
                      sendEmail.sendMail(htmlReport, "Automation Test Report",to);
                }

                

                public void createHtML(String result, String Link, String ExpectedTime, long actualTime) {
                                htmlReport = htmlReport + "<tr>" + "<td>" + result + "</td>" + "<td>" + Link + "</td>" + "<td>" + ExpectedTime
                                                                + "</td>" + "<td>" + actualTime + "</td>" + "</tr>";
                }
}
