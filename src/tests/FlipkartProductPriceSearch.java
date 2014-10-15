package tests;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FlipkartProductPriceSearch
{

	private WebDriver			driver;
	List<WebElement>			webelements			= null;
	WebElement element = null;
	List<String>				elements			= null;
	List<ProductDetails>		productList			= new ArrayList<ProductDetails>();
	Map<String, List<String>>	allProductsPrices	= new HashMap<String, List<String>>();
	ProductDetails details = null;

	public FlipkartProductPriceSearch(WebDriver driver)
	{

		this.driver = driver;
	}

	public void search(String product) throws Exception
	{
		long start = System.currentTimeMillis();

		driver.get("http://www.flipkart.com/");
		driver.findElement(By.id("fk-top-search-box")).clear();
		driver.findElement(By.id("fk-top-search-box")).sendKeys("Hugo Boss Bottled Eau de Toilette - 200 ml (For Men)");
		driver.findElement(By.xpath("//input[@value='Search']")).click();
		//webelements = driver.findElements(By.id("products"));
		webelements = driver.findElements(By.xpath("//div[@id='products']/div/div[@class='gd-col gu3']"));
		//By.xpath("//table[@id='buttons']/tbody/tr[2]/td/a/span")
		for(WebElement element: webelements)
		{
			details = new ProductDetails();
			details.setProductTitle(element.findElement(By.xpath("div/div/div[@class='pu-title fk-font-13']")).getText());
			details.setProductPrice(element.findElement(By.xpath("div/div/div[@class='pu-price']/div/div[@class='pu-final']")).getText());
			productList.add(details);
			//System.out.println(element.findElement(By.xpath("div/div/div[@class='pu-title fk-font-13']")).getText());
			//System.out.println(element.findElement(By.xpath("div/div/div[@class='pu-price']/div/div[@class='pu-final']")).getText());
			
		}
		
		long end = System.currentTimeMillis();
		
		for(ProductDetails pd : productList)
		{
			System.out.println(pd);
		}
		System.out.println("Total product:" + productList.size());
		
		System.out.println("Total time taken:"+(TimeUnit.MILLISECONDS.toMinutes(end-start)) );


	}

	public static void main(String[] args)
	{
		FlipkartProductPriceSearch priceSearch = new FlipkartProductPriceSearch(new FirefoxDriver());
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
