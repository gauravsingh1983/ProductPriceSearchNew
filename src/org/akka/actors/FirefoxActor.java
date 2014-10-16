package org.akka.actors;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.search.entity.ProductDetails;

import akka.actor.UntypedActor;

public abstract class FirefoxActor extends UntypedActor
{
	protected static int						counter				= 0;
	protected WebDriver							driver;
	//public static Map<String, List<String>>	allProductsPrices	= new HashMap<String, List<String>>();
	public static Map<String, List<ProductDetails>>	allProductsPrices	= new HashMap<String, List<ProductDetails>>();
	protected List<WebElement>			webelements			= null;
	protected WebElement				element				= null;
	protected List<String>				elements			= null;
	//protected List<ProductDetails>		productList			= new ArrayList<ProductDetails>();
	protected Map<String, List<String>>	allProductsPricesStr	= new HashMap<String, List<String>>();
	protected ProductDetails			details				= null;

	@Override
	public void preStart() throws Exception
	{
		//driver = new FirefoxDriver();
		//driver.manage().timeouts().implicitlyWait(60, TimeUnit.SECONDS);
		super.preStart();
	}

	@Override
	public void postStop() throws Exception
	{
		//driver.close();
		super.postStop();
	}
	
	public boolean isElementPresent(WebElement element, By by) {
	    try {
	        element.findElement(by);
	        return true;
	    } catch (NoSuchElementException e) {
	        return false;
	    }
	}
	
	public boolean isListElementPresent(By by) {
	    try {
	        driver.findElements(by);
	        return true;
	    } catch (NoSuchElementException e) {
	        return false;
	    }
	}
}
