package org.search.test;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.akka.actors.FirefoxActor;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.selenium.search.GoogleProductPriceSearch;

public class FlipkartProductPriceSearch
{

	private WebDriver			driver;
	WebElement					webelement = null;
	List<WebElement>			webelements			= null;
	List<String>				elements			= null;
	Map<String, List<String>>	allProductsPrices	= new HashMap<String, List<String>>();

	public FlipkartProductPriceSearch(WebDriver driver)
	{

		this.driver = driver;
	}

	public void search(String[] productNames) throws Exception
	{

		for (String product : productNames)
		{
			driver.get("http://www.flipkart.com/");
			driver.findElement(By.id("fk-top-search-box")).clear();
			driver.findElement(By.id("fk-top-search-box")).sendKeys("Canon EOS 1200D Kit (EF S18-55 IS II) DSLR Camera(Black) ");
			driver.findElement(By.xpath("//input[@value='Search']")).click();

			//webelements = driver.findElement(By.id("products")).findElements(By.className("pu-details lastUnit"));
			driver.findElement(By.id("products")).findElement(By.linkText("Canon EOS 1200D Kit (EF S18-55 IS II) DSLR Camera(Black) ")).click();
			webelement = driver.findElement(By.className("prices")).findElement(By.xpath("//span[@class='selling-price omniture-field']"));
			
			System.out.println(webelement.getText());

			/*if (webelements.size() != 0)
			{
				// allProductsPrices.put(product, webelements);
				elements = new ArrayList<String>();
				for (WebElement e : webelements)
				{
					elements.add(e.getText());
				}

				allProductsPrices.put(product, elements);

				// System.out.println(elements);
			}
			else
			{
				throw new Exception("NO RESULTS FOUND FOR THIS SEARCH");
			}*/
		}
		System.out.println(allProductsPrices);
		driver.quit();
	}

	public static void main(String[] args)
	{

		FlipkartProductPriceSearch priceSearch = new FlipkartProductPriceSearch(new FirefoxDriver());
		String[] products = { "Davidoff Cool Water Eau de Toilette - 125 ml (For Men)" };
		try
		{
			priceSearch.search(products);
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
