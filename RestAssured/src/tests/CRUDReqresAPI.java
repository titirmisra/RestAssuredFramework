package tests;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.*;
import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;
import org.testng.Assert;
import pojo.CreateUser;
import pojo.UpdateUser;

public class CRUDReqresAPI {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		RestAssured.baseURI = "https://reqres.in/";

		//Get List of users in a certain page - GET request
		String pageNumber = "2";
		String listUserResponse = 
			given().
				queryParam("page",pageNumber).
			when().
				get("api/users").
			then(). 
				assertThat().statusCode(200).extract().response().asString();

		//Verify that the response has a user named "Rachel"
		String name = "Rachel";
		int id=0;
		JsonPath js = new JsonPath(listUserResponse);
		int size = js.get("data.size()");

		for(int i=0; i<size; i++) {
			String firstName = js.get("data["+i+"].first_name");
			if(firstName.equalsIgnoreCase(name)) {
				Assert.assertTrue(true);
				id = js.getInt("data["+i+"].id");
				System.out.println(js.getString("data["+i+"]"));
				break;
			}
		}
		
		/***********************************************************************************************************************************************************/

		//Get the details of a single user and verify the details - GET request
		String userResponse = 
			given().
				pathParam("id", id).
			when().
				get("api/users/{id}"). 
			then().
				assertThat().statusCode(200).extract().response().asString();

		js = new JsonPath(userResponse);
		Assert.assertEquals(name, js.getString("data.first_name"));
		System.out.println(userResponse);
		
		/***********************************************************************************************************************************************************/

		//Create an user - POST request
		CreateUser cr = new CreateUser();
		cr.setName("morpheus");
		cr.setJob("leader");
		
		String createUserRresponse = 
			given(). 
				contentType(ContentType.JSON). //Alternatively you can use header("Content-Type","application/json")
				body(cr).
			when().
				post("api/users"). 
			then(). 
				assertThat().statusCode(201).extract().response().asString();
		
		js = new JsonPath(createUserRresponse);	
		Assert.assertEquals(js.getString("name"), "morpheus");
		Assert.assertEquals(js.getString("job"), "leader");
		
		/***********************************************************************************************************************************************************/
		
		//Update an user - PUT request
		cr.setName("morpheus");
		cr.setJob("zion resident");
		
		UpdateUser u = 
			given().
				contentType(ContentType.JSON).
				body(cr). 
			when(). 
				put("api/users/2").
			then(). 
				assertThat().statusCode(200).extract().response().as(UpdateUser.class);
		Assert.assertEquals(u.getJob(), "zion resident");
		
		/***********************************************************************************************************************************************************/
		
		//Delete an user - DELETE request
	}
}