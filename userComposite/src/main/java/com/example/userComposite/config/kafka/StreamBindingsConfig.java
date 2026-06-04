package com.example.userComposite.config.kafka;

import org.springframework.cloud.stream.config.BindingProperties;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.context.annotation.Configuration;
import com.example.userComposite.helper.KafkaBindings;
import com.example.userComposite.utils.TopicNames;


@Configuration
public class StreamBindingsConfig {

    public StreamBindingsConfig(BindingServiceProperties properties) {

        producer(properties, KafkaBindings.USER_COMMANDS_PRODUCER, TopicNames.User.COMMANDS);
//        producer(properties, KafkaBindings.USER_COMMANDS_DLQ_PRODUCER, TopicNames.User.COMMANDS_DLQ);
//        consumer(properties, KafkaBindings.USER_EVENTS_CONSUMER, TopicNames.User.EVENTS, KafkaBindings.USER_EVENTS_CONSUMER_GROUP);

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