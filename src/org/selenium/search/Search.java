package org.selenium.search;

import java.util.List;
import java.util.Map;

import org.openqa.selenium.WebDriver;

public interface Search
{
	public void search(String product) throws Exception;

	public void setWebDriver(WebDriver webDriver);
	
}
