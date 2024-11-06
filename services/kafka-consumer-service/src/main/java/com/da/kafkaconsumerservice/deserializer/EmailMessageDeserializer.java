package com.da.kafkaconsumerservice.deserializer;

import com.da.kafkaconsumerservice.dto.EmailMessage;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Deserializer;

import java.util.Map;

public class EmailMessageDeserializer implements Deserializer<EmailMessage> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public void configure(Map<String, ?> configs, boolean isKey) {
    }

    @Override
    public EmailMessage deserialize(String topic, byte[] data) {
        try {
            return objectMapper.readValue(data, EmailMessage.class);
        } catch (Exception e) {
            throw new RuntimeException("Error deserializing EmailMessage", e);
        }
    }

    @Override
    public void close() {
    }
}
