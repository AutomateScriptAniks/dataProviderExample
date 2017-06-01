package helper;

import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class ScheduleService extends BaseClass {

    public ScheduleService(Client client) {this.client=client;}


    public Response getDeliveryOption(Map<String,String> params)
    {
        Response response = given()
                            .filter(new HmacFilter(client))
                            .spec(deliveryoptionuri)
                            .queryParams(params)
                            .when()
                            .contentType(ContentType.JSON)
                            .get()
                            .prettyPeek();

        response
                .then()
                .statusCode(200);

        return response;

    }





}
