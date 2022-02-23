package com.apiBase;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.apiAnnotations.PreAndPost;
import com.github.wnameless.json.flattener.JsonFlattener;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;
import net.minidev.json.JSONObject;

public class ApiBase extends PreAndPost{



	public static RequestSpecification setLogs() {
		return RestAssured
				.given()
				.log()
				.all()
				.contentType(getContentType());
	}

	public static Response get(String URL) {
		Response response = setLogs().when().get(URL);
		reportRequest("<pre>"+response.prettyPrint()+"</pre>", "");
		response.then().log().all();
		return response;
	}


	public static Response get() {
		Response response = setLogs()
		.get();
		reportRequest("<pre>"+response.prettyPrint()+"</pre>", "");
		response.then().log().all();
		return response;
	}

	public static Response getWithHeader(Map<String, String> headers, String URL) {
		Response response = setLogs()
		.when()
		.headers(headers)
		.get(URL);
		reportRequest("<pre>"+response.prettyPrint()+"</pre>", "");
		response.then().log().all();
		return response;
	}

	public static Response post() {
		Response post = setLogs()
		.post();
		reportRequest("<pre>"+post.prettyPrint()+"</pre>", "");
		post.then().log().all();
		return post;
	}

	public static Response post(String URL) {
		Response post = setLogs()
		.post(URL);
		reportRequest("<pre>"+post.prettyPrint()+"</pre>", "");
		post.then().log().all();
		return post;
	}

	public static Response postWithBodyAsFile(File bodyFile) {
		Response post = setLogs()
		.body(bodyFile)
		.post();
		reportRequest("<pre>"+post.prettyPrint()+"</pre>", "");
		post.then().log().all();
		return post;
	}
	
	public static Response postWithBodyAsFileAndUrl(File bodyFile, String URL) {
		Response post = setLogs()
		.body(bodyFile)
		.post(URL);
		reportRequest("<pre>"+post.prettyPrint()+"</pre>", "");
		post.then().log().all();
		return post;
	}
	
	public static Response postWithHeaderAndForm(Map<String, String> headers,
			JSONObject jsonObject, String URL) {
		Response post = setLogs()
		.headers(headers)
		.body(jsonObject)
		.post(URL);
		reportRequest("<pre>"+post.prettyPrint()+"</pre>", "");
		post.then().log().all();
		return post;
	}

	public static Response postWithJsonAsBody(String jsonObject, String URL) {
		Response post = setLogs()
		.body(jsonObject)
		.post(URL);
		reportRequest("<pre>"+post.prettyPrint()+"</pre>", "");
		post.then().log().all();
		return post;
	}


	public static Response postWithHeaderAndJsonBody(Map<String, String> headers,
			String jsonObject, String URL) {
		Response post = setLogs()
		.when()
		.headers(headers)
		.body(jsonObject)
		.post(URL);
		reportRequest("<pre>"+post.prettyPrint()+"</pre>", "");
		post.then().log().all();
		return post;
	}


	public static Response postWithHeaderParam(Map<String, String> headers, String URL) {
		Response post = setLogs()
		.when()
		.headers(headers)
		.post(URL);
		reportRequest("<pre>"+post.prettyPrint()+"</pre>", "");
		post.then().log().all();
		return post;
	}
	
	public static Response delete(String URL) {
		Response delete = setLogs()
		.when()
		.delete(URL);
		reportRequest("<pre>"+delete.prettyPrint()+"</pre>", "");
		delete.then().log().all();
		return delete;
	}

	public static Response deleteWithHeaderAndPathParam(Map<String, String> headers,
			JSONObject jsonObject, String URL) {
		Response delete = setLogs()
		.when()
		.headers(headers)
		.body(jsonObject)
		.delete(URL);
		reportRequest("<pre>"+delete.prettyPrint()+"</pre>", "");
		delete.then().log().all();
		return delete;
	}

