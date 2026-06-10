package com.example.userComposite.kafka;

import com.example.userComposite.dto.Event;
import com.example.userComposite.dto.User;
import com.example.userComposite.helper.KafkaHelper;
import com.example.userComposite.repo.OutboxEventRepository;
import com.example.userComposite.user.persistences.OutboxEvent;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;
import tools.jackson.core.type.TypeReference;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;

@Component
@RequiredArgsConstructor
@Slf4j
public class OutboxPublisher {

    private final KafkaHelper kafkaHelper;
    private final OutboxEventRepository outboxRepository;
    private final ObjectMapper objectMapper;


    @Scheduled(fixedDelay = 5000)
    public void publishEvents() {

        outboxRepository
                .findByStatus(OutboxEvent.Status.NEW)
                .flatMap(this::publish)
                .subscribe();
    }

    private Mono<Void> publish(
            OutboxEvent outboxEvent) {

        try {

            Event<String, User> event =
                    objectMapper.readValue(
                            outboxEvent.getPayloadJson(),
                            new TypeReference<Event<String, User>>() {}
                    );

            kafkaHelper.sendMessage(
                    outboxEvent.getTopic(),
                    event
            );

            outboxEvent.setStatus(
                    OutboxEvent.Status.SENT
            );

            outboxEvent.setPublishedAt(
                    Instant.now()
            );

            return outboxRepository
                    .save(outboxEvent)
                    .then();

        } catch (Exception ex) {

            outboxEvent.setStatus(
                    OutboxEvent.Status.RETRY
            );

            outboxEvent.setLastError(
                    ex.getMessage()
            );

            return outboxRepository
                    .save(outboxEvent)
                    .then();
        }
    }



}