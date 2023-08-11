package com.hdfs.etl.util;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

// FRED 에서 값을 불러올 때 가끔 숫자가 아닌 `.`값만 반환되는 경우가 있다.
public class CustomFloatDeserializer extends JsonDeserializer<Float> {

    @Override
    public Float deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        // TODO : Auto-Generated Method stub
        String floatString = jp.getText();
        if(floatString.equals(".")){
            return null;
        }
        return Float.valueOf(floatString);
    }

}
