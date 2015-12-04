package test.java.com.smoketest;

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

public class RingBracelet extends BaseTest {

                private static String htmlReport;
                Map<String, Boolean> finalResult;
                private Map<String, List<String[]>> totalValues;

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
                                String s[][] = utility.getDataFromCSV(workingdirectory + "\\bracelet.csv", colNameToLocation);
                                totalValues = utility.getMapValues(s, colNameToLocation);
                                Integer l = totalValues.size();
                                String[][] random = new String[l][1];

                                for (int i = 0; i < l; i++) {
                                                random[i][0] = totalValues.keySet().toArray()[i].toString();
                                }
                                return random;// here it stores the no of input from the test suite file
                                                                                                // i.e the user input

                }

                @Test(dataProvider = "getRandomData1")

                public void testCSVData(String key) throws InterruptedException {
                                List<String[]> ss = totalValues.get(key);
                                for(String[] a  : ss){
                                                System.out.println(a[0] + " " + a[1] + "   "+a[2]);
                                }

                }

                public void initilizeEtry() {

                }

                @BeforeClass
                public void createHtML() {
                                htmlReport = "<!DOCTYPE html><html><head><p>Hi All,</p>"
                                                                + "<p>Please find Automation Result for Random Product Add to cart : </p>"
                                                                + "<style>table {width:100%;}table, th, td {border: 1px solid black;border-collapse: collapse;}"
                                                                + "th, td { padding: 5px; width:100%; text-align: left;} "
                                                                + "table#t01 tr:nth-child(even) {background-color: #eee;}"
                                                                + "table#t01 tr:nth-child(odd) {background-color:#fff;}"
                                                                + "table#t01 th {background-color: black;color: white;}</style></head>" + "<body> " + ""
                                                                + "<table ><tr>" + "<th>Result</th>" + "<th>Sku</th>" + "<th>goldweight</th>" +"<th>size</th>"+ "</tr>";
                }

                @AfterClass
                public void prepareAndSendHTML() {
                                htmlReport = htmlReport + "</body></html>";
                                SendEmail sendEmail = new SendEmail();
                                sendEmail.sendMail(htmlReport, "Automation Test Report",to);
                }

                public void createHTML(String Sku, String goldweight, String size) {

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
                                if (!(finalResult.get("size"))) {
                                    size = "<font color=red>" + size + "</font>";
                                    finalRes = finalRes & false;
                                }
                                
                                if (finalRes) {
                                                result = "<font color=green>" + result + "</font>";
                                }

                                else {
                                                result = "<font color=red>" + "FAIL" + "</font>";

                                }
                                htmlReport = htmlReport + "<tr>" + "<td>" + result + "</td>" + "<td>" + Sku + "</td>" + "<td>" + goldweight
                                                                + "</td>" +"<td>" + size + "</td>"+ "</tr>";
                                

                                finalResult = null;
                }
}
