package helper;

import com.fasterxml.jackson.core.JsonProcessingException;

import io.restassured.RestAssured;
import utils.MongoDataLoader;
import utils.PropertyReader;
import utils.TestData;

public class Setup {

     MongoDataLoader getData = new MongoDataLoader(TestData.mongohost,
            TestData.mongoport);

     public Setup(){

         //RestAssured.baseURI = "http://" + System.getenv().get("BASEHOST");
         RestAssured.baseURI = new PropertyReader().readProperty("SIT_external_uri");
     }

    public void setBasePort(String port) {RestAssured.port = Integer.valueOf(port);

    }
    public  void setBasePath(String basePath)
    {
        RestAssured.basePath = basePath;
    }

    public  void settingLegacyData() throws JsonProcessingException {

            getData.deleteAllRecordFrom
                    (TestData.mongolegacydatabase,
                    TestData.mongolegacycollection,
                    TestData.legacyfieldname,
                    TestData.legacyfieldvalue);

            getData.addRecord
                    (TestData.mongolegacydatabase,
                    TestData.mongolegacycollection,
                            new TestData().jsonCollectPlusRequest());


    }

    public  void settingOrderData() throws JsonProcessingException {
        getData.deleteAllRecordFrom
                (TestData.mongoorderdatabase,
                        TestData.mongoordercollection,
                        TestData.orderfieldname,
                        TestData.orderfieldvalue);


        getData.addRecord
                (TestData.mongoorderdatabase,
                        TestData.mongoordercollection,
                        new TestData().jsonCollectPlusRequest());

    }
}
