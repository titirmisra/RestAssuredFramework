package pojo;

import java.util.List;

public class BrandList {
	
	int responseCode;
	List<BrandDetails> brands;
	
	public List<BrandDetails> getBrands() {
		return brands;
	}
	public void setBrands(List<BrandDetails> brands) {
		this.brands = brands;
	}
	public int getResponseCode() {
		return responseCode;
	}
	public void setResponseCode(int responseCode) {
		this.responseCode = responseCode;
	}
}
