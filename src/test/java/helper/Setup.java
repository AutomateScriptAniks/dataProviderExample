package helper;

import utils.JsonUtils;
import utils.MongoDataLoader;
import utils.TestData;

public class Setup {

    static MongoDataLoader getData = new MongoDataLoader(TestData.mongohost,
            TestData.mongoport);

    public static void settingLegacyData()
    {

            getData.deleteAllRecordFrom
                    (TestData.mongolegacydatabase,
                    TestData.mongolegacycollection,
                    TestData.legacyfieldname,
                    TestData.legacyfieldvalue);

            getData.addRecord
                    (TestData.mongolegacydatabase,
                    TestData.mongolegacycollection,
                    TestData.jsonLegacyOrderData());


    }

    public static void settingOrderData()
    {
        getData.deleteAllRecordFrom
                (TestData.mongoorderdatabase,
                        TestData.mongoordercollection,
                        TestData.orderfieldname,
                        TestData.orderfieldvalue);


        getData.addRecord
                (TestData.mongoorderdatabase,
                        TestData.mongoordercollection,
                        TestData.jsonOrderData());

    }
}
