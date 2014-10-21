package org.search.util;

public interface Constants
{

	public static final String	NA	= "NA";
	
	public static final String	SNAPDEAL_KEY_PREFIX	= "SD-";
	public static final String	FLIPKART_KEY_PREFIX	= "FK-";
	public static final String	JABONG_KEY_PREFIX	= "JB-";
	public static final String	AMAZON_KEY_PREFIX	= "AZ-";
	public static final String	SHOPPERS_KEY_PREFIX	= "SS-";

	public static final String	SD_PRD_TITLE_XPATH	= "";
	public static final String	SD_PRD_PRICE_XPATH	= "";
	public static final String	SD_PRD_MRP_XPATH	= "";
	public static final String	SD_PRD_DISC_XPATH	= "";

	public static final String	FK_PRD_TITLE_XPATH	= "div/div/div[1]";
	public static final String	FK_PRD_PRICE_XPATH	= "div/div/div[@class='pu-price']/div/div[@class='pu-final']";
	public static final String	FK_PRD_MRP_XPATH	= "div/div/div[@class='pu-price']/div[1]/span[1]";
	public static final String	FK_PRD_DISC_XPATH	= "div/div/div[@class='pu-price']/div[1]";

	public static final String	JB_PRD_TITLE_XPATH	= "a/span[3]";
	public static final String	JB_PRD_PRICE_XPATH	= "a/span[4]/strong[1]";
	public static final String	JB_PRD_MRP_XPATH	= "a/span[4]/strike[1]";
	public static final String	JB_PRD_DISC_XPATH	= "a/span[4]/span[1]";

	public static final String	AZ_PRD_TITLE_XPATH	= "";
	public static final String	AZ_PRD_PRICE_XPATH	= "";
	public static final String	AZ_PRD_MRP_XPATH	= "";
	public static final String	AZ_PRD_DISC_XPATH	= "";
}
