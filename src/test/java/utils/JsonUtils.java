package utils;


import com.google.common.collect.Maps;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;


import java.util.Map;


import static io.restassured.RestAssured.given;


public class JsonUtils {


    public void setContentType(ContentType type) { given().contentType(type);}

    public JsonPath parseResponse(String resAsString) { return new JsonPath(resAsString);}

    public static Map<String, String> setMap() {
        Map<String, String> parameters = Maps.newHashMap();
        return parameters;
    }




}
