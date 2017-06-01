package utils;

import com.opencsv.CSVReader;
import helper.BaseClass;
import org.testng.annotations.DataProvider;

import java.io.FileReader;
import java.io.IOException;
import java.util.*;


public class TestDataReaderFromCsv extends BaseClass {

    private CSVReader reader;

    public  List<Object[]> getCsvData(String filePath) throws IOException {
        reader = new CSVReader(new FileReader(filePath));
        List<Object []> testData = new ArrayList<>();
        String[] columns = reader.readNext();

        while(columns != null)
        {
            testData.add(columns);
            columns = reader.readNext();
        }

        return testData;
    }

}






