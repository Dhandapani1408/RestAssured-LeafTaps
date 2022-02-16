package com.apiAnnotations;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Properties;

import org.testng.annotations.AfterClass;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterSuite;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.DataProvider;

import com.apiUtils.DataInputProvider;
import com.apiUtils.HTMLReporter;

import io.restassured.RestAssured;

public class PreAndPost extends HTMLReporter{
	
	public static String dataFileName, dataFileType;	
	
	
	@BeforeSuite
	public void beforeSuite() {
		File file = new File("./reports/result.html");
		if(file.exists()) {
			file.delete();
		}
		startReport();
	}
	
	@BeforeTest
	public void beforeTest() {
		
	}
	
	
	@BeforeClass
	public void beforeClass() {
		startTestCase(testCaseName, testDescription);		
	}
	
	
	@BeforeMethod
	public void beforeMethod() throws FileNotFoundException, IOException {
		//for reports		
		svcTest = startTestModule(nodes);
		svcTest.assignAuthor(authors);
		svcTest.assignCategory(category);
		
		Properties prop = new Properties();
		prop.setProperty(dataFileType, dataFileName);
		prop.load(new FileInputStream(new File("./src/test/resources/config.properties")));
		
		RestAssured.baseURI = "https://"+prop.getProperty("server")+"/"+prop.getProperty("resources")+"/";
		//RestAssured.baseURI = "https://reqres.in/api";
		//RestAssured.authentication = RestAssured.basic(prop.getProperty("username").toString().trim(), prop.getProperty("password").toString().trim());

	}

	@AfterMethod
	public void afterMethod() {
		
	}
	
	@AfterClass
	public void afterClass() {
		
	}
	
	@AfterTest
	public void afterTest() {
		
	}

	@AfterSuite
	public void afterSuite() {
		endResult();
	}

	@DataProvider(name="fetchData")
	public  Object[][] getData(){
		if(dataFileType.equalsIgnoreCase("Excel"))
			return DataInputProvider.getSheet(dataFileName);	
		else if(dataFileType.equalsIgnoreCase("JSON")){
			Object[][] data = new Object[1][1];
			data[0][0] = new File("./data/"+dataFileName+"."+dataFileType);
			return data;
		}else {
			return null;
		}
			
	}

	@Override
	public long takeSnap() {
		return 0;
	}	

	
	
}
