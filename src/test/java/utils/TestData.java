package utils;

import com.beust.jcommander.internal.Maps;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


import java.util.HashMap;
import java.util.Map;
import java.util.Random;

public class TestData {

    ObjectMapper mapper = new ObjectMapper();

    public static final String deliverydate = "/delivery-date";
    public static final String orderpath = "/orders/{id}";
    public static final String orders = "/orders/";
    public static final String orderid = "da19a9db-b754-47a6-a54b-5a07d0656526";
    public static final String strid = "W20T";
    public static final String parcelId = "/parcels/{id}";
    public static final String storeId = "/stores/{storeId}";
    public static final String scheduleserviceport = "8084";
    public static final String orderserviceport = "8085";
    public static final String legacyserviceport = "8086";
    public static final String parcelserviceport = "8083";
    public static final String collectplusserviceport = "8092";
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
    //6fe80bdb-8cef-4ca5-be1f-098f13d0da19
    //public static final String HTTP_METHOD = "POST";
    public static final String HTTP_URI = "10.48.160.61";
    public static final String HTTP_CLIENT_ID = "100004";
    public static final String HTTP_SECRET_KEY = "password4";



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
        json.put("service","1VX");
        json.put("shipmentDate","2018-03-22");
        json.put("pickupLocation","TADLEY");

        Map<String,Object> to = Maps.newHashMap();

        Map<String,Object> address = Maps.newHashMap();
        address.put("addressLine1","Apartment 25, SpringField Court");
        address.put("countryCode","GB");
        address.put("postcode","DB999AA");

        to.put("address",address);
        to.put("customerContactNumber","07448622464");
        to.put("customerEmail","string@string.com");
        to.put("customerName","Seeburger-To-Fusion");
        to.put("emailNotification",true);
        to.put("phoneNotification",true);
        json.put("to",to);

        return mapper.writeValueAsString(json);

    }


    public String jsonAddParcelRequest() throws JsonProcessingException {
        String clientReferenceNumber = String.format("AAA%07d", new Random().nextInt());

        return String.format("[{\"clientParcelReference\":\"%s\"}]", clientReferenceNumber);
    }
}
