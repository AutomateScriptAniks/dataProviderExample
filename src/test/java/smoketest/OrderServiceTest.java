package smoketest;


import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import org.junit.BeforeClass;
import org.junit.Test;
import org.testng.annotations.BeforeTest;

import utils.Constants;

import static com.jayway.restassured.RestAssured.given;

import static org.junit.Assert.assertEquals;
import static utils.JsonUtils.*;

public class OrderServiceTest  {

    @BeforeClass
    public static void setPath()
    {
        setBasePort(Constants.orderserviceport);
        setBasePath(Constants.orderpath);
    }

    @Test
    public void verifyGetParcelIdById()
    {
        Response res = getResponse(Constants.orderid);
        JsonPath jpath = parseResponse(res);

        assertEquals(200,res.getStatusCode());
        assertEquals("YX2DXAA3",jpath.get("parcelId"));
        assertEquals("2017-12-23",jpath.get("deliveryDate"));
        assertEquals("c1de2b44-601e-4a72-ba11-5d4495d2455a",jpath.get("orderId"));


    }

}
