package org.akka.actors;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.akka.actors.FirefoxActor;
import org.akka.messages.Download;
import org.akka.messages.ProcessingCompleted;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.search.entity.ProductDetails;
import org.search.util.Constants;
import org.selenium.search.ProductPriceSearch;

public class ShoppersStopProductPriceSearch extends Downloader
{

	@Override
	public void onReceive(Object message) throws Exception
	{
		List<ProductDetails>		productList			= new ArrayList<ProductDetails>();
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
		if (message instanceof Download)
		{
			long start = System.currentTimeMillis();
			String product = ((Download) message).getProductName();
			try
			{
				// driver = new FirefoxDriver();
				driver.get("http://www.shoppersstop.com/");
				driver.findElement(By.id("search")).clear();
				driver.findElement(By.id("search")).sendKeys(product);
				//driver.findElement(By.id("webklipper-publisher-widget-container-notification-close-div")).click();
				driver.findElement(By.name("botton")).click();
				Thread.sleep(100);
				webelements = driver.findElements(By.xpath("//div[@class='category-products']/ul/li"));
				
				System.out.println(webelements.size());
				//System.out.println(webelements.toString());
				
				By titleXpath = By.xpath("div[@class='product-name']/h2/a[1]");
				By priceXpath = By.xpath("div[@class='price-box']/span[@class='regular-price']");
				By mrpXpath = By.xpath("div[@class='price-box']/span[@class='old-price']");
				By discXpath = By.xpath("div[@class='price-box']/span[@class='special-price']");


				// int counter = 0;
				for (WebElement element : webelements)
				{
					details = new ProductDetails();
					// details.setProductDetails(element.getText());
					if(isElementPresent(element, titleXpath)){details.setProductTitle(element.findElement(titleXpath).getText());}else{details.setProductTitle(Constants.NA);};
					if(isElementPresent(element, priceXpath)){details.setProductPrice(element.findElement(priceXpath).getText());}else{details.setProductPrice(Constants.NA);};
					if(isElementPresent(element, mrpXpath)){details.setProductMrp(element.findElement(mrpXpath).getText());}else{details.setProductMrp(Constants.NA);}
					if(isElementPresent(element, discXpath)){details.setProductDiscount(element.findElement(discXpath).getText());}else{details.setProductDiscount(Constants.NA);}
					productList.add(details);
					// ++counter;
				}

			}
			catch (Exception e)
			{
				System.out.println("SOME EXCEPTION OCCURED in class"+this.getClass()+"--- EXITING APPLICATION" + e.getMessage());
				FirefoxActor.allProductsPrices.put(Constants.SHOPPERS_KEY_PREFIX + product, productList);
				long end = System.currentTimeMillis();
				System.out.println("In exception -- Program executed for product" + product + " in " + (end - start) + " ms ");
				driver.close();
			}
			
			FirefoxActor.allProductsPrices.put(Constants.SHOPPERS_KEY_PREFIX + product, productList);
			long end = System.currentTimeMillis();
			System.out.println("Program executed for product" + product + " @"+ Constants.SHOPPERS_KEY_PREFIX +"  in " + (end - start) + " ms ");
			System.out.println("Total product found ->" + productList.size());

		}

		getSender().tell(new ProcessingCompleted(++counter, FirefoxActor.allProductsPrices), getSelf());
		driver.close();
	}

}
