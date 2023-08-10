package com.hdfs.etl.util;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyFileReader {

    private static Properties prop = new Properties();

    public static Properties readPropertyFile(String file) throws Exception {
        if(prop.isEmpty()) {
            InputStream inputStream = PropertyFileReader.class.getClassLoader().getResourceAsStream(file);
            try{
                prop.load(inputStream);
            } catch (IOException ioe) {
                throw ioe;
            } finally {
                if(inputStream != null){
                    inputStream.close();
                }
            }
        }
        return prop;
    }
}
