package org.akka.actors;

import java.util.concurrent.TimeUnit;

import org.akka.messages.Download;
import org.akka.messages.ProcessingCompleted;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.search.entity.ProductDetails;
import org.search.util.Constants;

public class SnapdealProductPriceSearch extends Downloader
{

	@Override
	public void onReceive(Object message) throws Exception
	{
		driver = new FirefoxDriver();
		driver.manage().timeouts().implicitlyWait(10, TimeUnit.SECONDS);
		
		if (message instanceof Download)
		{
			long start = System.currentTimeMillis();
			String product = ((Download) message).getProductName();
			try
			{
				driver.get("http://www.snapdeal.com/");
				driver.findElement(By.id("keyword")).clear();
				driver.findElement(By.id("keyword")).sendKeys(product);
				driver.findElement(By.id("searchBtn")).click();
				
				By productListXpath = By.xpath("//div[@id='products-main4']/div/div/div[@class='product_grid_box']/div[@class='productWrapper']");
				//By productListXpath = By.xpath("//div[@id='products-main4']/div/div/div[@class='product_grid_box']/div[@class='productWrapper']");
				if (isListElementPresent(productListXpath))
				{
					webelements = driver.findElements(productListXpath);
				}
				else
				{
					throw new Exception("NO PRODUCT LIST AVAILABLE FOR THIS PATH");
				}
				//webelements = driver.findElements(By.xpath("//div[@id='products-main4']/div[@class='product_grid_box']"));

				By titleXpath = By.xpath("div[2]/div/a[1]");
				By priceXpath = By.xpath("div[2]/a[1]/div/div/span[@id='price']");
				By mrpXpath = By.xpath("div[2]/a[1]/div/div/span[@id='disc']/strike");
				By discXpath = By.xpath("div[2]/a[1]/div/div/span[@id='disc']");

				// int counter = 0;
				for (WebElement element : webelements)
				{
					details = new ProductDetails();
					// details.setProductDetails(element.getText());
					if(isElementPresent(element, titleXpath)){details.setProductTitle(element.findElement(titleXpath).getText());}else{details.setProductTitle("NA");};
					if(isElementPresent(element, priceXpath)){details.setProductPrice(element.findElement(priceXpath).getText());}else{details.setProductPrice("NA");};
					if(isElementPresent(element, mrpXpath)){details.setProductMrp(element.findElement(mrpXpath).getText());}else{details.setProductMrp("NA");}
					if(isElementPresent(element, discXpath)){details.setProductDiscount(element.findElement(discXpath).getText());}else{details.setProductDiscount("NA");}
					productList.add(details);
					// ++counter;
				}

			}
			catch (Exception e)
			{
				System.out.println("SOME EXCEPTION OCCURED in class"+this.getClass()+"--- EXITING APPLICATION" + e.getMessage());
				FirefoxActor.allProductsPrices.put(Constants.SNAPDEAL_KEY_PREFIX  + product, productList);
				long end = System.currentTimeMillis();
				System.out.println("In exception -- Program executed for product" + product + " @"+ Constants.SNAPDEAL_KEY_PREFIX +" in " + (end - start) + " ms ");
				driver.close();
			}

			FirefoxActor.allProductsPrices.put(Constants.SNAPDEAL_KEY_PREFIX + product, productList);
			long end = System.currentTimeMillis();
			System.out.println("Program executed for product" + product + " @"+ Constants.SNAPDEAL_KEY_PREFIX +" in " + (end - start) + " ms ");
			System.out.println("Total product found ->" + productList.size());

		}

		getSender().tell(new ProcessingCompleted(++counter, FirefoxActor.allProductsPrices), getSelf());
		driver.close();

	}
	
	
	@Override
	public boolean isElementPresent(WebElement element, By by)
	{
		// TODO Auto-generated method stub
		return super.isElementPresent(element, by);
	}

}
