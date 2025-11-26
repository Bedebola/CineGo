package com.cinego;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class CineGoApplication {

	public static void main(String[] args) {
		SpringApplication.run(CineGoApplication.class, args);
	}

}
