package tests;

public class ProductDetails
{
	private String productTitle;
	private String productPrice;
	
	
	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString()
	{
		return "ProductDetails [productTitle=" + productTitle + ", productPrice=" + productPrice + "]";
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
	
		
}
