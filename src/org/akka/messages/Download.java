package org.akka.messages;

import org.selenium.search.ProductPriceSearch;

public class Download
{
	private final String	productName;
	
	private final ProductPriceSearch priceSearch;
	

	public Download(String productName, ProductPriceSearch priceSearch)
	{
		super();
		this.productName = productName;
		this.priceSearch = priceSearch;
	}
	
	public Download(String productName)
	{
		super();
		this.productName = productName;
		this.priceSearch = null;
	}


	/**
	 * @return the productName
	 */
	public String getProductName()
	{
		return productName;
	}
	/**
	 * @return the priceSearch
	 */
	public ProductPriceSearch getPriceSearch()
	{
		return priceSearch;
	}

}