	public static Response deleteWithHeaderAndPathParamWithoutRequestBody(
			Map<String, String> headers, String URL) throws Exception {
		Response delete = setLogs()
		.when()
		.headers(headers)
		.delete(URL);
		reportRequest("<pre>"+delete.prettyPrint()+"</pre>", "");
		delete.then().log().all();
		return delete;
	}

	public static Response putWithHeaderAndBodyParam(Map<String, String> headers,
			JSONObject jsonObject, String URL) {
		Response put = RestAssured.given().headers(headers).contentType(getContentType()).request()
		.body(jsonObject).when().put(URL);
		reportRequest("<pre>"+put.prettyPrint()+"</pre>", "");
		put.then().log().all();
		return put;
	}
	
	public static ValidatableResponse enableResponseLog(Response response) {
		return response.then().log().all();
	}

	private static ContentType getContentType() {
		return ContentType.JSON;
	}

	public static void verifyContentType(Response response, String type) {
		if(response.getContentType().toLowerCase().contains(type.toLowerCase())) {
			reportRequest("The Content type "+type+" matches the expected content type", "PASS");
		}else {
			reportRequest("The Content type "+type+" does not match the expected content type "+response.getContentType(), "FAIL");	
		}
	}

	public static void verifyResponseCode(Response response, int code) {
		if(response.statusCode() == code) {
			reportRequest("The status code "+code+" matches the expected code", "PASS");
		}else {
			reportRequest("The status code "+code+" does not match the expected code"+response.statusCode(), "FAIL");	
		}
	}
	
	public static void verifyResponseTime(Response response, long time) {
		if(response.time() <= time) {
			reportRequest("The time taken "+response.time() +" with in the expected time", "PASS");
		}else {
			reportRequest("The time taken "+response.time() +" is greater than expected SLA time "+time,"WARNING");		
		}
	}

	public static void verifyContentWithKey(Response response, String key, String expVal) {
		if(response.getContentType().contains("json")) {
			JsonPath jsonPath = response.jsonPath();
			String actValue = jsonPath.get(key);
			if(actValue.equalsIgnoreCase(expVal)) {
				reportRequest("The JSON response has value "+expVal+" as expected. ", "PASS");
			}else {
				reportRequest("The JSON response :"+actValue+" does not have the value "+expVal+" as expected. ", "FAIL");		
			}
		}
	}
	
	public static void verifyContentsWithKey(Response response, String key, String expVal) {
		if(response.getContentType().contains("json")) {
			JsonPath jsonPath = response.jsonPath();
			List<String> actValue = jsonPath.getList(key);
			if(actValue.get(0).equalsIgnoreCase(expVal)) {
				reportRequest("The JSON response has value "+expVal+" as expected. ", "PASS");
			}else {
				reportRequest("The JSON response :"+actValue+" does not have the value "+expVal+" as expected. ", "FAIL");		
			}
		}
	}
	
	public static List<String> getContentsWithKey(Response response, String key) {
		if(response.getContentType().contains("json")) {
			JsonPath jsonPath = response.jsonPath();
			return jsonPath.getList(key);			
		}else {
			return null;
		}
	}
	
	public static String getContentWithKey(Response response, String key) {
		if(response.getContentType().contains("json")) {
			JsonPath jsonPath = response.jsonPath();
			return (String) jsonPath.get(key);			
		}else {
			return null;
		}
	}
	
	 /**
     * This method takes a response and json path and returns the value from the the
     * given response
     *
     * @param jsonPath the path of the value being returned e.g person.name
     * @return an object
     */

    public Object getParameterValue(Response response, String jsonPath) {
        Object jsonObject = response.jsonPath().get(jsonPath);
        return jsonObject;
    }

    /**
     * This method takes a response, parameter name and index and will return the
     * json path for e.g person.name from the given response
     *
     * @param parameterName the parameterName string e.g name
     * @param index         the index of the parameter that should be returned e.g
     *                      {persons: [{"id": 1, "name": "Bill"} , {"id": 2, "name":
     *                      "Rick"}]} index 1 will return persons[1].name
     * @return a string
     */

