package tests;

import java.io.FileOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;

public class FlipkartProductPriceSearchXML
{

	private WebDriver			driver;
	List<WebElement>			webelements			= null;
	WebElement element = null;
	List<String>				elements			= null;
	List<ProductDetails>		productList			= new ArrayList<ProductDetails>();
	Map<String, List<String>>	allProductsPrices	= new HashMap<String, List<String>>();
	ProductDetails details = null;

	public FlipkartProductPriceSearchXML(WebDriver driver)
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
		String pageSource = driver.findElement(By.xpath("//div[@id='products']")).getText();

		System.out.println(pageSource);
		try (PrintStream out = new PrintStream(new FileOutputStream("c:/test/flipkart.txt"))) {
		    out.print(pageSource);
		}
		long end = System.currentTimeMillis();
		
		System.out.println("Total time taken:"+(end-start));

		driver.quit();
	}

	public static void main(String[] args)
	{
		FlipkartProductPriceSearchXML priceSearch = new FlipkartProductPriceSearchXML(new FirefoxDriver());
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
