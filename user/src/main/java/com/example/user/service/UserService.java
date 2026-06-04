package com.example.user.service;

import com.example.user.persistences.User;
import com.example.user.repo.UserRepo;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.data.mongodb.core.MongoAction;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepo userRepo;

    public Flux<User> getAllUsers() {
       return userRepo.findAll();
    }

    public Mono<User> createUser(User user) {
        return userRepo.save(user);
    }
}
