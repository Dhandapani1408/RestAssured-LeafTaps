package tests.rest;

import java.io.File;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.apiBase.ApiBase;

import io.restassured.response.Response;



public class TC001_Create extends ApiBase{

	@BeforeTest
	public void setValues() {
		testCaseName = "Create a new user (REST)";
		testDescription = "Create a new user and Verify the response";
		nodes = "User";
		authors = "Dhandapani";
		category = "API";
		dataFileName = "TC001";
		dataFileType = "json";
	}

	@Test(dataProvider = "fetchData")
	public void createUser(File file) {		
		
		// Post the request
		Response response = postWithBodyAsFileAndUrl(file,"/users");
			System.out.println(response.then().log().all());
		//Verify the Content by Specific Key
		//verifyContentWithKey(response, "result.short_description", "This is Rest Assured Automation framework - Makaia");
		
		// Verify the Content type
		verifyContentType(response, "JSON");
		
		// Verify the response status code
		verifyResponseCode(response, 201);	
		
		// Verify the response time
		verifyResponseTime(response, 10000);
		
		// Verify the response schema
		File schema = new File("./src/test/resources/PostSchema.json");	
		verifyResponseSchema(response, schema);
	}


}





