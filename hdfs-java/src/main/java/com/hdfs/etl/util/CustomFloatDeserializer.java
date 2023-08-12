package com.hdfs.etl.util;

import com.fasterxml.jackson.core.JacksonException;
import com.fasterxml.jackson.databind.JsonDeserializer;
//import org.codehaus.jackson.JsonParser;
//import org.codehaus.jackson.JsonProcessingException;
//import org.codehaus.jackson.map.DeserializationContext;


import java.io.IOException;

// FRED 에서 값을 불러올 때 가끔 숫자가 아닌 `.`값만 반환되는 경우가 있다.
public class CustomFloatDeserializer extends JsonDeserializer<Float> {

    @Override
    public Float deserialize(com.fasterxml.jackson.core.JsonParser p, com.fasterxml.jackson.databind.DeserializationContext ctxt) throws IOException, JacksonException {
        String floatString = p.getText();
        if(floatString.equals(".")){
            return null;
        }
        return Float.valueOf(floatString);
    }
}
