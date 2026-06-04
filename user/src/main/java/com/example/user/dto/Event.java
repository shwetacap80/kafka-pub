package com.example.user.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;

import java.time.Instant;
import java.util.UUID;

@Data
@SuperBuilder
@NoArgsConstructor
@AllArgsConstructor
public class Event<K, T> {

    private String eventId;
    private K key;
    private String eventType;
    private T data;
    @Builder.Default
    private Instant eventCreatedAt= Instant.now();

    // Convenience 3-arg constructor (preserved)
    public Event(String eventType, K key, T data) {
        this.eventId = UUID.randomUUID().toString();
        this.eventType = eventType;
        this.key = key;
        this.data = data;
        this.eventCreatedAt = Instant.now();
    }

    // Builder-style constructor (preserved)
    public Event(String eventId, String eventType, K key, T data) {
        this.eventId = eventId;
        this.eventType = eventType;
        this.key = key;
        this.data = data;
        this.eventCreatedAt = Instant.now();
    }
}