package helper;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import utils.PropertyReader;

import static io.restassured.RestAssured.given;

public class TrackingService extends BaseClass {

    private static final String trackingAPIPath = "/{trackingId}";


    public Response getOrderStatus(String trackingId)
    {
        tokenReader = new OauthTokenReader();
        String token = tokenReader.getToken(
                new PropertyReader().readProperty("Order.clientId.tracking"),
                new PropertyReader().readProperty("Order.secret.tracking"));
        Response response = given()
                .config(RestAssured.config().sslConfig(
                        new SSLConfig().allowAllHostnames()))
                .spec(trackinguri)
                .auth().preemptive().oauth2(token)
                .pathParam("trackingId",trackingId)
                .when()
                .contentType(ContentType.JSON)
                .get(trackingAPIPath)
                .prettyPeek();

        return response;

    }



}
