package utils;

import com.beust.jcommander.internal.Maps;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TestData {

    ObjectMapper mapper = new ObjectMapper();

    public String jsonCollectPlusRequest() throws JsonProcessingException {

        Map<String, Object> json = Maps.newHashMap();
        json.put("brand","House of Fraser");
        json.put("clientAccount","100001");
        json.put("customerReference","My Parcel");
        json.put("service","C24P");
        json.put("shipmentDate","2018-03-22");
        json.put("pickupLocation","stockport");

        Map<String,Object> to = Maps.newHashMap();
        to.put("customerContactNumber","07448622464");
        to.put("customerEmail","string@string.com");
        to.put("customerName","Seeburger-To-Fusion");
        to.put("customerContactStrategy","ES");
        to.put("emailNotification",true);
        to.put("phoneNotification",true);
        to.put("storeCode","W20C");
        json.put("to",to);

        return mapper.writeValueAsString(json);

    }

    public String jsonYellowRequest() throws JsonProcessingException {

        Map<String, Object> json = Maps.newHashMap();
        json.put("brand","House of Fraser");
        json.put("clientAccount","100021");
        json.put("customerReference","My Parcel");
        json.put("service","12A");
        json.put("shipmentDate","2018-07-10");
        json.put("pickupLocation","LIVERPool");

        Map<String,Object> to = Maps.newHashMap();

        Map<String,Object> address = Maps.newHashMap();
        address.put("addressLine1","Apartment 25, SpringField Court");
        address.put("countryCode","GB");
        address.put("postcode","M3 7EH");
        address.put("organisationName","Yodel");
        address.put("departmentName","OptionalButImp");
        address.put("town","Manchester");

        to.put("address",address);
        to.put("customerContactNumber","07448622464");
        to.put("customerEmail","string@string.com");
        to.put("customerName","AnikWithServiceName");
        to.put("emailNotification",true);
        to.put("phoneNotification",true);
        json.put("to",to);

        return mapper.writeValueAsString(json);

    }

    public String jsonYellowRequestForIreland() throws JsonProcessingException {

        Map<String, Object> irjson = Maps.newHashMap();
        irjson.put("brand","House of Fraser");
        irjson.put("clientAccount","100021");
        irjson.put("customerReference","My Parcel");
        irjson.put("service","1EEN");
        irjson.put("shipmentDate","2018-06-10");
        irjson.put("pickupLocation","LIVERPOOL");

        Map<String,Object> irto = Maps.newHashMap();

        Map<String,Object> iraddress = Maps.newHashMap();
        iraddress.put("addressLine1","Dublin");
        iraddress.put("countryCode","IE");
        iraddress.put("postcode","D02 AF30");
        iraddress.put("organisationName",null);
        iraddress.put("departmentName","OptionalButImp");
        iraddress.put("town","Dublin");

        irto.put("address",iraddress);
        irto.put("customerContactNumber","07448622464");
        irto.put("customerEmail","string@string.com");
        irto.put("customerName","Seeburger-To-Fusion");
        irto.put("emailNotification",true);
        irto.put("phoneNotification",true);
        irjson.put("to",irto);

        return mapper.writeValueAsString(irjson);

    }


    public String jsonAddParcelRequest() throws JsonProcessingException {
        String clientReferenceNumber = String.format("AAA%07d", new Random().nextInt());

        return String.format("[{\"clientParcelReference\":\"%s\"}]", clientReferenceNumber);
    }
}
