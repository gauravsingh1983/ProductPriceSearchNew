package org.akka.actors;

import java.util.concurrent.TimeUnit;

import org.akka.messages.Download;
import org.akka.messages.ProcessingCompleted;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.search.entity.ProductDetails;
import org.search.util.Constants;

import akka.actor.ActorRef;
import akka.actor.ActorSystem;
import akka.actor.Props;
import akka.routing.RoundRobinPool;

import com.thoughtworks.selenium.webdriven.commands.GetSelectOptions;
import com.typesafe.config.Config;
import com.typesafe.config.ConfigFactory;

public class FlipkartProductPriceSearch extends Downloader
{

	@Override
	public void onReceive(Object message) throws Exception
	{
		driver = new FirefoxDriver();
		if (message instanceof Download)
		{
			long start = System.currentTimeMillis();
			String product = ((Download) message).getProductName();
			String url = "FK";

			try
			{

				driver.get("http://www.flipkart.com/");
				driver.findElement(By.id("fk-top-search-box")).clear();
				driver.findElement(By.id("fk-top-search-box")).sendKeys(product);
				driver.findElement(By.xpath("//input[@value='Search']")).click();

				By productListXpath = By.xpath("//div[@id='products']/div/div[@class='gd-col gu4']");
				By productListXpath1 = By.xpath("//div[@id='products']/div/div[@class='gd-col gu3']");
				if (isListElementPresent(productListXpath))
				{
					webelements = driver.findElements(productListXpath);
				}
				if (webelements.size()==0 && isListElementPresent(productListXpath1))
				{
					webelements = driver.findElements(productListXpath1);
				}
				else
				{
					throw new Exception("NO PRODUCT LIST AVAILABLE FOR THIS PATH");
				}

				By titleXpath = By.xpath("div/div/div[1]");
				By priceXpath = By.xpath("div/div/div[@class='pu-price']/div/div[@class='pu-final']");
				By mrpXpath = By.xpath("div/div/div[@class='pu-price']/div[1]/span[1]");
				By discXpath = By.xpath("div/div/div[@class='pu-price']/div[1]");

				for (WebElement element : webelements)
				{

					details = new ProductDetails();
					// details.setProductDetails(element.getText());
					if(isElementPresent(element, titleXpath)){details.setProductTitle(element.findElement(titleXpath).getText());}else{details.setProductTitle("NA");};
					if(isElementPresent(element, priceXpath)){details.setProductPrice(element.findElement(priceXpath).getText());}else{details.setProductPrice("NA");};
					if(isElementPresent(element, mrpXpath)){details.setProductMrp(element.findElement(mrpXpath).getText());}else{details.setProductMrp("NA");}
					if(isElementPresent(element, discXpath)){details.setProductDiscount(element.findElement(discXpath).getText());}else{details.setProductDiscount("NA");}
					productList.add(details);

				}
			}
			catch (Exception e)
			{
				System.out.println("SOME EXCEPTION OCCURED in class"+this.getClass()+"--- EXITING APPLICATION" + e.getMessage());
				FirefoxActor.allProductsPrices.put(Constants.FLIPKART_KEY_PREFIX + product, productList);
				long end = System.currentTimeMillis();
				System.out.println("In exception -- Program executed for product" + product + " @"+ Constants.FLIPKART_KEY_PREFIX +"  in " + (end - start) + " ms ");
				getSender().tell(new ProcessingCompleted(++counter, FirefoxActor.allProductsPrices), getSelf());
				driver.close();
			}

			FirefoxActor.allProductsPrices.put(Constants.FLIPKART_KEY_PREFIX + product, productList);
			long end = System.currentTimeMillis();
			System.out.println("Program executed for product" + product + " @"+ Constants.FLIPKART_KEY_PREFIX +"  in " + (end - start) + " ms ");
			System.out.println("Total product found ->" + productList.size());
		}
		getSender().tell(new ProcessingCompleted(++counter, FirefoxActor.allProductsPrices), getSelf());
		driver.close();
	}

	
}
