package com.example.user.config.kafka;


import com.example.user.helper.KafkaBindings;

import com.example.user.utils.TopicNames;
import org.springframework.context.annotation.Configuration;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.cloud.stream.config.BindingProperties;

@Configuration
public class StreamBindingsConfig {

    public StreamBindingsConfig(BindingServiceProperties properties) {

//        producer(properties, KafkaBindings.USER_COMMANDS_PRODUCER, TopicNames.User.COMMANDS);
//        producer(properties, KafkaBindings.USER_EVENTS_PRODUCER, TopicNames.User.EVENTS);


        consumer(properties, KafkaBindings.USER_COMMANDS_CONSUMER, TopicNames.User.COMMANDS, KafkaBindings.USER_COMMANDS_CONSUMER_GROUP);

    }

    public static void producer(BindingServiceProperties props, String bindingName, String destination) {
        BindingProperties binding = new BindingProperties();
        binding.setDestination(destination);
        props.getBindings().put(bindingName, binding);
    }

    public static void consumer(BindingServiceProperties props, String bindingName, String destination, String group) {
        BindingProperties binding = new BindingProperties();
        binding.setDestination(destination);
        binding.setGroup(group);
        props.getBindings().put(bindingName, binding);
}
}