package com.da.kafkaproducerservice.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Builder
@Data
public class EmailMessage {

    private String id;
    private String subject;
    private String body;
    private String senderEmail;
    private List<String> recipientEmails;

}