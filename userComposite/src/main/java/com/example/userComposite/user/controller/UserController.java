package com.example.userComposite.user.controller;

import com.example.userComposite.dto.Event;
import com.example.userComposite.dto.User;
import com.example.userComposite.helper.KafkaBindings;
import com.example.userComposite.helper.KafkaHelper;
import com.example.userComposite.utils.EventTypes;
import com.example.userComposite.utils.TopicNames;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final KafkaHelper kafkaHelper;
    private final ObjectMapper objectMapper;

    public UserController(
                          KafkaHelper kafkaHelper,
                          ObjectMapper objectMapper){
        this.kafkaHelper = kafkaHelper;
        this.objectMapper = objectMapper;

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



     kafkaHelper.sendMessage(
                        KafkaBindings.USER_COMMANDS_PRODUCER,
                        event
                );
    return Mono.just(Map.of(eventId,user.getName()));
}

//    private String writeJson(Object obj) {
//        try {
//            return objectMapper.writeValueAsString(obj);
//        } catch (Exception e) {
//            throw new RuntimeException(e);
//        }
//    }
}
