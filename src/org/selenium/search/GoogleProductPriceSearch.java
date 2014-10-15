package org.selenium.search;

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

public class GoogleProductPriceSearch extends ProductPriceSearch implements Search
{

	public GoogleProductPriceSearch(WebDriver driver)
	{

		this.driver = driver;
	}

	public void search(String product) throws Exception
	{

		driver.get("https://www.google.co.in/");
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
			// allProductsPrices.put(product, webelements);
			elements = new ArrayList<String>();
			for (WebElement e : webelements)
			{
				// e.
				elements.add(e.getText());
			}

			allProductsPricesStr.put(product, elements);

			// System.out.println(elements);
		}
		else
		{
			throw new Exception("NO RESULTS FOUND FOR THIS SEARCH");
		}

		// return elements;

	}

	@Override
	public void setWebDriver(WebDriver webDriver)
	{
		this.driver = webDriver;
	}

}
