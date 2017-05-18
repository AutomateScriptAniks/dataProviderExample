package utils;


import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    Properties properties = new Properties();
    InputStream inputStream = null;

    public PropertyReader()
    {
        loadPropertyFile();
    }

    public void loadPropertyFile()
    {
        try {
            inputStream = new FileInputStream("src/test/resources/uri.properties");
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public String readProperty(String key) {
        return properties.getProperty(key);
    }
}


