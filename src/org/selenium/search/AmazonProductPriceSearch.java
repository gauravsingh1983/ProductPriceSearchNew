package org.selenium.search;

import org.akka.actors.FirefoxActor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.search.entity.ProductDetails;

public class AmazonProductPriceSearch extends ProductPriceSearch
{

	public AmazonProductPriceSearch(WebDriver driver)
	{

		this.driver = driver;
	}

	public AmazonProductPriceSearch()
	{
	}

	@Override
	public void search(String product) throws Exception
	{
		//driver.get("http://www.amazon.in/");
		//driver.findElement(By.id("twotabsearchtextbox")).clear();
		//driver.findElement(By.id("twotabsearchtextbox")).sendKeys(product);
		//driver.findElement(By.cssSelector("input.nav-submit-input")).click();
		//String pageSourceAZ = driver.findElement(By.xpath("//div[@id='atfResults']")).getText();
		//System.out.println(pageSourceAZ);
		//try (PrintStream out = new PrintStream(new FileOutputStream("c:/test/productprice/amazon.txt"))) {out.print(pageSourceAZ);}
		
		
		//long start = System.currentTimeMillis();
		String url = "amazon-result";
		driver.get("http://www.amazon.in/");
		driver.findElement(By.id("twotabsearchtextbox")).clear();
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys(product);
		driver.findElement(By.cssSelector("input.nav-submit-input")).click();
		webelements = driver.findElements(By.xpath("//div[@id='atfResults']/div"));
		//System.out.println("total web elements" + webelements.size());
		if(webelements.size()!=0)
		{
			for (WebElement element : webelements)
			{
				details = new ProductDetails();
				details.setProductDetails(element.getText());
				//details.setProductTitle(element.findElement(By.xpath("h3/a/span[1]")).getText());
				//details.setProductPrice(element.findElement(By.xpath("ul/li/div/a/span[1]")).getText());
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
				//details.setProductTitle(element.findElement(By.xpath("div/div/div/div[2]/div/a")).getText());
				//details.setProductPrice(element.findElement(By.xpath("div/div/div/div[2]/div[2]/div/div/a/span[1]")).getText());
				productList.add(details);
			}
		}

		else
		{
			System.out.println("No products to view");
		}

		//this.writeToFile(productList, "c:/test/productprice/amazon.txt");
		
		FirefoxActor.allProductsPrices.put(url+product, productList);
		
		//long end = System.currentTimeMillis();
		//System.out.println("Total product:" + productList.size());
		//System.out.println("Total time taken:" + (TimeUnit.MILLISECONDS.toMinutes(end - start)));

	}


	protected String getGeneratedKey(String website, String product, int counter)
	{
		return website +"-"+ product + "-"+counter;
	}
	
	@Override
	public void setWebDriver(WebDriver webDriver)
	{
		this.driver = webDriver;
	}

}
