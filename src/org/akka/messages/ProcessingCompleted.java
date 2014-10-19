package org.akka.messages;

import java.util.List;
import java.util.Map;

import org.search.entity.ProductDetails;

public class ProcessingCompleted
{

	private Map<String, List<ProductDetails>>	allProductsPrices;
	private int							count;

	/**
	 * @return the count
	 */
	public int getCount()
	{
		return count;
	}
	
	public ProcessingCompleted()
	{
		
	}

	public ProcessingCompleted(int count, Map<String, List<ProductDetails>> allProductsPrices)
	{
		super();
		this.count = count;
		this.allProductsPrices = allProductsPrices;
	}

	/**
	 * @param count
	 *            the count to set
	 */
	public void setCount(int count)
	{
		this.count = count;
	}

	/**
	 * @return the allProductsPrices
	 */
	public Map<String, List<ProductDetails>> getAllProductsPrices()
	{
		return allProductsPrices;
	}

	/**
	 * @param allProductsPrices
	 *            the allProductsPrices to set
	 */
	public void setAllProductsPrices(Map<String, List<ProductDetails>> allProductsPrices)
	{
		this.allProductsPrices = allProductsPrices;
	}

}
