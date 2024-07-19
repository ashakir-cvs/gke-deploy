package org.example.gkedeploy;

import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
@Slf4j
public class KafkaRecordListener {

    @KafkaListener(topics = "topic_01")
    public void consumeKafkaMessage(String message) {
        log.warn(message);
    }
}
