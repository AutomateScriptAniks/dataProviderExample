package smoketest;

import org.junit.BeforeClass;
import org.junit.Test;
import utils.TestData;

import static com.jayway.restassured.RestAssured.given;
import static utils.JsonUtils.setBasePath;
import static utils.JsonUtils.setBasePort;

public class CollectPlusServiceTest {

    @BeforeClass
    public static void setPath() {
        setBasePort(TestData.collectplusserviceport);
        setBasePath(TestData.storeId);
    }

    @Test
    public void shouldVerifyStoreId() {
        given().pathParam("storeId", "W20T").
                when().
                get().
                then().
                statusCode(200);
    }
}
