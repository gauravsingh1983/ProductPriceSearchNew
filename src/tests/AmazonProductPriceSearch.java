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

public class AmazonProductPriceSearch
{

	private WebDriver			driver				= new FirefoxDriver();
	List<WebElement>			webelements			= null;
	WebElement					element				= null;
	List<String>				elements			= null;
	List<ProductDetails>		productList			= new ArrayList<ProductDetails>();
	Map<String, List<String>>	allProductsPrices	= new HashMap<String, List<String>>();
	ProductDetails				details				= null;

	public AmazonProductPriceSearch(WebDriver driver)
	{

		this.driver = driver;
	}

	public AmazonProductPriceSearch()
	{
	}

	public void search(String product) throws Exception
	{
		long start = System.currentTimeMillis();

		driver.get("http://www.amazon.in/");
		driver.findElement(By.id("twotabsearchtextbox")).clear();
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys("Google Nexus 5 D821 (16GB, Black)");
		driver.findElement(By.cssSelector("input.nav-submit-input")).click();
		// webelements = driver.findElements(By.id("products"));
		webelements = driver.findElements(By.xpath("//div[@id='atfResults']/div"));
		System.out.println("total web elements" + webelements.size());
		if(webelements.size()!=0)
		{
			for (WebElement element : webelements)
			{
				details = new ProductDetails();
				details.setProductTitle(element.findElement(By.xpath("h3/a/span[1]")).getText());
				details.setProductPrice(element.findElement(By.xpath("ul/li/div/a/span[1]")).getText());
				productList.add(details);
			}
		}
		else if (webelements.size() == 0)
		{
			webelements = driver.findElements(By.xpath("//div[@id='atfResults']/ul/li"));
			
			for (WebElement element : webelements)
			{
				details = new ProductDetails();
				details.setProductTitle(element.findElement(By.xpath("div/div/div/div[2]/div/a")).getText());
				details.setProductPrice(element.findElement(By.xpath("div/div/div/div[2]/div[2]/div/div/a/span[1]")).getText());
				productList.add(details);
			}
		}

		else
		{
			System.out.println("No products to view");
		}

		
		for (ProductDetails pd : productList)
		{
			System.out.println(pd);
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("Total product:" + productList.size());

		System.out.println("Total time taken:" + (TimeUnit.MILLISECONDS.toMinutes(end - start)));

		// driver.quit();
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
		AmazonProductPriceSearch priceSearch = new AmazonProductPriceSearch();
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
