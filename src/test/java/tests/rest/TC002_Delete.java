package tests.rest;

import java.io.File;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.apiBase.ApiBase;

import io.restassured.response.Response;



public class TC002_Delete extends ApiBase{

	@BeforeTest
	public void setValues() {

		testCaseName = "Delete the first user (REST)";
		testDescription = "Get all user from the search and delete the first user";
		nodes = "User";
		authors = "Dhandapani";
		category = "REST";
		dataFileName = "TC002";
		dataFileType = "json";
	}

	@Test()
	public void deleteUser() {		
		
		// Post the request
		Response response = get("/users");		
			
		// Verify the Content type
		verifyContentType(response, "JSON");
		
		// Verify the response status code
		verifyResponseCode(response, 200);	
		
		// Verify the response time
		verifyResponseTime(response, 10000);
		
		// Verify the response schema
		File schema = new File("./src/test/resources/GetAllSchema.json");	
		verifyResponseSchema(response, schema);
		// Get the Incidents
	//	List<String> contents = getContentsWithKey(response, "data[1].id");
		
		int id = (Integer) getParameterValueUsingParameterName(response, "id", 0);
		
		// Delete the first incident
		response = delete("/users/"+id);
		
		System.out.println("ID = "+id);
		int statusCode = response.getStatusCode();
		System.out.println(statusCode);

		// Verify the response status code
		verifyResponseCode(response, 204);	
		
		
	}


}





