package smoketest;


import com.jayway.restassured.path.json.JsonPath;
import com.jayway.restassured.response.Response;

import helper.Setup;
import org.junit.BeforeClass;
import org.junit.Test;

import utils.TestData;

import static com.jayway.restassured.RestAssured.given;

import static org.junit.Assert.assertEquals;
import static utils.JsonUtils.*;

public class OrderServiceTest  {

    @BeforeClass
    public static void setPath()
    {
        setBasePort(TestData.orderserviceport);
        Setup.settingOrderData();
        //setBasePath(TestData.orderpath);
    }

    @Test
    public void verifyGetParcelIdById()
    {
        Response res = getResponse(TestData.orderpath);
        JsonPath jpath = parseResponse(res);

        assertEquals(200,res.getStatusCode());
        //assertEquals("D3OVE3PL",jpath.get("parcelId"));
        //assertEquals("2018-03-01",jpath.get("deliveryDate"));
        //assertEquals("02bc59bf-53bf-4dd5-8dc3-72ec97335db3",jpath.get("orderId"));


    }

}
