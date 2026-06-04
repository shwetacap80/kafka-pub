package com.example.user.kafka;

import com.example.user.dto.Event;
import com.example.user.persistences.User;
import com.example.user.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import java.util.function.Consumer;

@Component
@RequiredArgsConstructor
@Slf4j
public class UserCommandConsumer {

    private final UserService userService;

    @Bean
    public Consumer<Event<String, User>> userCommands() {

        return event -> {

            log.info("Received Event Type: {}", event.getEventType());
            log.info("Received Event Id: {}", event.getEventId());

            User user = event.getData();
            System.out.println("==user"+user);
            userService.createUser(user)
                    .doOnSuccess(saved ->
                            log.info(
                                    "User saved successfully: {}",
                                    saved.getId()
                            )
                    )
                    .doOnError(error ->
                            log.error(
                                    "Failed to save user",
                                    error
                            )
                    )
                    .subscribe();
        };
    }


}
