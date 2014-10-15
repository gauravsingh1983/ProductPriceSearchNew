package org.search.entity;

public class ProductDetails
{
	private String productTitle;
	private String productMrp;
	private String productPrice;
	private String productDiscount;
	private String productDetails;
	
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "ProductDetails [productTitle=" + productTitle + ", productMrp=" + productMrp + ", productPrice=" + productPrice
				+ ", productDiscount=" + productDiscount + ", productDetails=" + productDetails + "]";
	}
	/**
	 * @return the productDiscount
	 */
	public String getProductDiscount()
	{
		return productDiscount;
	}
	/**
	 * @param productDiscount the productDiscount to set
	 */
	public void setProductDiscount(String productDiscount)
	{
		this.productDiscount = productDiscount;
	}

	/**
	 * @return the productTitle
	 */
	public String getProductTitle()
	{
		return productTitle;
	}
	/**
	 * @param productTitle the productTitle to set
	 */
	public void setProductTitle(String productTitle)
	{
		this.productTitle = productTitle;
	}
	/**
	 * @return the productPrice
	 */
	public String getProductPrice()
	{
		return productPrice;
	}
	/**
	 * @param productPrice the productPrice to set
	 */
	public void setProductPrice(String productPrice)
	{
		this.productPrice = productPrice;
	}
	/**
	 * @return the productDetails
	 */
	public String getProductDetails()
	{
		return productDetails;
	}
	/**
	 * @param productDetails the productDetails to set
	 */
	public void setProductDetails(String productDetails)
	{
		this.productDetails = productDetails;
	}
	/**
	 * @return the productMrp
	 */
	public String getProductMrp()
	{
		return productMrp;
	}
	/**
	 * @param productMrp the productMrp to set
	 */
	public void setProductMrp(String productMrp)
	{
		this.productMrp = productMrp;
	}
	
		
}
