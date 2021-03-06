package RegressionTestSuite;
import helper.BaseClass;
import helper.DataProviders;
import helper.HmacFilter;
import helper.ScheduleService;
import io.restassured.http.ContentType;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.PropertyReader;
import utils.TestDataReaderFromCsv;


import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

public class PreOrderJourney extends BaseClass {


    @BeforeSuite
    public void setUp()
    {
        client.setId(new PropertyReader().readProperty("Order.clientId.preOrder"));
        client.setSecret(new PropertyReader().readProperty("Order.secret.preOrder"));
        scheduleService = new ScheduleService(client);
    }

    @Test(dataProvider = "dataForDeliveryOptions", dataProviderClass = DataProviders.class)
    public void verifyDeliveryOptionWithinGBUsingMuiltpleData(String clientAccount
                                                                ,String pickupLocation
                                                                ,String destinationPostcode
                                                                ,String destinationCountryCode
                                                                ,String shipmentDate
                                                                ,String expectedDeliveryDateFirstService
                                                                ,String expectedDeliveryDateSecondService)
    {
        given()
                .filter(new HmacFilter(client))
                .spec(deliveryoptionuri)
                .queryParams("clientAccount",clientAccount,
                        "pickupLocation",pickupLocation,
                        "destinationPostcode",destinationPostcode,
                        "destinationCountryCode",destinationCountryCode,
                        "shipmentDate",shipmentDate)
                .when()
                .contentType(ContentType.JSON)
                .get()
                .prettyPeek()
                .then()
                .statusCode(200)
                .body(
            "options.serviceCode", hasItems("C72P","C24P"),
            "options.find { it.serviceCode == 'C72P' }.deliverByDate", is(expectedDeliveryDateFirstService),
            "options.find { it.serviceCode == 'C24P' }.deliverByDate", is(expectedDeliveryDateSecondService));
    }



}
