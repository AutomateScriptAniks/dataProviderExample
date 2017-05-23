package helper;


import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import utils.JsonUtils;
import utils.PropertyReader;

import java.util.Map;
import java.util.concurrent.ScheduledExecutorService;

public abstract class BaseClass {


   protected static Map<String, String> params =  new JsonUtils().setMap();

   protected static RequestSpecification createorderuri = new
           RequestSpecBuilder().
           setBaseUri(new PropertyReader().readProperty("Order.uri")).build();

   protected static RequestSpecification deliveryoptionuri = new
           RequestSpecBuilder().
           setBaseUri(new PropertyReader().readProperty("deliveryOption.uri")).build();


   protected static Response response;
   protected static JsonPath json;
   protected Client client = new Client();
   protected OrderService orderService;
   protected ScheduleService scheduleService;


}
