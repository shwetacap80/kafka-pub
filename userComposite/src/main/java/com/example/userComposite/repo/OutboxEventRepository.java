package com.example.userComposite.repo;

import com.example.userComposite.user.persistences.OutboxEvent;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import reactor.core.publisher.Flux;

public interface OutboxEventRepository extends ReactiveMongoRepository<OutboxEvent, String> {
    Flux<OutboxEvent> findByStatus(
            OutboxEvent.Status status
    );
}
