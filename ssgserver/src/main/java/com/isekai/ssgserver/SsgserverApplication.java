package com.isekai.ssgserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class SsgserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsgserverApplication.class, args);
	}

}
