package org.example.gkedeploy;

import lombok.NoArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class KafkaRecordListener {

    @KafkaListener(topics = "topic_01")
    public void consumeKafkaMessage(String message) {
        System.out.println(message);
    }
}
