package regressionTestSuit;

import com.fasterxml.jackson.core.JsonProcessingException;
import helper.BaseClass;
import helper.Client;
import helper.OrderService;
import org.junit.Before;
import org.junit.Test;

import utils.TestData;

import java.util.List;

public class ConsingmentJourney extends BaseClass {

    @Before
    public void setUp() throws Exception {
        client = new Client();
        client.setId(TestData.HTTP_CLIENT_ID);
        client.setSecret(TestData.HTTP_SECRET_KEY);
        orderService = new OrderService(client);
    }

    @Test
    public void verifyMulitpleConsginmentFunctionality() throws JsonProcessingException, InterruptedException {

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
    public void verifyMulitpleParcelSingleConsignmentFunctionality() throws JsonProcessingException, InterruptedException {
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
        String trackingBarcode = orderService.getTrackingBarcode(orderId).get(0).toString();
        orderService.deleteParcelInOrderWith(orderId,trackingBarcode);
        orderService.verifyOrderStatusAndParcelCount(orderId, "ORDER_PENDING", 1);


    }




}




