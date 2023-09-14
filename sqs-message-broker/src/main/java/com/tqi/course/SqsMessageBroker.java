package com.tqi.course;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class SqsMessageBroker {

	public static void main(String[] args) {
		SpringApplication.run(SqsMessageBroker.class, args);
	}

}
