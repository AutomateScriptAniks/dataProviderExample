package utils;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.http.ContentType;
import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import static com.jayway.restassured.RestAssured.given;


public  class JsonUtils {

    static {
        RestAssured.baseURI = "http://" + System.getenv().get("BASEHOST");
    }


    public static void setBasePort(String port)
    {
        RestAssured.port = Integer.valueOf(port);

    }
    public static void setBasePath(String basePath)
    {
        RestAssured.basePath = basePath;
    }

    public static Response getResponse(String basepath)
    {
        Response res = given().pathParam("id",TestData.orderid).
                when().
                get(basepath).
                then().
                contentType(ContentType.JSON).
                extract().
                response();
        return res;

    }
    public static JsonPath parseResponse(Response res)
    {
        String json = res.asString();
        JsonPath jpath = new JsonPath(json);
        return jpath;
    }

}
