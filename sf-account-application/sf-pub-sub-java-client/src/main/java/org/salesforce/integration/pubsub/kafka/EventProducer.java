package org.salesforce.integration.pubsub.kafka;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class EventProducer {

    public KafkaProducer<String, String> eventProduce(){
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        return new KafkaProducer<>(properties);
    }


    public static void main(String[] args) {
        Properties properties = new Properties();
        properties.setProperty("bootstrap.servers", "localhost:9092");
        properties.setProperty("key.serializer", StringSerializer.class.getName());
        properties.setProperty("value.serializer", StringSerializer.class.getName());

        KafkaProducer<String, String> producer = new KafkaProducer<>(properties);

//        ProducerRecord<String, String> record =
//                new ProducerRecord<String, String>("demo_topic", "Hello World");
//
//        // Send data
//        producer.send(record);
//
//        // Tell producer to send all data and block until complete - synchronous
//        producer.flush();
//
//        // Close the producer
//        producer.close();
    }
}
