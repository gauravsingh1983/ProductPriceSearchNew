package org.akka.actors;

import java.util.concurrent.TimeUnit;

import org.akka.actors.FirefoxActor;
import org.akka.messages.Download;
import org.akka.messages.ProcessingCompleted;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.search.entity.ProductDetails;
import org.selenium.search.ProductPriceSearch;

public class AmazonProductPriceSearch extends Downloader
{


	@Override
	public void onReceive(Object message) throws Exception
	{
		if (message instanceof Download)
		{
			long start = System.currentTimeMillis();
			String product = ((Download) message).getProductName();
			String url = "amazon-result";
			driver.get("http://www.amazon.in/");
			driver.findElement(By.id("twotabsearchtextbox")).clear();
			driver.findElement(By.id("twotabsearchtextbox")).sendKeys(product);
			driver.findElement(By.cssSelector("input.nav-submit-input")).click();
			driver.manage().timeouts().implicitlyWait(20, TimeUnit.SECONDS);
			webelements = driver.findElements(By.xpath("//div[@id='atfResults']/div"));
			// System.out.println("total web elements" + webelements.size());
			if (webelements.size() != 0)
			{
				for (WebElement element : webelements)
				{
					details = new ProductDetails();
					details.setProductDetails(element.getText());
					// details.setProductTitle(element.findElement(By.xpath("h3/a/span[1]")).getText());
					// details.setProductPrice(element.findElement(By.xpath("ul/li/div/a/span[1]")).getText());
					productList.add(details);
				}
			}
			else if (webelements.size() == 0)
			{
				webelements = driver.findElements(By.xpath("//div[@id='atfResults']/ul/li"));

				for (WebElement element : webelements)
				{
					details = new ProductDetails();
					details.setProductDetails(element.getText());
					// details.setProductTitle(element.findElement(By.xpath("div/div/div/div[2]/div/a")).getText());
					// details.setProductPrice(element.findElement(By.xpath("div/div/div/div[2]/div[2]/div/div/a/span[1]")).getText());
					productList.add(details);
				}
			}

			else
			{
				System.out.println("No products to view");
			}

			// this.writeToFile(productList, "c:/test/productprice/amazon.txt");

			FirefoxActor.allProductsPrices.put(url + product, productList);
			long end = System.currentTimeMillis();
			System.out.println("Program executed for product" + product + " in " + (end - start) + " ms ");
		}
		
		getSender().tell(new ProcessingCompleted(++counter,FirefoxActor.allProductsPrices), getSelf());
	}

}
