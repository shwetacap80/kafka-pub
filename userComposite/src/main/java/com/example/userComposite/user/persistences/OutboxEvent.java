package com.example.userComposite.user.persistences;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.time.Instant;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Document("outbox_events")
public class OutboxEvent {

    @Id
    private String id; // same as eventId

    private String topic;
    private String eventType;
    private String key;
    private String payloadJson;

    private Status status;

    private int attemptCount;
    private Instant nextAttemptAt;

    private String lockedBy;
    private Instant lockedUntil;

    private Instant createdAt;
    private Instant updatedAt;
    private Instant publishedAt;

    private String lastError;

    public enum Status {
        NEW,
        PROCESSING,
        RETRY,
        SENT,
        DLQ,
        FAILED
    }
}