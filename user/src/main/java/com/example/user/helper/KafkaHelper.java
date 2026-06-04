package com.example.user.helper;

import com.example.user.dto.Event;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class KafkaHelper {
    private final StreamBridge streamBridge;

    public void sendMessage(String binding, Event<?, ?> event) {

        Message<?> message = MessageBuilder
                .withPayload(event)
                .setHeader("partitionKey", event.getKey())
                .build();

        boolean sent = streamBridge.send(binding, message);

        if (sent) {
            log.info("Event key ={} ", event.getKey());
        } else {
            log.error("Failed to send Event key={}", event.getKey());
        }
    }
}
