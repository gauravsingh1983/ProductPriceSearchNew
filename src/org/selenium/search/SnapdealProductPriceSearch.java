package org.selenium.search;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.akka.actors.FirefoxActor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.search.entity.ProductDetails;

public class SnapdealProductPriceSearch extends ProductPriceSearch
{

	public SnapdealProductPriceSearch(WebDriver driver)
	{
		this.driver = driver;
	}

	public SnapdealProductPriceSearch()
	{
	}

	@Override
	public void search(String product) throws Exception
	{
	    //driver.get("http://www.snapdeal.com/");
	    //driver.findElement(By.id("keyword")).clear();
	    //driver.findElement(By.id("keyword")).sendKeys(product);
	   // driver.findElement(By.id("searchBtn")).click();
		//String pageSourceSD = driver.findElement(By.xpath("//div[@id='products-main4']")).getText();
		//System.out.println(pageSourceSD);
		//try (PrintStream out = new PrintStream(new FileOutputStream("c:/test/productprice/snapdeal.txt"))) {
		//    out.print(pageSourceSD);
		//}
	
		//long start = System.currentTimeMillis();
		String url = "snapdeal-result";
		driver.get("http://www.snapdeal.com/");
		driver.findElement(By.id("keyword")).clear();
		driver.findElement(By.id("keyword")).sendKeys(product);
		driver.findElement(By.id("searchBtn")).click();
		webelements = driver.findElements(By.xpath("//div[@id='products-main4']/div[@class='product_grid_row']/div"));
		
		for (WebElement element : webelements)
		{
			details = new ProductDetails();
			details.setProductDetails(element.getText());
			//details.setProductTitle(element.findElement(By.xpath("div/div/div[5]/div[2]/div[@class='product-title']")).getText());
			//details.setProductPrice(element.findElement(By.xpath("div/div/div[5]/div[2]/a/div[@class='product-price']/div/span[@id='price']"))
			//		.getText());
			productList.add(details);
			//System.out.println(element.findElement(By.xpath("div/div/div[5]/div[2]/div[@class='product-title']")).getText());
			//System.out.println(element.findElement(By.xpath("div/div/div[5]/div[2]/a/div[@class='product-price']/div/span[@id='price']")).getText());

		}

		
		FirefoxActor.allProductsPrices.put(url+product, productList);
		//this.writeToFile(productList, "c:/test/productprice/snapdeal.txt");
		
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
