package main.java.com.ilovediamonds.entity;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class Product {
	private WebDriver driver;
	private String sku;
	private String name;
	private String size;
	private String DiamondPriceSIGH;
	private String DiamondPriceVVSEF;
	private String TGemAmount;
	private String metalPurity;
	private String Height;
	private String Width;
	private String GoldWeight;
	private String DesignType;
	private String style;
	private String DqualitySIGH;
	private String DqualityVVSEF;
	private String Dshape;
	private String TotalnoDiamonds;
	private String DtotalWeight;
	private String Collections;
	private String GemType;
	private String GemShape;
	private String TotalNoGem;
	private String TotalGemWeight;
	private String grade;
	
	
	
	public String getsku() {
	 sku = driver.findElement(By.xpath("//span[@class='product-sku']")).getText();
		return sku;
	}
	public String getgrade() {
		 grade = driver.findElement(By.xpath("//input[@id='attribute978']")).getText();
			return grade;
		}
	public String getname() {
		name = driver.findElement(By.xpath("//div[@class='product-name']")).getText();
		        return name;
	}
	public String getsize() {
		 size = driver.findElement(By.xpath("//select[@id='attribute1018']")).getText();
			return size;
		}
		
	public String getDiamondPriceSIGH() {
		 DiamondPriceSIGH =  driver.findElement(By.xpath("//div[@id='light_price_split']/ul/li[2]//span[@class='price']")).getText();
		return DiamondPriceSIGH;
		
	}
	public String getDiamondPriceVVSEF() {
		 DiamondPriceVVSEF =  driver.findElement(By.xpath("//div[@id='light_price_split']/ul/li[2]//span[@class='price']")).getText();
		return DiamondPriceVVSEF;
	}
	public String getTGemAmount() {
		 TGemAmount =  driver.findElement(By.xpath("//div[@id='light_price_split']/ul/li[3]//span[@class='price']")).getText();
		return TGemAmount;
	}
	public String getmetalPurity() {
		metalPurity = driver.findElement(By.xpath("//th[contains(text(),'Metal Purity')]/../td")).getText();
		return metalPurity;
	}
	public String getHeight() {
		Height =  driver.findElement(By.xpath("//th[contains(text(),'Height')]/../td")).getText();
		String heightinFloat = Height.split(" ")[0].toString();
		return heightinFloat;
	}
	public String getWidth() {
	    Width =  driver.findElement(By.xpath("//th[contains(text(),'Width')]/../td")).getText();
		String widthinFloat = Width.split(" ")[0].toString();
		return widthinFloat;
	}
	 public String getGoldWeight() {
			 GoldWeight = driver.findElement(By.xpath("//th[contains(text(),'Gold Weight')]/../td")).getText();
			return GoldWeight;
	}
	 public String getDesignType() {
			 DesignType = driver.findElement(By.xpath("//th[contains(text(),'Design Type')]/../td")).getText();
			return DesignType;
	 }
	 public String getstyle() {
		  style = driver.findElement(By.xpath("//th[contains(text(),'Style')]/../td")).getText();
			return style;
	}
	 public String getDqualitySIGH() {
		  DqualitySIGH = driver.findElement(By.xpath("//th[contains(text(),'Diamond Quality')]/../td")).getText();
			return DqualitySIGH;
		}
		public String getDqualityVVSEF() {
			 DqualityVVSEF = driver.findElement(By.xpath("//th[contains(text(),'Diamond Quality')]/../td")).getText();
			return DqualityVVSEF;
		}
		public String getDshape() {
			 Dshape = driver.findElement(By.xpath("//th[contains(text(),'Diamond Shape')]/../td")).getText();
			return Dshape;
		}
		public String getTotalnoDiamonds() {
			 TotalnoDiamonds = driver.findElement(By.xpath("//th[contains(text(),'Total No. of Diamonds')]/../td")).getText();
			return TotalnoDiamonds;
		}
			 
       public String getDtotalWeight() {
	         DtotalWeight = driver.findElement(By.xpath("//th[contains(text(),'Diamond Total Weight')]/../td")).getText();
	        return DtotalWeight;
	
	   }
    
      public String getCollections() {
    	 Collections = driver.findElement(By.xpath("//th[contains(text(),'Collections')]/../td")).getText();
    	return Collections;
    	
    	}
    public String getGemType() {
    	 GemType = driver.findElement(By.xpath("//th[contains(text(),'Gemstone Type')]/../td")).getText();
    	return GemType;
    	
    	}
    
    public String getGemShape() {
    	 GemShape = driver.findElement(By.xpath("//th[contains(text(),'Gemstone Shape')]/../td")).getText();
    	return GemShape;
    	
    	}
    public String getTotalNoGem() {
    	 TotalNoGem = driver.findElement(By.xpath("//th[contains(text(),'Total No. of Gemstones')]/../td")).getText();
    	return TotalNoGem;
    	
    	}
    public String getTotalGemWeight() {
    	 TotalGemWeight = driver.findElement(By.xpath("//th[contains(text(),'Gemstone Total Weight')]/../td")).getText();
    	return TotalGemWeight;
    	
    	}
	
	public Product(WebDriver driver) {
		this.driver = driver;

	}

}