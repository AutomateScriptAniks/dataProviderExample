package helper;

import org.testng.annotations.DataProvider;
import utils.TestDataReaderFromCsv;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class DataProviders {

    @DataProvider
    public Iterator<Object[]> dataForDeliveryOptions() throws IOException {

        List<Object[]> csvData ;
        csvData = new TestDataReaderFromCsv().getCsvData("src/test/resources/testData/deliverOptionsTestData.csv");


        return csvData.iterator();
    }


    @DataProvider
    public Iterator<Object[]> endpointForConsignmentJourney() throws IOException {

        List<Object[]> csvData ;
        csvData = new TestDataReaderFromCsv().getCsvData("src/test/resources/testData/TestEndPoint.csv");


        return csvData.iterator();
    }
}
