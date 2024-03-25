package com.isekai.ssgserver;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan(basePackages = {"com.isekai"})
public class SsgserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsgserverApplication.class, args);
	}

}
