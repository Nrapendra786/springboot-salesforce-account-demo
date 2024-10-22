package org.salesforce.integration.pubsub.kafka;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nrapendra.Message;
import org.apache.kafka.common.serialization.Serializer;

public class MessageSerializer implements Serializer<Message> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, Message data) {
        try {
            return objectMapper.writeValueAsBytes(data);
        } catch (Exception e) {
            throw new RuntimeException("Error serializing Message", e);
        }
    }
}
