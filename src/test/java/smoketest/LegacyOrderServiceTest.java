package smoketest;


import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import org.junit.BeforeClass;

import org.junit.Test;
import utils.TestData;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static utils.JsonUtils.*;

public class LegacyOrderServiceTest {

    @BeforeClass
    public static void setPath()
    {
        setBasePort(TestData.legacyserviceport);
        //setBasePath(TestData.orderpath);
    }

    @Test
    public void verifyGetLegacyIdByOrderId()
    {
        Response res = getResponse(TestData.orderpath);
        JsonPath jpath = parseResponse(res);

        assertEquals(200,res.getStatusCode());
        //assertEquals("I7ECZWS7",jpath.get("parcelId"));
       // assertEquals("8TMJ81613368A002",jpath.get("legacyParcelId"));
        //assertEquals("4e604991-9575-4e24-8918-ccb1c3990b94",jpath.get("orderId"));


    }

}
