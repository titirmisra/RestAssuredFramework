package tests;

import io.restassured.RestAssured;
import io.restassured.path.json.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

public class CRUDPlaceAPI {
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI = "https://rahulshettyacademy.com/";	
		
		//Add Place API - POST request
		String addPlaceResponse = 
		given().
			header("Content-Type","application/json").
			queryParam("key", "qaclick123")
			.body("{\r\n"
					+ "  \"location\": {\r\n"
					+ "    \"lat\": -38.383494,\r\n"
					+ "    \"lng\": 33.427362\r\n"					+ "  },\r\n"
					+ "  \"accuracy\": 50,\r\n"
					+ "  \"name\": \"Frontline house\",\r\n"
					+ "  \"phone_number\": \"(+91) 983 893 3937\",\r\n"
					+ "  \"address\": \"29, side layout, cohen 09\",\r\n"
					+ "  \"types\": [\r\n"
					+ "    \"shoe park\",\r\n"
					+ "    \"shop\"\r\n"
					+ "  ],\r\n"
					+ "  \"website\": \"http://google.com\",\r\n"
					+ "  \"language\": \"French-IN\"\r\n"
					+ "}\r\n"
					+ "").
		when().
			post("maps/api/place/add/json").
		then().
			assertThat().statusCode(200).  				//Validating status code
			body("scope", equalTo("APP")).			   	//Validating the value of a JSON key in the response
			header("Server", containsString("Apache")). //Validating the response server header value
			extract().response().asString();	
		
		System.out.println("AddPlace Response: "+addPlaceResponse);
		JsonPath js = new JsonPath(addPlaceResponse);
		String placeID = js.get("place_id");
		
		/************************************************************************************************************************************************************/
		
		//Update Place API - PUT request
		String address = "Mayfair, England";
		String updatePlaceResponse = 
		given().
		    header("Content-Type","application/json").
			queryParam("key","qaclick123").
			body("{\r\n"
					+ "\"place_id\":\""+placeID+"\",\r\n"
					+ "\"address\":\""+address+"\",\r\n"
					+ "\"key\":\"qaclick123\"\r\n"
					+ "}").	
		when().
			put("maps/api/place/update/json"). 
		then(). 
			assertThat().statusCode(200).
			body("msg", equalTo("Address successfully updated")).
			extract().response().asString();
		
		System.out.println("UpdatePlace Response: "+updatePlaceResponse);
		
		/***********************************************************************************************************************************************************/
		
		//Get Place API- GET request
		String getPlaceResponse = 
				given().
				    header("Content-Type","application/json").
					queryParam("place_id",placeID).
					queryParam("key","qaclick123").
				when().
					get("maps/api/place/get/json"). 
				then(). 
					assertThat().statusCode(200).
					body("address", equalTo(address)).
					extract().response().asString();
				
		System.out.println("GetPlace Response: "+getPlaceResponse);
		
		/***********************************************************************************************************************************************************/
		
		//Delete Place API - DELETE request
		String deletePlaceResponse = 
			given().
				header("Content-Type","application/json").
				queryParam("key","qaclick123").
				body("{\r\n"
						+ "\"place_id\":\""+placeID+"\"\r\n"
						+ "}").	
			when().
				delete("maps/api/place/delete/json"). 
			then().
				assertThat().statusCode(200).
				body("status", equalTo("OK")).
				extract().response().asString();
		
		System.out.println("DeletePlace Response: "+deletePlaceResponse);
	}
}