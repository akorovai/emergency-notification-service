package com.da.kafkaproducerservice.serializer;

import com.da.kafkaproducerservice.dto.EmailMessage;
import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializerProvider;
import org.apache.kafka.common.serialization.Serializer;

import java.io.IOException;

public class EmailMessageSerializer implements Serializer<EmailMessage> {

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    public byte[] serialize(String topic, EmailMessage emailMessage) {
        try {
            return objectMapper.writeValueAsBytes(emailMessage);
        } catch (IOException e) {
            throw new RuntimeException("Error serializing EmailMessage", e);
        }
    }
}
