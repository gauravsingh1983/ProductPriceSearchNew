package org.selenium.search;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.search.entity.ProductDetails;

public abstract class ProductPriceSearch implements Search
{
	protected WebDriver					driver;
	protected List<WebElement>			webelements			= null;
	protected WebElement				element				= null;
	protected List<String>				elements			= null;
	protected List<ProductDetails>		productList			= new ArrayList<ProductDetails>();
	protected static Map<String, List<ProductDetails>>	allProductsPrices	= new HashMap<String, List<ProductDetails>>();
	protected Map<String, List<String>>	allProductsPricesStr	= new HashMap<String, List<String>>();
	/**
	 * @return the allProductsPrices
	 */
	public Map<String, List<ProductDetails>> getAllProductsPrices()
	{
		return allProductsPrices;
	}

	/**
	 * @param allProductsPrices the allProductsPrices to set
	 */
	public void setAllProductsPrices(Map<String, List<ProductDetails>> allProductsPrices)
	{
		this.allProductsPrices = allProductsPrices;
	}

	protected ProductDetails			details				= null;

	public abstract void setWebDriver(WebDriver webDriver);

	public void writeToFile(List<ProductDetails> productList, String fileLocation) throws FileNotFoundException
	{
		StringBuffer sbf = new StringBuffer();

		for (ProductDetails pd : productList)
		{
			sbf.append(pd.toString() + "\n");
		}

		try (PrintStream out = new PrintStream(new FileOutputStream(fileLocation)))
		{
			out.print(sbf.toString());
		}

	}
}
