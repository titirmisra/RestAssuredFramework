package pojo;

import java.util.List;

public class ProductResponse {
	
	private int responseCode;
	private List<Product> products;
		
	public List<Product> getProducts() {
		return products;
	}
	public void setProducts(List<Product> products) {
		this.products = products;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
}
