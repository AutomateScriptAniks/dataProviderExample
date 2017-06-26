package RegressionTestSuite;

import com.fasterxml.jackson.core.JsonProcessingException;
import helper.BaseClass;
import helper.DataProviders;
import helper.OrderService;
import helper.TrackingService;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utils.PropertyReader;
import utils.TestData;

import java.util.List;

import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class ConsingmentJourneyForCollectPlus extends BaseClass {

    private static final String expectedEventCode = "1";
    private static final String expectedEventDescription = "Parcel data received awaiting coll.";

    @BeforeSuite
    public void setUp() throws Exception {

        client.setId(new PropertyReader().readProperty("Order.clientId.collectPlus"));
        client.setSecret(new PropertyReader().readProperty("Order.secret.collectPlus"));
        orderService = new OrderService(client);
        trackingService = new TrackingService();
    }

    @Test(dataProvider="endpointForConsignmentJourney",dataProviderClass = DataProviders.class)
    public void verifyCollectPlusOrderJourney(String uri) throws JsonProcessingException, InterruptedException {

            String fromLocationHeader = orderService
                    .AfterCreatingAOrderWithRequest(new TestData().jsonCollectPlusRequest(),uriSpec(uri))
                    .then()
                    .extract()
                    .header("location");
            String orderId = orderService.getOrderID(fromLocationHeader);
            orderService.verifyOrderStatusAndParcelCount(orderId, "ORDER_PENDING", 0,uriSpec(uri));
            orderService.AddParcelForOrderWith(orderId,uriSpec(uri));
            orderService.verifyOrderStatusAndParcelCount(orderId, "ORDER_PENDING", 1,uriSpec(uri));
            orderService.AddParcelForOrderWith(orderId,uriSpec(uri)).then().statusCode(400);
            List<String> trackingBarcodes = orderService.getOrderUsing(orderId,uriSpec(uri))
                    .then()
                    .extract()
                    .path("parcels.trackingBarcode");
            String deleteToBeTrackingBarcode = trackingBarcodes.get(0);
            orderService.deleteParcelInOrderWith(orderId, deleteToBeTrackingBarcode,uriSpec(uri)).then().statusCode(204);
            orderService.verifyOrderStatusAndParcelCount(orderId, "ORDER_PENDING", 0,uriSpec(uri));
            orderService.AddParcelForOrderWith(orderId,uriSpec(uri));
            orderService.confirmOrderWith(orderId,uriSpec(uri));
            orderService.deleteParcelInOrderWith(orderId, trackingBarcodes.get(0),uriSpec(uri)).then().statusCode(400);

        }
}






