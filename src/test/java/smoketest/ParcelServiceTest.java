package smoketest;

import com.jayway.restassured.http.ContentType;

import org.junit.BeforeClass;

import org.junit.Test;
import utils.Constants;

import static com.jayway.restassured.RestAssured.given;
import static utils.JsonUtils.setBasePath;
import static utils.JsonUtils.setBasePort;

public class ParcelServiceTest {

    @BeforeClass
    public static void setPath()
    {
        setBasePort(Constants.parcelserviceport);
        setBasePath(Constants.parcelId);
    }

    @Test
    public void verifyGetParcelById()
   {
       given().
               parameter("id","1").
               when().
               get().
               then().
               contentType(ContentType.JSON).
               statusCode(404);

   }


}
