package com.example.userComposite.user.controller;

import com.example.userComposite.dto.Event;
import com.example.userComposite.dto.User;
import com.example.userComposite.helper.KafkaBindings;
import com.example.userComposite.helper.KafkaHelper;
import com.example.userComposite.utils.EventTypes;
import com.example.userComposite.utils.TopicNames;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;
import java.util.UUID;

@RestController
@RequestMapping("/api/v1/composite/user")
public class UserController {
    private final KafkaHelper kafkaHelper;
    private final WebClient webClient;

    @Value("${user.service.base-url}")
    private String userServiceUrl;

    public UserController(
                          KafkaHelper kafkaHelper,
                          WebClient webClient){
        this.kafkaHelper = kafkaHelper;
        this.webClient = webClient;

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

    @GetMapping
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
