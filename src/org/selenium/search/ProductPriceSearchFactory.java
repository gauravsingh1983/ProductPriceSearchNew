package org.selenium.search;

import java.util.HashMap;
import java.util.Map;

public class ProductPriceSearchFactory
{
	private static Map<String, ProductPriceSearch> searchFactory = null;
	static
	{
		searchFactory = new HashMap<String, ProductPriceSearch>();
		searchFactory.put("http://www.flipkart.com/", new FlipkartProductPriceSearch());
		searchFactory.put("http://www.amazon.in/", new AmazonProductPriceSearch());
		searchFactory.put("http://www.snapdeal.com/", new SnapdealProductPriceSearch());
		searchFactory.put("http://www.jabong.com/", new JabongProductPriceSearch());
	}
	public ProductPriceSearch createProductPriceSearch(String key)
	{
		ProductPriceSearch productPriceSearch = null;
		if(searchFactory.get(key)!=null)
			productPriceSearch = searchFactory.get(key);
		
		return productPriceSearch;
	}
}
