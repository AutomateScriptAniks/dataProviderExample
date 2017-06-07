package RegressionTestSuite;

import com.fasterxml.jackson.core.JsonProcessingException;
import helper.BaseClass;
import helper.OrderService;

import helper.TrackingService;
import org.awaitility.Duration;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utils.PropertyReader;
import utils.TestData;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.TimeUnit;

import static org.awaitility.Awaitility.await;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;


public class ConsingmentJourney extends BaseClass {

    //private static final String trackingId = "JD0002216000150047";

    @BeforeSuite
    public void setUp() throws Exception {

        client.setId(new PropertyReader().readProperty("Order.clientId.yellow"));
        client.setSecret(new PropertyReader().readProperty("Order.secret.yellow"));
        orderService = new OrderService(client);
        trackingService = new TrackingService();
    }

    @Test
    public void verifyDeleteParcelMultipleConsignmentFunctionality() throws JsonProcessingException, InterruptedException {
        for (int i = 1; i <= 3; i++) {
            String fromLocationHeader = orderService
                    .AfterCreatingAOrderWithRequest(new TestData().jsonYellowRequest())
                    .then()
                    .extract()
                    .header("location");
            String orderId = orderService.getOrderID(fromLocationHeader);
            orderService.verifyOrderStatusAndParcelCount(orderId, "ORDER_PENDING", 0);
            for (int j = 0; j < 2; j++) {
                orderService.AddParcelForOrderWith(orderId);
            }
            orderService.verifyOrderStatusAndParcelCount(orderId, "ORDER_PENDING", 2);
            List<String> trackingBarcodes = orderService.getOrderUsing(orderId)
                    .then()
                    .extract()
                    .path("parcels.trackingBarcode");
            String deleteToBeTrackingBarcode = trackingBarcodes.get(0);
            orderService.deleteParcelInOrderWith(orderId, deleteToBeTrackingBarcode).then().statusCode(204);
            orderService.verifyOrderStatusAndParcelCount(orderId, "ORDER_PENDING", 1);
            assertThat(trackingBarcodes, not(contains(deleteToBeTrackingBarcode)));
            orderService.confirmOrderWith(orderId);
            orderService.deleteParcelInOrderWith(orderId, trackingBarcodes.get(1)).then().statusCode(400);
            trackingService.getOrderStatus(trackingBarcodes.get(1));

        }
    }

    @Test
    public void verifyTrackingAPIFunctionality() throws JsonProcessingException, InterruptedException {
            String fromLocationHeader = orderService
                    .AfterCreatingAOrderWithRequest(new TestData().jsonYellowRequest())
                    .then()
                    .extract()
                    .header("location");
            String orderId = orderService.getOrderID(fromLocationHeader);
            orderService.verifyOrderStatusAndParcelCount(orderId, "ORDER_PENDING", 0);
            orderService.AddParcelForOrderWith(orderId);
            orderService.verifyOrderStatusAndParcelCount(orderId, "ORDER_PENDING", 1);
            List<String> trackingBarcodes = orderService.getOrderUsing(orderId)
                    .then()
                    .extract()
                    .path("parcels.trackingBarcode");
            orderService.verifyOrderStatusAndParcelCount(orderId, "ORDER_PENDING", 1);
            orderService.confirmOrderWith(orderId);
            orderService.deleteParcelInOrderWith(orderId, trackingBarcodes.get(0)).then().statusCode(400);
            await().atMost(Duration.FIVE_MINUTES)
                        .pollDelay(new Duration(120, TimeUnit.SECONDS))
                        .until(() -> trackingService
                                .getOrderStatus(trackingBarcodes.get(0)).thenReturn().statusCode() == 200);
        }

}








