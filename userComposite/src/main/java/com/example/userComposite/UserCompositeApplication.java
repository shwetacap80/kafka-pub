package com.example.userComposite;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class UserCompositeApplication {

	public static void main(String[] args) {
		SpringApplication.run(UserCompositeApplication.class, args);
	}

}
