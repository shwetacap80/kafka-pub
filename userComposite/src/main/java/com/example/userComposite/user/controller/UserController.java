package com.example.userComposite.user.controller;

import com.example.userComposite.dto.Event;
import com.example.userComposite.dto.User;
import com.example.userComposite.helper.KafkaBindings;
import com.example.userComposite.helper.KafkaHelper;
import com.example.userComposite.repo.OutboxEventRepository;
import com.example.userComposite.user.persistences.OutboxEvent;
import com.example.userComposite.utils.EventTypes;
import com.example.userComposite.utils.TopicNames;
import io.swagger.v3.oas.annotations.Operation;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/composite/user")
public class UserController {
    private final KafkaHelper kafkaHelper;
    private final ObjectMapper objectMapper;
    private final WebClient webClient;
    private final OutboxEventRepository outboxRepository;

    @Value("${user.service.base-url}")
    private String userServiceUrl;

    public UserController(
                          KafkaHelper kafkaHelper,
                          WebClient webClient,
                          ObjectMapper objectMapper,OutboxEventRepository outboxRepository){
        this.kafkaHelper = kafkaHelper;
        this.webClient = webClient;
        this.objectMapper = objectMapper;
        this.outboxRepository = outboxRepository;

    }

@PostMapping
public Mono<Map<String, String>> createUser(
        @RequestBody User user) {

    String eventId = UUID.randomUUID().toString();

    Event<String, User> event =
            new Event<>(
                    EventTypes.CREATE_REQUESTED,
                    eventId,
                    user
            );

    OutboxEvent outboxEvent =
            OutboxEvent.builder()
                    .id(eventId)
                    .topic(KafkaBindings.USER_COMMANDS_PRODUCER)
                    .eventType(EventTypes.CREATE_REQUESTED)
                    .key(eventId)
                    .payloadJson(objectMapper.writeValueAsString(event))
                    .status(OutboxEvent.Status.NEW)
                    .attemptCount(0)
                    .createdAt(Instant.now())
                    .updatedAt(Instant.now())
                    .build();

    return outboxRepository.save(outboxEvent)
            .thenReturn(Map.of(
                    "eventId", eventId,
                    "userName", user.getName()
            ));
}

    @GetMapping
    @Operation(hidden = true)
    public Flux<User> getUsers(){
        return webClient.get()
                .uri(userServiceUrl + "/api/v1/user")
                .retrieve()
                .bodyToFlux(User.class);
    }


//    private String writeJson(Object obj) {
//        try {
//            return objectMapper.writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
