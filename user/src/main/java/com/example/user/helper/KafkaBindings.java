package com.example.user.helper;

import static com.example.user.utils.TopicNames.User.COMMANDS;
import static com.example.user.utils.TopicNames.User.COMMANDS_DLQ;

public final class KafkaBindings {

    private KafkaBindings() {}

    // =========================================================
    // PRODUCERS
    // =========================================================
    public static final String USER_COMMANDS_PRODUCER = "userCommandsProducer-out-0";
    public static final String USER_EVENTS_PRODUCER = "userEventsProducer-out-0";

    // =========================================================
    // CONSUMERS
    // =========================================================
    public static final String USER_COMMANDS_CONSUMER = "userCommands-in-0";

    public static final String USER_COMMANDS_CONSUMER_GROUP = "user-commands-cg";


}