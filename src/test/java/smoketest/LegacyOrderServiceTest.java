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
        setBasePath(TestData.orderpath);
    }

    @Test
    public void verifyGetLegacyIdByOrderId()
    {
        Response res = getResponse(TestData.orderid);
        JsonPath jpath = parseResponse(res);

        assertEquals(200,res.getStatusCode());
        assertEquals("YX2DXAA3",jpath.get("parcelId"));
        assertEquals("8TAC40421920A012",jpath.get("legacyParcelId"));
        assertEquals("c1de2b44-601e-4a72-ba11-5d4495d2455a",jpath.get("orderId"));


    }

}
