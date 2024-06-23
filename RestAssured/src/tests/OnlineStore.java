package tests;

import io.restassured.RestAssured;
import io.restassured.parsing.Parser;
import io.restassured.path.json.*;
import pojo.BrandList;
import pojo.Product;
import pojo.ProductResponse;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import java.util.*;

public class OnlineStore {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		//Get the list of all the products in the store 
		RestAssured.registerParser("text/html", Parser.JSON); //The JSON response is displayed in HTML format by default. This will convert the HTML response to JSON
		RestAssured.baseURI = "https://automationexercise.com/";
		
		ProductResponse pr = 
		given().
		when().
			get("api/productsList"). 
		then().
			assertThat().statusCode(200).extract().response().as(ProductResponse.class);
		
		//Display the names of all the products in the store
		System.out.println("The list of products in the store are: ");
		System.out.println();	
		for(int i=0; i<pr.getProducts().size(); i++) 
			System.out.println(i+1+". "+pr.getProducts().get(i).getName());
		
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
		
		/***********************************************************************************************************************************************************/
		
		//Get the list of all product categories in the response
		List<String> productCategory = new ArrayList<String>();
		for(int i=0; i<pr.getProducts().size(); i++)
			productCategory.add(pr.getProducts().get(i).getCategory().getCategory());
		
		for(int i=0; i<productCategory.size(); i++)
			System.out.println(productCategory.get(i));
		
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
		
		/***********************************************************************************************************************************************************/
		
		//Display all the items where the userType is Kids or Women
		for(int i=0; i<pr.getProducts().size(); i++) {
			String userType = pr.getProducts().get(i).getCategory().getUsertype().getUsertype();
			if(userType.equalsIgnoreCase("Kids") || userType.equalsIgnoreCase("Women"))
				System.out.println(i+1+". Name - "+pr.getProducts().get(i).getName()+". User Type - " +userType);
		}
		
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
		
		/***********************************************************************************************************************************************************/
		
		//Get the brand-details list
		BrandList bl =
		given().
		when().
			get("api/brandsList"). 
		then(). 
			assertThat().extract().response().as(BrandList.class);
		
		for(int i=0; i<bl.getBrands().size(); i++)
			System.out.println(bl.getBrands().get(i).getBrand());
		
		System.out.println("--------------------------------------------------------------------------------------------------------------------------------------");
		
		/***********************************************************************************************************************************************************/
		
		//Search for a particular product that belongs to the first product category in the response
		pr = 
		given().
			formParam("search_product", productCategory.get(0)).	
		when().
			post("api/searchProduct").
		then(). 
			assertThat().statusCode(200).extract().response().as(ProductResponse.class);
		
		for(int i=0; i<pr.getProducts().size(); i++)
			System.out.println(pr.getProducts().get(i).getName());
	}
}