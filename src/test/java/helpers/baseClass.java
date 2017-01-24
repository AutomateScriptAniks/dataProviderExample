package helpers;

import com.jayway.restassured.RestAssured;

import org.junit.BeforeClass;

 public class baseClass {

    @BeforeClass
    public static void setup() {

        /*
        String baseClass = System.getProperty("server.base");
        if(baseClass==null){
            baseClass = "/rest-garage-sample/";
        }
        RestAssured.baseClass = baseClass;
        */

        RestAssured.baseURI = "http://" + System.getenv().get("BASEHOST");
    }
}
