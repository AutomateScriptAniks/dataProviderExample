package RegressionTestSuite;

import com.fasterxml.jackson.core.JsonProcessingException;
import helper.*;

import org.awaitility.Duration;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import utils.PropertyReader;
import utils.TestData;

import java.net.URI;
import java.util.List;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.*;

import static org.junit.Assert.assertThat;

public class ConsingmentJourneyForNonCollectPlus extends BaseClass {

    private static final String expectedEventCode = "1";
    private static final String expectedEventDescription = "Parcel data received awaiting coll.";
    private static final String trackingBarcode = "JD0002216135093359";

    @BeforeSuite
    public void setUp() throws Exception {

        client.setId(new PropertyReader().readProperty("Order.clientId.yellow"));
        client.setSecret(new PropertyReader().readProperty("Order.secret.yellow"));
        orderService = new OrderService(client);
        trackingService = new TrackingService();


    }

    @Test(dataProvider="endpointForConsignmentJourney",dataProviderClass = DataProviders.class)
    public void verifyMultipleConsignmentJourneyUsingGBAddress(String uri) throws JsonProcessingException, InterruptedException {


        for (int i = 1; i <= 2; i++) {
            String fromLocationHeader = orderService
                    .AfterCreatingAOrderWithRequest(new TestData().jsonYellowRequest(),uriSpec(uri))
                    .then()
                    .extract()
                    .header("location");
            String orderId = orderService.getOrderID(fromLocationHeader);
            orderService.verifyOrderStatusAndParcelCount(orderId, "ORDER_PENDING", 0,uriSpec(uri));
            for (int j = 0; j < 2; j++) {
                orderService.AddParcelForOrderWith(orderId,uriSpec(uri));
            }
            orderService.verifyOrderStatusAndParcelCount(orderId, "ORDER_PENDING", 2,uriSpec(uri));
            List<String> trackingBarcodes = orderService.getOrderUsing(orderId,uriSpec(uri))
                    .then()
                    .extract()
                    .path("parcels.trackingBarcode");
            String deleteToBeTrackingBarcode = trackingBarcodes.get(0);
            orderService.deleteParcelInOrderWith(orderId, deleteToBeTrackingBarcode,uriSpec(uri)).then().statusCode(204);
            orderService.verifyOrderStatusAndParcelCount(orderId, "ORDER_PENDING", 1,uriSpec(uri));
            assertThat(trackingBarcodes, not(contains(deleteToBeTrackingBarcode)));
            orderService.confirmOrderWith(orderId,uriSpec(uri));
            orderService.deleteParcelInOrderWith(orderId, trackingBarcodes.get(1),uriSpec(uri)).then().statusCode(400);
            await().atMost(Duration.FIVE_MINUTES)
                    .pollDelay(new Duration(180, TimeUnit.SECONDS))
                    .until(() -> trackingService
                            .getOrderStatus(trackingBarcodes.get(1)).thenReturn().statusCode() == 200);
            trackingService.getOrderStatus(trackingBarcodes.get(1))
                    .then()
                    .body("trackingEvents.eventCode",contains(expectedEventCode))
                    .and()
                    .body("trackingEvents.eventDescription",contains(expectedEventDescription));

        }
    }

    @Test(dataProvider="endpointForConsignmentJourney",dataProviderClass = DataProviders.class)
    public void verifyDeleteAndConfirmUsingIEAddress(String uri) throws JsonProcessingException, InterruptedException {
            String fromLocationHeader = orderService.AfterCreatingAOrderWithRequest(new TestData().jsonYellowRequestForIreland(),uriSpec(uri))
                    .then()
                    .extract()
                    .header("location");
            String orderId = orderService.getOrderID(fromLocationHeader);
            orderService.verifyOrderStatusAndParcelCount(orderId, "ORDER_PENDING", 0,uriSpec(uri));
            orderService.AddParcelForOrderWith(orderId,uriSpec(uri));
            orderService.verifyOrderStatusAndParcelCount(orderId, "ORDER_PENDING", 1,uriSpec(uri));
            List<String> trackingBarcodes = orderService.getOrderUsing(orderId,uriSpec(uri))
                    .then()
                    .extract()
                    .path("parcels.trackingBarcode");
            orderService.confirmOrderWith(orderId,uriSpec(uri));
            orderService.verifyOrderStatusAndParcelCount(orderId, "ORDER_ADVISED", 1,uriSpec(uri));
            orderService.deleteParcelInOrderWith(orderId,trackingBarcodes.get(0),uriSpec(uri)).then().statusCode(400);
        }

}








