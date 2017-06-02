package utils;

import com.beust.jcommander.internal.Maps;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TestData {

    ObjectMapper mapper = new ObjectMapper();

    public static final String mongohost = "10.48.211.120";
    public static final String mongoport = "27017";
    public static final String mongolegacydatabase = "legacy-orders";
    public static final String mongolegacycollection = "legacy.orders";
    public static final String mongoorderdatabase = "orders";
    public static final String mongoordercollection = "order";
    public static final String legacyfieldname = "legacyId";
    public static final String legacyfieldvalue = "8TMJ78610122A064";
    public static final String orderfieldname = "orderId";
    public static final String orderfieldvalue = "da19a9db-b754-47a6-a54b-5a07d0656526";





    public String jsonCollectPlusRequest() throws JsonProcessingException {

        Map<String, Object> json = Maps.newHashMap();
        json.put("brand","House of Fraser");
        json.put("clientAccount","100003");
        json.put("customerReference","My Parcel");
        json.put("service","C24P");
        json.put("shipmentDate","2018-03-22");
        json.put("pickupLocation","MARPLE");

        Map<String,Object> to = Maps.newHashMap();
        to.put("customerContactNumber","07448622464");
        to.put("customerEmail","string@string.com");
        to.put("customerName","Seeburger-To-Fusion");
        to.put("customerContactStrategy","ES");
        to.put("emailNotification",true);
        to.put("phoneNotification",true);
        to.put("storeCode","W20T");
        json.put("to",to);

        return mapper.writeValueAsString(json);

    }

    public String jsonYellowRequest() throws JsonProcessingException {

        Map<String, Object> json = Maps.newHashMap();
        json.put("brand","House of Fraser");
        json.put("clientAccount","100004");
        json.put("customerReference","My Parcel");
        json.put("service","12A");
        json.put("shipmentDate","2018-06-10");
        json.put("pickupLocation","TADLEY");

        Map<String,Object> to = Maps.newHashMap();

        Map<String,Object> address = Maps.newHashMap();
        address.put("addressLine1","Apartment 25, SpringField Court");
        address.put("countryCode","GB");
        address.put("postcode","M3 7EH");
        address.put("organisationName","OrgGB");
        address.put("departmentName","OptionalButImp");

        to.put("address",address);
        to.put("customerContactNumber","07448622464");
        to.put("customerEmail","string@string.com");
        to.put("customerName","Seeburger-To-Fusion");
        to.put("emailNotification",true);
        to.put("phoneNotification",true);
        json.put("to",to);

        return mapper.writeValueAsString(json);

    }

    public String jsonYellowRequestForIreland() throws JsonProcessingException {

        Map<String, Object> irjson = Maps.newHashMap();
        irjson.put("brand","House of Fraser");
        irjson.put("clientAccount","100018");
        irjson.put("customerReference","My Parcel");
        irjson.put("service","12A");
        irjson.put("shipmentDate","2018-06-10");
        irjson.put("pickupLocation","HATFIELD");

        Map<String,Object> irto = Maps.newHashMap();

        Map<String,Object> iraddress = Maps.newHashMap();
        iraddress.put("addressLine1","Dublin");
        iraddress.put("countryCode","IE");
        iraddress.put("postcode","D02 AF30");
        iraddress.put("organisationName",null);
        iraddress.put("departmentName","OptionalButImp");

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
