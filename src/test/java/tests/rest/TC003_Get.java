package tests.rest;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.apiBase.ApiBase;

import io.restassured.response.Response;



public class TC003_Get extends ApiBase{

	@BeforeTest
	public void setValues() {
		testCaseName = "Get Existing User (REST)";
		testDescription = "Get all User and Print the first Name";
		nodes = "User";
		authors = "Dhandapani";
		category = "API";
		dataFileName = "TC001";
		dataFileType = "json";
	}

	@Test()
	public void getIncidents() {		
		
		// Get the request
		Response response = get("/users");
				
		// Verify the Content type
		verifyContentType(response, "JSON");
		
		// Verify the response status code
		verifyResponseCode(response, 200);	
		
		// Verify the response time
		verifyResponseTime(response, 10000);
		
		// Print the first incident number
		//String number = (String) response.jsonPath().getList("result.number").get(0);
		String name =(String) getParameterValueUsingParameterName(response, "first_name", 0);
		System.out.println("name is "+name);
		
	}


}





