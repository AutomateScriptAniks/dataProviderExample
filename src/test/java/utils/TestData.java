package utils;

public class TestData {

    public static final String deliverydate = "/delivery-date";
    public static final String orderpath = "/orders/{id}";
    public static final String orderid = "da19a9db-b754-47a6-a54b-5a07d0656526";
    public static final String strid = "W20T";
    public static final String parcelId = "/parcels/{id}";
    public static final String storeId = "/stores/{storeId}";
    public static final String scheduleserviceport = "8084";
    public static final String orderserviceport =  "8085";
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
    public static String jsonLegacyOrderData()
    {
        String json =
                "{"
                        + "\"_id\" : \"da19a9db-b754-47a6-a54b-5a07d0656526\","
                        + "\"_class\" : \"legacyOrder.greencollectplus.CollectPlusLegacyOrder\","
                        + "\"legacyId\" : \"8TMJ78610122A064\","
                        + "\"legacyPOD\" : \"24\","
                        + "\"legacyDepot\" : \"64\","
                        + "\"legacyRoutingServiceCentreCode\" : \"LICH\","
                        + "\"yodelSector\" : \"52E\","
                        + "\"legacyLabelServiceCode\" : \"POD\""
                        + "}";

        return json;



    }

    public static String jsonOrderData()
    {
        String json =
                "{"

                        + "\"_id\" : ObjectId(\"58b6d7175908010001545dc1\"),"
                        + "\"_class\" : \"order.orders.Order\","
                        + "\"orderId\" : \"da19a9db-b754-47a6-a54b-5a07d0656526\","
                        + "\"parcelId\" : \"2NWDXMLK\","
                        + "\"clientAccount\" : \"100001\","
                        + "\"service\" : \"C24P\","
                        + "\"shipmentDate\" : ISODate(\"2018-02-28T00:00:00.000Z\"),"
                        + "\"customerName\" : \"James Bond - 007\","
                        + "\"to\": {"
                        + "\"storeCode\" : \"W20C\","
                        + "\"customerContactStrategy\" : \"ES\","
                        + "\"addressLine1\" : \"Apartment 25, SpringField Court\","
                        + "\"addressLine2\" : \"\","
                        + "\"town\" : \"Manchester\","
                        + "\"postcode\" : \"B28 0HT\","
                        + "\"countryCode\" : \"UK\","
                        + "\"county\" : \"\","
                        + "\"customerContactNumber\" : \"07448622464\","
                        + "\"customerEmail\" : \"string@string.com\","
                        + "\"customerName\" : \"string\""
                        + "},"
                        + "\"brand\" : \"House of Fraser\","
                        + "\"deliveryDate\" : \"2018-03-01\","
                        + "\"createdAt\" : ISODate(\"2017-03-01T14:13:43.444Z\"),"
                        + "\"updatedAt\" : ISODate(\"2017-03-01T14:13:43.444Z\"),"
                        + "\"orderStatus\" : \"ORDER_ADVISED\""
                        + "}";

        return json;



    }
}
