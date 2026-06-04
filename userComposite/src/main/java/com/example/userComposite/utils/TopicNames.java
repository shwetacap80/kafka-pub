package com.example.userComposite.utils;


import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.stream.function.StreamBridge;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Component;

public class TopicNames {

    public static final class Versions {
        public static final String V1 = "v1";
    }

    public static final class User {


            public static final String COMMANDS = "user.commands." + Versions.V1;
            public static final String COMMANDS_DLQ = "user.commands." + Versions.V1 + ".dlq";
            public static final String EVENTS = "user.events." + Versions.V1;




    }

}
