package com.hdfs.etl.util;

import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.DeserializationContext;
import org.codehaus.jackson.map.JsonDeserializer;

import java.io.IOException;

public class CustomFloatDeserializer extends JsonDeserializer<Float> {

    @Override
    public Float deserialize(JsonParser jp, DeserializationContext ctxt) throws IOException, JsonProcessingException {
        return null;
    }

}
