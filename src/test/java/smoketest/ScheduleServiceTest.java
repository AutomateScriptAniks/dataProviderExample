package smoketest;
import com.jayway.restassured.http.ContentType;

import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;
import utils.Constants;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.core.IsEqual.equalTo;
import static utils.JsonUtils.*;

public class ScheduleServiceTest  {

    @BeforeTest
    public void setPath()
    {
        setBasePort(Constants.scheduleserviceport);
        setBasePath(Constants.deliverydate);
    }

   @Test
    public void verifyGetDeliverByDate()
   {
       given().
               parameters("shippingDate","2017-12-09",
                       "serviceCode","1CP",
                       "destinationPostcode","M37EH").
               when().
               get().
               then().
               contentType(ContentType.JSON).
                body("deliveryDate",equalTo("2017-12-11")).
               statusCode(200);

   }


}
