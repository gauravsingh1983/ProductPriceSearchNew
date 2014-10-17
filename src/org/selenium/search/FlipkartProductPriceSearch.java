package org.selenium.search;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.concurrent.TimeUnit;

import org.akka.actors.FirefoxActor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.search.entity.ProductDetails;

public class FlipkartProductPriceSearch extends ProductPriceSearch
{

	public FlipkartProductPriceSearch(WebDriver driver)
	{

		this.driver = driver;
	}

	public FlipkartProductPriceSearch()
	{

	}

	@Override
	public void search(String product) throws Exception
	{
		//driver.get("http://www.flipkart.com/");
		//driver.findElement(By.id("fk-top-search-box")).clear();
		//driver.findElement(By.id("fk-top-search-box")).sendKeys(product);
		//driver.findElement(By.xpath("//input[@value='Search']")).click();
		//String pageSourceFK = driver.findElement(By.xpath("//div[@id='products']")).getText();

		//System.out.println(pageSourceFK);
		//try (PrintStream out = new PrintStream(new FileOutputStream("c:/test/productprice/flipkart.txt"))) {
		//    out.print(pageSourceFK);
		//}
		//long start = System.currentTimeMillis();
		String url = "flipkart-result";
		driver.get("http://www.flipkart.com/");
		driver.findElement(By.id("fk-top-search-box")).clear();
		driver.findElement(By.id("fk-top-search-box")).sendKeys(product);
		driver.findElement(By.xpath("//input[@value='Search']")).click();
		webelements = driver.findElements(By.xpath("//div[@id='products']/div/div[@class='gd-col gu3']"));
		
		for (WebElement element : webelements)
		{
			details = new ProductDetails();
			details.setProductDetails(element.getText());
			//details.setProductTitle(element.findElement(By.xpath("div/div/div[@class='pu-title fk-font-13']")).getText());
			//details.setProductPrice(element.findElement(By.xpath("div/div/div[@class='pu-price']/div/div[@class='pu-final']")).getText());
			productList.add(details);
		}

		FirefoxActor.allProductsPrices.put(url+product, productList);
		//this.writeToFile(productList, "c:/test/productprice/flipkart.txt");

				
		//long end = System.currentTimeMillis();
		//System.out.println("Total product:" + productList.size());
		//System.out.println("Total time taken:" + (TimeUnit.MILLISECONDS.toMinutes(end - start)));

	}


	@Override
	public void setWebDriver(WebDriver webDriver)
	{
		this.driver = webDriver;

	}

}
