package com.example.user.repo;

import com.example.user.persistences.User;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

@Repository

public interface UserRepo extends ReactiveMongoRepository<User, String> {



}
