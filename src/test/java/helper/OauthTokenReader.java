package helper;

import io.restassured.RestAssured;
import io.restassured.config.SSLConfig;
import io.restassured.http.ContentType;

import static io.restassured.RestAssured.given;

public class OauthTokenReader extends BaseClass {

    /*
    public OauthTokenReader(Client client)
    {
        this.client=client;
    }
*/

    public String getToken(String clientId, String clientSecret) {
        return given()
                .config(RestAssured.config().sslConfig(new SSLConfig().allowAllHostnames()))
                .spec(outhtokenbuilder)
                .accept(ContentType.JSON)
                .formParam("grant_type","client_credentials")
                .auth().preemptive().basic(clientId, clientSecret)
                .when()
                .post()
                .then()
                .statusCode(200)
                .extract()
                .path("access_token");
    }
}
