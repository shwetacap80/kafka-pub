package com.example.user;

import com.example.user.config.kafka.StreamBindingsConfig;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.stream.config.BindingServiceProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;

@SpringBootApplication
@Import(StreamBindingsConfig.class)
public class UserApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserApplication.class, args);
	}

	@Bean
    ApplicationRunner runner(BindingServiceProperties props) {
		return args -> {
			System.out.println("===== BINDINGS =====");

			props.getBindings().forEach((k, v) ->
					System.out.println(k + " -> " + v.getDestination()));
		};
	}
}
