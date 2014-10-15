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
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.search.entity.ProductDetails;

public class JabongProductPriceSearch extends ProductPriceSearch
{

	public JabongProductPriceSearch(WebDriver driver)
	{
		this.driver = driver;
	}

	public JabongProductPriceSearch()
	{
	}

	@Override
	public void search(String product) throws Exception
	{
	   // driver.get("http://www.jabong.com/");
	   // driver.findElement(By.id("searchInput")).clear();
	   // driver.findElement(By.id("searchInput")).sendKeys(product);
	   // driver.findElement(By.id("qa-searchBtn")).click();
		//String pageSourceJB = driver.findElement(By.xpath("//ul[@id='productsCatalog']")).getText();
		//System.out.println(pageSourceJB);
		//try (PrintStream out = new PrintStream(new FileOutputStream("c:/test/productprice/jabong.txt"))) {
		 //   out.print(pageSourceJB);
		///}
	
		//long start = System.currentTimeMillis();
		String url = "jabong-result";
		driver.get("http://www.jabong.com/");
		driver.findElement(By.id("searchInput")).clear();
		driver.findElement(By.id("searchInput")).sendKeys(product);
		driver.findElement(By.id("qa-searchBtn")).click();
		webelements = driver.findElements(By.xpath("//ul[@id='productsCatalog']/li"));
		
		//int counter = 0;
		for (WebElement element : webelements)
		{
			details = new ProductDetails();
			details.setProductDetails(element.getText());
			//details.setProductTitle(element.findElement(By.xpath("a/span[@id='qa-brandTitle" + counter + "']")).getText());
			//details.setProductPrice(element.findElement(By.xpath("a/span[4]/strong[@id='qa-price" + counter + "']")).getText());
			productList.add(details);
			//++counter;
		}


		FirefoxActor.allProductsPrices.put(url+product, productList);
		this.writeToFile(productList, "c:/test/productprice/jabong.txt");
		
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
