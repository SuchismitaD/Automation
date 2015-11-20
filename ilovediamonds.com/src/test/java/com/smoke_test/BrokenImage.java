package test.java.com.smoke_test;


	 import java.util.List;

	 import org.apache.http.HttpResponse;
	 import org.apache.http.client.HttpClient;
	 import org.apache.http.client.methods.HttpGet;
	 import org.apache.http.impl.client.HttpClientBuilder;
	 import org.openqa.selenium.By;
	 import org.openqa.selenium.WebDriver;
	 import org.openqa.selenium.WebElement;
	 import org.openqa.selenium.firefox.FirefoxDriver;
	 import org.testng.annotations.AfterClass;
	 import org.testng.annotations.BeforeClass;
	 import org.testng.annotations.Test;

	 public class BrokenImage {

	  private WebDriver driver;
	  private int invalidImageCount;

	  @BeforeClass
	  public void setUp() {
	   driver = new FirefoxDriver();
	   driver.get("http://www.ilovediamonds.com/");
	  }

	  @Test
	  public void validateInvalidImages() {
	   try {
	    invalidImageCount = 0;
	    List<WebElement> imagesList = driver.findElements(By.tagName("img"));
	    System.out.println("Total no. of images present " + imagesList.size());
	    for (WebElement imgElement : imagesList) {
	     if (imgElement != null) {
	      verifyimageActive(imgElement);
	     }
	    }
	    System.out.println("Total no. of broken images found " + invalidImageCount);
	   } catch (Exception e) {
	    e.printStackTrace();
	    System.out.println(e.getMessage());
	   }
	  }

	  @AfterClass
	  public void tearDown() {
	   if (driver != null)
	    driver.quit();
	  }

	  public void verifyimageActive(WebElement imgElement) {
	   try {
	    HttpClient client = HttpClientBuilder.create().build();
	    HttpGet request = new HttpGet(imgElement.getAttribute("src"));
	    HttpResponse response = client.execute(request);
	    // verifying response code he HttpStatus should be 200 if not,
	    // increment as invalid images count
	    if (response.getStatusLine().getStatusCode() != 200)
	     invalidImageCount++;
	   } catch (Exception e) {
	    e.printStackTrace();
	   }
	  }
	 }


