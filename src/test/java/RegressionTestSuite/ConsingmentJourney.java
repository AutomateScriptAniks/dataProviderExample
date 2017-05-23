package RegressionTestSuite;

import com.fasterxml.jackson.core.JsonProcessingException;
import helper.BaseClass;
import helper.Client;
import helper.OrderService;

import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utils.PropertyReader;
import utils.TestData;
import java.util.List;
import static org.hamcrest.Matchers.contains;
import static org.hamcrest.Matchers.not;
import static org.junit.Assert.assertThat;

public class ConsingmentJourney extends BaseClass {

    @BeforeSuite
    public void setUp() throws Exception {

        client.setId(new PropertyReader().readProperty("Order.clientId.yellow"));
        client.setSecret(new PropertyReader().readProperty("Order.secret.yellow"));
        orderService = new OrderService(client);
    }

    @Test
    public void verifyMulitpleConsginmentUsingGBAddress() throws JsonProcessingException, InterruptedException {

        for (int i = 1;i <= 3;i++) {
            String fromLocationHeader = orderService.AfterCreatingAOrderWithRequest(new TestData().jsonYellowRequest())
                    .then()
                    .extract()
                    .header("location");
            String orderId = orderService.getOrderID(fromLocationHeader);
            orderService.verifyOrderStatusAndParcelCount(orderId, "ORDER_PENDING", 0);
            orderService.AddParcelForOrderWith(orderId);
            orderService.verifyOrderStatusAndParcelCount(orderId, "ORDER_PENDING", 1);
            orderService.confirmOrderWith(orderId);
            orderService.verifyOrderStatusAndParcelCount(orderId, "ORDER_ADVISED", 1);
        }

    }

    @Test
    public void verifyConfirmMulitpleParcelSingleConsignmentUsingGBAddress() throws JsonProcessingException, InterruptedException {
        String fromLocationHeader = orderService.AfterCreatingAOrderWithRequest(new TestData().jsonYellowRequest())
                        .then()
                        .extract()
                        .header("location");
        String orderId = orderService.getOrderID(fromLocationHeader);
        orderService.verifyOrderStatusAndParcelCount(orderId, "ORDER_PENDING", 0);
        for (int i=0;i<=2;i++)
        {orderService.AddParcelForOrderWith(orderId);}
        orderService.verifyOrderStatusAndParcelCount(orderId, "ORDER_PENDING", 3);
        orderService.confirmOrderWith(orderId);
        orderService.verifyOrderStatusAndParcelCount(orderId, "ORDER_ADVISED", 3);
    }

    @Test
    public void verifyDeleteParcelSingleConsignmentFunctionality() throws JsonProcessingException, InterruptedException {
        String fromLocationHeader = orderService
                .AfterCreatingAOrderWithRequest(new TestData().jsonYellowRequest())
                .then()
                .extract()
                .header("location");
        String orderId = orderService.getOrderID(fromLocationHeader);
        orderService.verifyOrderStatusAndParcelCount(orderId, "ORDER_PENDING", 0);
        for (int i=0;i<2;i++)
        {orderService.AddParcelForOrderWith(orderId);}
        orderService.verifyOrderStatusAndParcelCount(orderId, "ORDER_PENDING", 2);
        List<String> trackingBarcodes = orderService.getOrderUsing(orderId)
                .then()
                .extract()
                .path("parcels.trackingBarcode");
        String deleteToBeTrackingBarcode = trackingBarcodes.get(0);
        orderService.deleteParcelInOrderWith(orderId,deleteToBeTrackingBarcode).then().statusCode(204);
        orderService.verifyOrderStatusAndParcelCount(orderId, "ORDER_PENDING", 1);
        assertThat(trackingBarcodes,not(contains(deleteToBeTrackingBarcode)));
        orderService.confirmOrderWith(orderId);
        orderService.deleteParcelInOrderWith(orderId,trackingBarcodes.get(1)).then().statusCode(400);
    }


}



