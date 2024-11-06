package com.da.kafkaconsumerservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmailMessage {

    private String id;
    private String subject;
    private String body;
    private String senderEmail;
    private List<String> recipientEmails;

}