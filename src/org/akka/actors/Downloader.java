package org.akka.actors;

import org.akka.messages.Download;
import org.akka.messages.ProcessingCompleted;
import org.selenium.search.ProductPriceSearch;
import org.selenium.search.Search;

public class Downloader extends FirefoxActor
{

	@Override
	public void onReceive(Object message) throws Exception
	{
		if (message instanceof Download)
		{
			ProductPriceSearch priceSearch = ((Download) message).getPriceSearch();
			priceSearch.setWebDriver(this.driver);
			try
			{
				String productName = ((Download) message).getProductName();
				long start = System.currentTimeMillis();
				priceSearch.search(productName);
				long end = System.currentTimeMillis();
				System.out.println("Program executed for product" + productName + " in " + (end - start) + " ms ");
				System.out.println(FirefoxActor.allProductsPrices);
				getSender().tell(new ProcessingCompleted(++counter,FirefoxActor.allProductsPrices), getSelf());
			}
			catch (Exception e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}