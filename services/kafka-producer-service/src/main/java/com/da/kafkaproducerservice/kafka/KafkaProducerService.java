package com.da.kafkaproducerservice.kafka;

import com.da.kafkaproducerservice.dto.EmailMessage;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KafkaProducerService {

    @Value("${spring.kafka.topic.name}")
    private String topic;

    private final KafkaTemplate<String, String> kafkaTemplate;

    public String sendMailMessage(EmailMessage emailMessage) {
        Message<EmailMessage> message = MessageBuilder
                .withPayload(emailMessage)
                .setHeader(KafkaHeaders.TOPIC, topic)
                .build();

        var future = kafkaTemplate.send(message);
        log.info("Message sent to topic: {}", topic);

        future.whenComplete(KafkaCallbackUtils
                .createKafkaCallback(topic,
                        "Sender: " + emailMessage.getSenderEmail()
                        + "\nReceivers: " + emailMessage.getRecipientEmails()
                        + "\nType: " + MessageType.EMAIL
                ));

        return "Message send initiated";
    }

}
