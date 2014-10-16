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

public class JabongProductPriceSearch extends Downloader
{

	@Override
	public void onReceive(Object message) throws Exception
	{
		List<ProductDetails>		productList			= new ArrayList<ProductDetails>();
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		if (message instanceof Download)
		{
			long start = System.currentTimeMillis();
			String product = ((Download) message).getProductName();
			try
			{
				// driver = new FirefoxDriver();
				driver.get("http://www.jabong.com/");
				driver.findElement(By.id("searchInput")).clear();
				driver.findElement(By.id("searchInput")).sendKeys(product);
				driver.findElement(By.id("qa-searchBtn")).click();
				
				webelements = driver.findElements(By.xpath("//ul[@id='productsCatalog']/li"));
				
				By titleXpath = By.xpath("a/span[3]");
				By priceXpath = By.xpath("a/span[4]/strong[1]");
				By mrpXpath = By.xpath("a/span[4]/strike[1]");
				By discXpath = By.xpath("a/span[4]/span[1]");


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
				FirefoxActor.allProductsPrices.put(Constants.JABONG_KEY_PREFIX + product, productList);
				long end = System.currentTimeMillis();
				System.out.println("In exception -- Program executed for product" + product + " in " + (end - start) + " ms ");
				driver.close();
			}
			
			FirefoxActor.allProductsPrices.put(Constants.JABONG_KEY_PREFIX + product, productList);
			long end = System.currentTimeMillis();
			System.out.println("Program executed for product" + product + " @"+ Constants.JABONG_KEY_PREFIX +"  in " + (end - start) + " ms ");
			System.out.println("Total product found ->" + productList.size());

		}

		getSender().tell(new ProcessingCompleted(++counter, FirefoxActor.allProductsPrices), getSelf());
		driver.close();
	}

}
