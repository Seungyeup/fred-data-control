package com.hdfs.etl.load;

import org.apache.kafka.clients.consumer.Consumer;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.codehaus.jackson.map.deser.std.StringDeserializer;

import java.util.Properties;

public class KafkaConsumerTest {
    public static void main(String[] args) {
        Properties kafkaConsProperty = new Properties();
        kafkaConsProperty.put(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092");
        kafkaConsProperty.put(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaConsProperty.put(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class);
        kafkaConsProperty.put(ConsumerConfig.GROUP_ID_CONFIG, "test-consumer-group");
        kafkaConsProperty.put(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "earliest");

        Consumer<String, String> consumer = new KafkaConsumer<String, String>()
    }
}
