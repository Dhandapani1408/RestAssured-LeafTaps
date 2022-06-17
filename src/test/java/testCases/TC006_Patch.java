package testCases;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import com.apiBase.ApiBase;

public class TC006_Patch  extends ApiBase{
	@BeforeTest
	public void setValues() {
		testCaseName = "Patch Existing User (REST)";
		testDescription = "Patch single property of User and Print the updated property";
		nodes = "User";
		authors = "Dhandapani";
		category = "API";
		dataFileName = "TC001";
		dataFileType = "json";
	}

	@Test()
	public void patchUser() {
		
	}

}
