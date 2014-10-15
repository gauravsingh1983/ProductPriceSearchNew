package org.search.test;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class GoogleProductPriceSearch
{

	private WebDriver		driver;
	private String			baseUrl;
	List<WebElement>		webelements			= null;
	Map<String, List<WebElement>> allProductsPrices = new HashMap<String, List<WebElement>>();

	public GoogleProductPriceSearch()
	{
		driver = new FirefoxDriver();
		baseUrl = "http://localhost:8080/";
		driver.manage().timeouts().implicitlyWait(30, TimeUnit.SECONDS);
		driver.get("https://www.google.co.in/");
	}

	public void testSearch(String[] productNames) throws Exception
	{

		for (String product : productNames)
		{
			driver.findElement(By.id("gbqfq")).clear();
			driver.findElement(By.id("gbqfq")).sendKeys(product);

			driver.findElement(By.id("gbqfb")).click();
			webelements = driver.findElement(By.id("tvcap")).findElements(By.className("pla-unit"));
			if (webelements.size() == 0)
			{
				webelements = driver.findElement(By.id("rhs_block")).findElements(By.className("pla-unit"));
			}

			if (webelements.size() != 0)
			{
				allProductsPrices.put(product, webelements);
			}
			else
			{
				throw new Exception("NO RESULTS FOUND FOR THIS SEARCH");
			}
		}
		
		
		Render(allProductsPrices);
		
		driver.quit();
	}

	public void Render(Map<String, List<WebElement>> allProductsPrices)
	{
		Set<String> keys = allProductsPrices.keySet();
		Iterator<String> i = keys.iterator();
		String key = null;
		
		while(i.hasNext())
		{
			key = i.next();
			
			webelements = allProductsPrices.get(key);
			
			for(WebElement e: webelements)
			{
				System.out.println(e.getText());
			}
		}
	}
	public static void main(String[] args)
	{
		GoogleProductPriceSearch priceSearch = new GoogleProductPriceSearch();
		String[] products = { "nikon d3100", "canon 1200d", "canon 1100d" };
		try
		{
			priceSearch.testSearch(products);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
