package helper;


import com.fasterxml.jackson.core.JsonProcessingException;
import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import utils.TestData;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;
import static org.hamcrest.core.IsEqual.equalTo;

public class OrderService extends BaseClass {

    private static final String createOrderPath = "/orders";
    private static final String addparcelPath = "/orders/{orderId}/parcels";
    private static final String getparcelPath = "/orders/{orderId}";
    private static final String confirmOrderPath = "/orders/{orderId}/confirm";
    private static final String deleteParcelPath = "/orders/{orderId}/parcels/{trackingBarcode}";


    public OrderService(Client client) {
        this.client = client;
    }

    public Response AfterCreatingAOrderWithRequest(String requestBody, RequestSpecification uri) throws JsonProcessingException {
        response =  given()
                .config(RestAssured.config().sslConfig(
                new SSLConfig().allowAllHostnames()))
                .spec(uri)
                .filter(new HmacFilter(client))
                .body(requestBody)
                .contentType(ContentType.JSON)
                .when()
                .post(createOrderPath)
                .prettyPeek();

        response
                .then()
                .statusCode(201);

        return response;

    }

    public Response AddParcelForOrderWith(String orderid,RequestSpecification uri) throws JsonProcessingException {

        response =  given()
                .config(RestAssured.config().sslConfig(
                        new SSLConfig().allowAllHostnames()))
                .spec(uri)
                .filter(new HmacFilter(client))
                .pathParam("orderId", orderid)
                .body(new TestData().jsonAddParcelRequest())
                .contentType(ContentType.JSON)
                .when()
                .post(addparcelPath);


        return response;
    }

    public Response getOrderUsing(String orderid,RequestSpecification uri)
    {
        response =  given()
                .config(RestAssured.config().sslConfig(
                        new SSLConfig().allowAllHostnames()))
                .spec(uri)
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

    public void verifyOrderStatusAndParcelCount(String orderid,String status,int count,RequestSpecification uri) throws JsonProcessingException {

               getOrderUsing(orderid,uri)
                .then()
                .body("orderStatus",equalTo(status))
                .body("parcels",hasSize(count));

    }

    public void confirmOrderWith(String orderid,RequestSpecification uri) throws JsonProcessingException {

        response = given()
                .config(RestAssured.config().sslConfig(
                        new SSLConfig().allowAllHostnames()))
                .spec(uri)
                .filter(new HmacFilter(client))
                .pathParam("orderId", orderid)
                .contentType(ContentType.JSON)
                .when()
                .post(confirmOrderPath);

        response
                .then()
                .statusCode(204);
    }

    public Response deleteParcelInOrderWith(String orderid,String trackingbarcode,RequestSpecification uri) throws JsonProcessingException {

        response = given()
                .config(RestAssured.config().sslConfig(
                        new SSLConfig().allowAllHostnames()))
                .spec(uri)
                .filter(new HmacFilter(client))
                .pathParams("orderId", orderid,"trackingBarcode",trackingbarcode)
                .contentType(ContentType.JSON)
                .when()
                .delete(deleteParcelPath)
                .prettyPeek();


        return response;

    }


    public String getOrderID(String locationHeader)
    {
        return locationHeader.substring(locationHeader.lastIndexOf('/') + 1);
    }
}
