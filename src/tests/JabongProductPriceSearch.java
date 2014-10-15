package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class JabongProductPriceSearch
{

	private WebDriver			driver = new FirefoxDriver();
	List<WebElement>			webelements			= null;
	WebElement element = null;
	List<String>				elements			= null;
	List<ProductDetails>		productList			= new ArrayList<ProductDetails>();
	Map<String, List<String>>	allProductsPrices	= new HashMap<String, List<String>>();
	ProductDetails details = null;

	public JabongProductPriceSearch(WebDriver driver)
	{

		this.driver = driver;
	}
	
	public JabongProductPriceSearch()
	{
	}


	public void search(String product) throws Exception
	{
		long start = System.currentTimeMillis();

	    driver.get("http://www.jabong.com/");
	    driver.findElement(By.id("searchInput")).clear();
	    driver.findElement(By.id("searchInput")).sendKeys("burberry touch 100ml");
	    driver.findElement(By.id("qa-searchBtn")).click();
		//webelements = driver.findElements(By.id("products"));
		webelements = driver.findElements(By.xpath("//ul[@id='productsCatalog']/li"));
		//By.xpath("//table[@id='buttons']/tbody/tr[2]/td/a/span")
		System.out.println("total web elements"+webelements.size());
		int counter = 0;
		for(WebElement element: webelements)
		{
			details = new ProductDetails();
			System.out.println("xpath"+ "li/a/span[@id='qa-brandTitle"+counter+"']");
			System.out.println("li/a/span[4]/strong[@id='qa-price"+counter+"']");
			
			details.setProductTitle(element.findElement(By.xpath("a/span[@id='qa-brandTitle"+counter+"']")).getText());
			details.setProductPrice(element.findElement(By.xpath("a/span[4]/strong[@id='qa-price"+counter+"']")).getText());
			productList.add(details);
			//System.out.println(element.findElement(By.xpath("div/div/div[5]/div[2]/div[@class='product-title']")).getText());
			//System.out.println(element.findElement(By.xpath("div/div/div[5]/div[2]/a/div[@class='product-price']/div/span[@id='price']")).getText());
			++counter;
		}
		
		long end = System.currentTimeMillis();
		
		for(ProductDetails pd : productList)
		{
			System.out.println(pd);
		}
		System.out.println("Total product:" + productList.size());
		
		System.out.println("Total time taken:"+(TimeUnit.MILLISECONDS.toMinutes(end-start)) );
		
		//driver.quit();
		/*
		 * webelements =
		 * driver.findElement(By.id("products")).findElements(By.className
		 * ("pla-unit"));
		 * 
		 * if (webelements.size() == 0) { webelements =
		 * driver.findElement(By.id(
		 * "rhs_block")).findElements(By.className("pla-unit")); }
		 * 
		 * if (webelements.size() != 0) { // allProductsPrices.put(product,
		 * webelements); elements = new ArrayList<String>(); for (WebElement e :
		 * webelements) { elements.add(e.getText()); }
		 * 
		 * allProductsPrices.put(product, elements);
		 * 
		 * // System.out.println(elements); } else { throw new
		 * Exception("NO RESULTS FOUND FOR THIS SEARCH"); }
		 * 
		 * //return elements;
		 */

	}

	public static void main(String[] args)
	{
		JabongProductPriceSearch priceSearch = new JabongProductPriceSearch();
		String[] products = { "Hugo Boss Bottled Eau de Toilette - 200 ml (For Men)" };
		try
		{
			priceSearch.search("Moto X (2nd Gen) (Black)");
		}
		catch (Exception e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