    public String getJsonPathOfParameter(Response response, String parameterName, int index) {
        List<String> listPath = new ArrayList<String>();
        String paths = null;
        String flatten = JsonFlattener.flatten(response.asString());
        String[] split = flatten.split(",");
        for (String string : split) {
            String checkName = "";
            if (string.split(":")[0].contains(".")) {

                String[] split2 = string.split(":");
                String checkSplit = split2[0].toString().replaceAll("\"", "");
                String[] paraName = checkSplit.split("[.]");
                checkName = paraName[paraName.length - 1];
                if (checkName.contains("{")) {
                    checkName = checkName.replace("{", "");
                } else if (checkName.contains("}")) {
                    checkName = checkName.replace("}", "");
                }

            } else {
                String[] split2 = string.split(":");
                String checkSplit = split2[0].toString().replaceAll("\"", "");
                checkName = checkSplit;
                if (checkName.contains("{")) {
                    checkName = checkName.replace("{", "");
                } else if (checkName.contains("}")) {
                    checkName = checkName.replace("}", "");
                }
            }
            if (string.contains(parameterName) && checkName.equals(parameterName)) {
                String[] split2 = string.split(":");
                paths = split2[0].toString().replaceAll("\"", "");
                listPath.add(paths);
            }
        }
        String paramPath = listPath.get(index);
        if (paramPath.contains("{")) {
            paramPath = paramPath.replace("{", "");
        } else if (paramPath.contains("}")) {
            paramPath = paramPath.replace("}", "");
        }
        return paramPath;
    }
    
    /**
     * This method takes a response, parameter name and index and will return the
     * json path for e.g person.name from the given response
     *
     * @param parameterName the parameterName string e.g name
     * @param index         the index of the parameter that should be returned e.g
     *                      {persons: [{"id": 1, "name": "Bill"} , {"id": 2, "name":
     *                      "Rick"}]} index 1 will return persons[1].name
     * @return a string
     */
    public String getJsonPathOfParameter(String responseOrRequest, String parameterName, int index) {
        List<String> listPath = new ArrayList<String>();
        String paths = null;
        String flatten = JsonFlattener.flatten(responseOrRequest);
        String[] split = flatten.split(",");
        for (String string : split) {
            String checkName = "";
            if (string.split(":")[0].contains(".")) {

                String[] split2 = string.split(":");
                String checkSplit = split2[0].toString().replaceAll("\"", "");
                String[] paraName = checkSplit.split("[.]");
                checkName = paraName[paraName.length - 1];
                if (checkName.contains("{")) {
                    checkName = checkName.replace("{", "");
                } else if (checkName.contains("}")) {
                    checkName = checkName.replace("}", "");
                }

            } else {
                String[] split2 = string.split(":");
                String checkSplit = split2[0].toString().replaceAll("\"", "");
                checkName = checkSplit;
                if (checkName.contains("{")) {
                    checkName = checkName.replace("{", "");
                } else if (checkName.contains("}")) {
                    checkName = checkName.replace("}", "");
                }
            }
            if (string.contains(parameterName) && checkName.equals(parameterName)) {
                String[] split2 = string.split(":");
                paths = split2[0].toString().replaceAll("\"", "");
                listPath.add(paths);
            }
        }
        String paramPath = listPath.get(index);
        if (paramPath.contains("{")) {
            paramPath = paramPath.replace("{", "");
        } else if (paramPath.contains("}")) {
            paramPath = paramPath.replace("}", "");
        }
        return paramPath;
    }

    
    public Object getParameterValueUsingParameterName(Response response, String parameterName, int index) {
        String wsIGetJsonPathOfParameter = getJsonPathOfParameter(response, parameterName, index);
        Object wsIGetParameterValue = getParameterValue(response,wsIGetJsonPathOfParameter);
        return wsIGetParameterValue;
    }
}
