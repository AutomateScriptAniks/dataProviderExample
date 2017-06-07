package helper;

import io.restassured.http.ContentType;
import io.restassured.response.Response;
import static io.restassured.RestAssured.given;

public class TrackingService extends BaseClass {

    private static final String trackingAPIPath = "/{trackingId}";

    public Response getOrderStatus(String trackingId)
    {
        Response response = given()
                .spec(trackinguri)
                .pathParam("trackingId",trackingId)
                .when()
                .contentType(ContentType.JSON)
                .get(trackingAPIPath)
                .prettyPeek();

        return response;

    }



}
