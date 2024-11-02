package com.da.kafkaproducerservice.kafka;

import com.da.kafkaproducerservice.dto.EmailMessage;
import lombok.Builder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/kafka")
@RequiredArgsConstructor(onConstructor = @__(@Autowired))
public class KafkaController {

    private final KafkaProducerService kafkaProducerService;

    @PostMapping("/send-email")
    public ResponseEntity<DefaultResponse> sendMailMessage(@RequestBody EmailMessage message) {
        var res = DefaultResponse
                .builder()
                .code(HttpStatus.OK.value())
                .message(kafkaProducerService.sendMailMessage(message))
                .build();

        return ResponseEntity.ok().body(res);
    }

    @Builder
    public record DefaultResponse(int code, String message) {
    }
}
