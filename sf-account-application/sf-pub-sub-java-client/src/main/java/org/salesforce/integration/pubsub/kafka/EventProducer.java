package org.salesforce.integration.pubsub.kafka;

import com.nrapendra.Message;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

import static org.salesforce.integration.pubsub.utils.AppUtil.BOOTSTRAP_SERVER_CONFIG;
import static org.salesforce.integration.pubsub.utils.AppUtil.TOPIC_NAME;

public class EventProducer {

    public KafkaProducer<String, Message> eventProduce() {
        Properties props = new Properties();
        props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, BOOTSTRAP_SERVER_CONFIG);
        props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, MessageSerializer.class.getName());
        return new KafkaProducer<>(props);
    }

    public void sendToKafkaTopic(Message message) {

        //Initialize Kafka Producer
        try (KafkaProducer<String, Message> producer = eventProduce()) {

            // Send data
            producer.send(new ProducerRecord<>(TOPIC_NAME, message));

            // Tell producer to send all data and block until complete - synchronous
            producer.flush();
        }

    }
}
