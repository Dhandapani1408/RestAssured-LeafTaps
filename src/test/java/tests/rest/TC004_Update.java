package tests.rest;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.apiBase.ApiBase;


import io.restassured.response.Response;
import net.minidev.json.JSONObject;
import net.minidev.json.parser.ParseException;

public class TC004_Update  extends ApiBase{

	@BeforeTest
	public void setValues() {
		testCaseName = "Create a new user and update (REST)";
		testDescription = "Create a new user and update then Verify the response";
		nodes = "User";
		authors = "Dhandapani";
		category = "API";
		dataFileName = "TC004";
		dataFileType = "json";
	}

	@Test(dataProvider = "fetchData")
	public void createIncident(File file) throws IOException, ParseException {		
		
		// Post the request
		Response postResponse = postWithBodyAsFileAndUrl(new File("./data/TC001.json"),"/users");
		//Verify the Content by Specific Key
		//verifyContentWithKey(response, "result.short_description", "This is Rest Assured Automation framework - Makaia");
		Object id = postResponse.jsonPath().get("id");
		System.out.println("id ="+id);
		
		
		String stringRequestBody = Files.readString(Paths.get("./data/TC004.json"));
		JSONObject putRequestBody = convertStringToJsonFormat(stringRequestBody);
		Response putResponse = putWithBodyParam(putRequestBody, "/users"+id);
		// Verify the Content type
		verifyContentType(putResponse, "JSON");
		
		// Verify the response status code
		verifyResponseCode(putResponse, 200);	
		
		// Verify the response time
		verifyResponseTime(putResponse, 10000);
		
	}

}
