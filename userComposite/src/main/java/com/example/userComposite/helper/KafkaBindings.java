package com.example.userComposite.helper;

import static com.example.userComposite.utils.TopicNames.User.COMMANDS;
import static com.example.userComposite.utils.TopicNames.User.COMMANDS_DLQ;

public final class KafkaBindings {

    private KafkaBindings() {}
    public static final String USER_COMMANDS_PRODUCER = "userCreateProducer-out-0";
    public static final String USER_COMMANDS_DLQ_PRODUCER = "userCreateDlqProducer-out-0";

    public static final String USER_EVENTS_CONSUMER = "userEvents-in-0";

    public static final String USER_EVENTS_CONSUMER_GROUP = "user-events-cg";

    public static String resolveTopicToBinding(String topic) {
        return switch (topic) {

            case COMMANDS -> USER_COMMANDS_PRODUCER;
            case COMMANDS_DLQ -> USER_COMMANDS_DLQ_PRODUCER;

            default -> throw new IllegalArgumentException(
                    "Unknown topic: " + topic
            );
        };
    }


}