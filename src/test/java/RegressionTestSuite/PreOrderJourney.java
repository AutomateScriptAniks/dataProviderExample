package RegressionTestSuite;

import helper.BaseClass;
import helper.ScheduleService;
import org.testng.annotations.BeforeSuite;
import org.testng.annotations.Test;
import utils.PropertyReader;

import java.util.List;

import static org.hamcrest.Matchers.hasItems;
import static org.hamcrest.Matchers.is;

public class PreOrderJourney extends BaseClass {


    @BeforeSuite
    public void setUp()
    {
        client.setId(new PropertyReader().readProperty("Order.clientId.yellow"));
        client.setSecret(new PropertyReader().readProperty("Order.secret.yellow"));
        scheduleService = new ScheduleService(client);
        params.put("clientAccount","100004");
        params.put("pickupLocation","TADLEY");
        params.put("destinationPostcode","M3 7EH");
        params.put("destinationCountryCode","GB");
        params.put("shipmentDate","2018-05-23");
    }

    @Test
    public void verifyDeliveryOptionWithinGBAddresses()
    {
    scheduleService.getDeliveryOption(params)
            .then()
            .body(
                    "options.serviceCode", hasItems("C72P","C24P","1VX"),
                    "options.find { it.serviceCode == 'C72P' }.deliverByDate", is("2018-05-26"),
                    "options.find { it.serviceCode == 'C24P' }.deliverByDate", is("2018-05-24"),
                    "options.find { it.serviceCode == '1VX' }.deliverByDate", is("2018-05-24"),
                    "options.find { it.serviceCode == 'C72P'}.serviceDescription",
                                is("Collect+ to store 72hr POD"),
                    "options.find { it.serviceCode == 'C24P'}.serviceDescription",
                                is("Collect+ delivery to store on next possible working day throughout the UK"),
                    "options.find { it.serviceCode == '1VX'}.serviceDescription",
                                is("Next day Xpert exchange service with POD"));
    }






}
