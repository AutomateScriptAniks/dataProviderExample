package RegressionTestSuite;

import com.fasterxml.jackson.core.JsonProcessingException;
import helper.BaseClass;
import helper.OrderService;


import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utils.PropertyReader;
import utils.TestData;


public class ConsignmentJourneyIreland extends BaseClass {

    @BeforeSuite
    public void setUp() throws Exception {
        client.setId(new PropertyReader().readProperty("Order.clientId.yellowIR"));
        client.setSecret(new PropertyReader().readProperty("Order.secret.yellowIR"));
        orderService = new OrderService(client);
    }

    @Test
    public void verifyMulitpleConsginmentDeleteAndConfirmUsingIEAddress() throws JsonProcessingException, InterruptedException {
        for (int i = 1;i <= 3;i++) {
            String fromLocationHeader = orderService.AfterCreatingAOrderWithRequest(new TestData().jsonYellowRequestForIreland())
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
}
