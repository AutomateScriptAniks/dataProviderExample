package helper;


import com.opencsv.CSVReader;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;


import org.testng.annotations.DataProvider;
import utils.JsonUtils;
import utils.PropertyReader;
import utils.TestDataReaderFromCsv;

import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import java.util.Map;


public abstract class BaseClass {


   protected static Map<String, String> params =  new JsonUtils().setMap();

   protected static RequestSpecification uriSpec(String uri) {

      RequestSpecification createorderuri = new
              RequestSpecBuilder().
              setBaseUri(uri).build();

      return createorderuri;
   }

   RequestSpecification createorderuri = new
           RequestSpecBuilder().
           setBaseUri(new PropertyReader().readProperty("Order.uri")).build();



   protected static RequestSpecification deliveryoptionuri = new
           RequestSpecBuilder().
           setBaseUri(new PropertyReader().readProperty("deliveryOption.uri")).build();

   protected static RequestSpecification outhtokenbuilder = new
           RequestSpecBuilder().
           setBaseUri(new PropertyReader().readProperty("oauth.uri")).build();

   protected static RequestSpecification trackinguri = new
           RequestSpecBuilder().
           setBaseUri(new PropertyReader().readProperty("tracking.uri")).build();


   protected static Response response;
   protected static JsonPath json;
   protected Client client = new Client();
   protected OrderService orderService;
   protected ScheduleService scheduleService;
   protected TrackingService trackingService;
   protected OauthTokenReader tokenReader;


   protected String todaysDate = new SimpleDateFormat("yyyy-MM-dd").format(new Date());






   }


