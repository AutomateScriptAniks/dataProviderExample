package helper;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.TestData;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsEqual.equalTo;

public class OrderService extends BaseClass {

    private static final String addparcelPath = "/{orderId}/parcels";
    private static final String getparcelPath = "/{orderId}";
    private static final String confirmOrderPath = "/{orderId}/confirm";
    private static final String deleteParcelPath = "/{orderId}/parcels/{trackingBarcode}";
    private final Client client;

    public OrderService(Client client) {
        this.client = client;
    }

    public Response AfterCreatingAOrderWithRequest(String requestBody) throws JsonProcessingException {
        response =  given()
                .config(RestAssured.config().sslConfig(
                new SSLConfig().allowAllHostnames()))
                .spec(createorderuri)
                .filter(new HmacFilter(client))
                .body(requestBody)
                .contentType(ContentType.JSON)
                .when()
                .post()
                .prettyPeek();

        response
                .then()
                .statusCode(201);

        return response;

    }

    public Response AddParcelForOrderWith(String orderid) throws JsonProcessingException {

        response =  given()
                .config(RestAssured.config().sslConfig(
                        new SSLConfig().allowAllHostnames()))
                .spec(createorderuri)
                .filter(new HmacFilter(client))
                .pathParam("orderId", orderid)
                .body(new TestData().jsonAddParcelRequest())
                .contentType(ContentType.JSON)
                .when()
                .post(addparcelPath);


        response
                .then()
                .statusCode(201);

        return response;
    }

    public Response getOrderUsing(String orderid)
    {
        response =  given()
                .config(RestAssured.config().sslConfig(
                        new SSLConfig().allowAllHostnames()))
                .spec(createorderuri)
                .filter(new HmacFilter(client))
                .pathParam("orderId", orderid)
                .when()
                .get(getparcelPath)
                .prettyPeek();

        response
                .then()
                .statusCode(200);

        return response;

    }

    public void verifyOrderStatusAndParcelCount(String orderid,String status,int count) throws JsonProcessingException {

               getOrderUsing(orderid)
                .then()
                .body("orderStatus",equalTo(status))
                .body("parcels",hasSize(count));

    }

    public void confirmOrderWith(String orderid) throws JsonProcessingException {

        response = given()
                .config(RestAssured.config().sslConfig(
                        new SSLConfig().allowAllHostnames()))
                .spec(createorderuri)
                .filter(new HmacFilter(client))
                .pathParam("orderId", orderid)
                .contentType(ContentType.JSON)
                .when()
                .post(confirmOrderPath);

        response
                .then()
                .statusCode(204);
    }

    public Response deleteParcelInOrderWith(String orderid,String trackingbarcode) throws JsonProcessingException {

        response = given()
                .config(RestAssured.config().sslConfig(
                        new SSLConfig().allowAllHostnames()))
                .spec(createorderuri)
                .filter(new HmacFilter(client))
                .pathParams("orderId", orderid,"trackingBarcode",trackingbarcode)
                .contentType(ContentType.JSON)
                .when()
                .delete(deleteParcelPath)
                .prettyPeek();


        return response;

    }

    public List<String> getListOfTrackingBarCode(String orderId)
    {
        List<String> trackingBarcodes = orderService.getOrderUsing(orderId)
                .then()
                .extract()
                .path("parcels.trackingBarcode");

        return trackingBarcodes;
    }

    public String getOrderID(String locationHeader)
    {
        return locationHeader.substring(locationHeader.lastIndexOf('/') + 1);
    }
}
