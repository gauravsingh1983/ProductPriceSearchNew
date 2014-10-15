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

public class AllSiteProductPriceSearchXML
{

	private WebDriver			driver;
	List<WebElement>			webelements			= null;
	WebElement element = null;
	List<String>				elements			= null;
	List<ProductDetails>		productList			= new ArrayList<ProductDetails>();
	Map<String, List<String>>	allProductsPrices	= new HashMap<String, List<String>>();
	ProductDetails details = null;

	public AllSiteProductPriceSearchXML(WebDriver driver)
	{

		this.driver = driver;
	}

	public void search(String product) throws Exception
	{
		long start = System.currentTimeMillis();

		String productName ="NINA RICCI NINA EDT 80ML";// "burberry touch 100ml";
		
		driver.get("http://www.flipkart.com/");
		driver.findElement(By.id("fk-top-search-box")).clear();
		driver.findElement(By.id("fk-top-search-box")).sendKeys(productName);
		driver.findElement(By.xpath("//input[@value='Search']")).click();
		String pageSourceFK = driver.findElement(By.xpath("//div[@id='products']")).getText();

		System.out.println(pageSourceFK);
		try (PrintStream out = new PrintStream(new FileOutputStream("c:/test/productprice/flipkart.txt"))) {
		    out.print(pageSourceFK);
		}
		
		driver.get("http://www.amazon.in/");
		driver.findElement(By.id("twotabsearchtextbox")).clear();
		driver.findElement(By.id("twotabsearchtextbox")).sendKeys(productName);
		driver.findElement(By.cssSelector("input.nav-submit-input")).click();
		String pageSourceAZ = driver.findElement(By.xpath("//div[@id='atfResults']")).getText();
		System.out.println(pageSourceAZ);
		try (PrintStream out = new PrintStream(new FileOutputStream("c:/test/productprice/amazon.txt"))) {
		    out.print(pageSourceAZ);
		}
		
		
	    driver.get("http://www.snapdeal.com/");
	    driver.findElement(By.id("keyword")).clear();
	    driver.findElement(By.id("keyword")).sendKeys(productName);
	    driver.findElement(By.id("searchBtn")).click();
		String pageSourceSD = driver.findElement(By.xpath("//div[@id='products-main4']")).getText();
		System.out.println(pageSourceSD);
		try (PrintStream out = new PrintStream(new FileOutputStream("c:/test/productprice/snapdeal.txt"))) {
		    out.print(pageSourceSD);
		}
		
	    driver.get("http://www.jabong.com/");
	    driver.findElement(By.id("searchInput")).clear();
	    driver.findElement(By.id("searchInput")).sendKeys(productName);
	    driver.findElement(By.id("qa-searchBtn")).click();
		String pageSourceJB = driver.findElement(By.xpath("//ul[@id='productsCatalog']")).getText();
		System.out.println(pageSourceJB);
		try (PrintStream out = new PrintStream(new FileOutputStream("c:/test/productprice/jabong.txt"))) {
		    out.print(pageSourceJB);
		}
		
		long end = System.currentTimeMillis();
		
		System.out.println("Total time taken:"+(end-start));

		driver.quit();
	}

	public static void main(String[] args)
	{
		AllSiteProductPriceSearchXML priceSearch = new AllSiteProductPriceSearchXML(new FirefoxDriver());
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
