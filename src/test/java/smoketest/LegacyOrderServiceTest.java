package smoketest;


import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;
import helpers.baseClass;
import org.junit.Before;
import org.junit.Test;
import utils.Constants;

import static com.jayway.restassured.RestAssured.given;
import static org.junit.Assert.assertEquals;
import static utils.JsonUtils.*;

public class LegacyOrderServiceTest extends baseClass {

    @Before
    public void setPath()
    {
        setBasePort(Constants.legacyserviceport);
        setBasePath(Constants.orderpath);
    }

    @Test
    public void verifyGetLegacyIdByOrderId()
    {
        Response res = getResponse(Constants.orderid);
        JsonPath jpath = parseResponse(res);

        assertEquals(200,res.getStatusCode());
        assertEquals("YX2DXAA3",jpath.get("parcelId"));
        assertEquals("8TAC40421920A012",jpath.get("legacyParcelId"));
        assertEquals("c1de2b44-601e-4a72-ba11-5d4495d2455a",jpath.get("orderId"));


    }

}
